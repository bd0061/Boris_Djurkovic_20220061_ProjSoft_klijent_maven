/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package framework.orm;

import framework.DbEngine;
import framework.config.AppConfig;
import framework.orm.anotacije.ImeKolone;
import framework.orm.anotacije.kljuc.ManyToOne;
import framework.orm.anotacije.kljuc.OneToMany;
import static framework.orm.EntityManager.vratiImePolja;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Djurkovic
 */
public final class QueryBuilder<T extends Entitet> {

    private final Class<T> target;
    private Map<Class<?>, Map<Object, Object>> identityCache = new HashMap<>();
    private Set<Object> inProgress = new HashSet<>();
    private AppConfig config;
    private char quote;
    public QueryBuilder(Class<T> target, AppConfig config) {
        this.target = target;
        this.config = config;
        this.quote = config.dbEngine == DbEngine.MYSQL ? '`' : '"';
    }
    
    public T buildObjectGraph(ResultSet rs) throws Exception {
        return buildObjectGraph(target, rs);
    }

    private T buildObjectGraph(Class<T> clazz, ResultSet rs) throws Exception {
        var m = EntityCache.getMetadata(clazz);
        var rsMeta = rs.getMetaData();

        Set<String> columnNames = new HashSet<String>();
        int noCol = rsMeta.getColumnCount();
        for (int i = 1; i <= noCol; i++) {
            columnNames.add(rsMeta.getColumnLabel(i));
        }
        Object id = EntityManager.extractId(clazz, rs, columnNames);

        Map<Object, Object> classCache = identityCache.computeIfAbsent(clazz, _ -> new HashMap<>());
        Object instance = classCache.get(id);
        if (instance == null) {
            Constructor<?> ctor = clazz.getDeclaredConstructor(); //svi enttieti moraju da imaju prazan konstruktor
            ctor.setAccessible(true);
            instance = ctor.newInstance();
            classCache.put(id, instance);

        }
        if (inProgress.contains(instance)) {
            return (T) instance;
        }
        inProgress.add(instance);
        //popuni skalarne vrednosti polja root objekta
        for (Field f : m.skalari) {
            String imeKolone = m.imeTabele + "_" + EntityManager.vratiImePolja(f);
            if (!columnNames.contains(imeKolone)) {
                throw new Exception("Neocekivano ime kolone: " + imeKolone);
            }
            Object val = EntityManager.vratiVrednostPolja(f.getType(), rs, imeKolone);
            f.setAccessible(true);
            f.set(instance, val);
        }

        //FORWARD PASS - idemo kroz sve many to one reference, popunjavamo ih rekurzivno, i stavljamo nazadnu referencu na nas root objekat
        for (Field mof : m.manyToOnes) {
            Class<?> tip = mof.getType();

            // val - instanca objekta ka kome root objekat ima spoljnji kljuc, popunjavamo je sa svim njenim asocijacijama - rekurzija :)
            Object val = buildObjectGraph((Class<T>) tip, rs);

            // postavljamo je kao clan naseg root objekta
            mof.setAccessible(true);
            mof.set(instance, val);
            var m2 = EntityCache.getMetadata((Class<T>)tip);

            // uzimamo klasu objekta ka kome root objekat ima spoljnji kljuc, ona ce sadrzati OneToMany List<rootObjekat> kao nazadnu referencu
            // naci cemo je i popuniti sa trenutnom instancom kako bismo upotpunili bidirekciono mapiranje
            for (Field omf : m2.oneToManys) {
                ParameterizedType pt = (ParameterizedType) omf.getGenericType(); // omf = List<rootCLass>
                Class<?> childType = (Class<?>) pt.getActualTypeArguments()[0]; // childType = rootClass

                //naci cemo odgovarajuce List<rootObjekat> polje tako sto ce childType biti jednak rootClass, a mappedBy anotacija ce biti jednaka
                //imenu polja reference u root klasi
                //sada koristimo getName a ne vratiImePolja jer nas intersuje referenca u aplikaciji a ne ime spoljnog kljuca u bazi
                if (!clazz.equals(childType) || !omf.getAnnotation(OneToMany.class).mappedBy().equals(mof.getName())) {
                    continue;
                }
                //nakon ovog checka nalazimo se na polju koje predstavlja Listu objekata tipa root, to jest referenca unazad
                omf.setAccessible(true);
                List<Object> children = (List<Object>) omf.get(val); //uzimamo referencu unazad, izracunatiZaposleni.listaSmena

                if (children == null) { //prva nazad referenca, inicijalizujemo listu
                    children = new ArrayList<>();
                    omf.set(val, children);
                }

                if (!children.contains(instance)) { //dodajemo nazad referencu ako vec nije prisutna
                    children.add(instance);
                }

            }
        }
        //BACKWARD PASS - idemo kroz sve one to many nazadne reference, popunjavamo jedno dete, opet naravno rekurzivno
        //(ostala deca ce biti popunjena ako ih ima u drugim redovima!),
        // proveravamo da li dete ime naprednu referencu ka root objektu, ako ima, ubacujemo ga u nasu listu nazadnih referennci

        // primetimo da nacin na koji su ovi prolazi implementirani znaci
        // da ukoliko nas ne zanimaju nazadne reference da mozemo da imamo unidirekciono many to one mapiranje
        // medjutim, za unidirekciono one to many bila bi potrebna dodatna logika da bi orm znao kako da interpretira spoljni kljuc u child klasi 
        // i popuni graf objekata
        for (Field omf : m.oneToManys) {
            ParameterizedType pt = (ParameterizedType) omf.getGenericType();
            Class<?> childType = (Class<?>) pt.getActualTypeArguments()[0];
            Object child = buildObjectGraph((Class<T>) childType, rs);
            var m2 = EntityCache.getMetadata((Class<T>)childType);
            boolean referencesUs = false;
            for (Field mof : m2.manyToOnes) {
                if (mof.getType().equals(clazz) && mof.getName().equals(omf.getAnnotation(OneToMany.class).mappedBy())) {
                    referencesUs = true;
                    break;
                }
            }
            if (!referencesUs) {
                throw new Exception("OneToMany mapiranje mora biti bidirekciono: " + clazz.getName() + " - " + omf.getName());
            }
            omf.setAccessible(true);
            List<Object> list = (List<Object>) omf.get(instance);
            if (list == null) {
                list = new ArrayList<>();
                omf.set(instance, list);
            }
            if (!list.contains(child)) {
                list.add(child);
            }

        }
        inProgress.remove(instance);
        return (T) instance;
    }

    public String generateJoinString() {
        Set<Class<T>> poseceneTabele = new HashSet<>();
        StringBuilder sp = new StringBuilder();
        StringBuilder fp = new StringBuilder();
        generateJoinString(target, sp, fp, poseceneTabele);
        sp.delete(sp.length() - 2, sp.length());
        return sp.toString() + "\n" + fp.toString();
    }

    private void generateJoinString(Class<T> root, StringBuilder select, StringBuilder from, Set<Class<T>> poseceneTabele) {
        poseceneTabele.add(root);
        EntityCache.EntityMetadata metadata = EntityCache.getMetadata(root);
        List<Field> polja = metadata.svaPolja;
        List<Class<?>> nextLvl = new ArrayList<>();
        String rtbl = metadata.imeTabele;
        if (from.isEmpty()) {
            from.append(" FROM ").append(quote).append(metadata.imeTabele).append(quote).append(" \n");
        }
        if (select.isEmpty()) {
            select.append("SELECT ");
            for (Field f : metadata.svaPolja) {
                String ime;
                if (f.isAnnotationPresent(OneToMany.class)) {
                    continue;
                }
                if (f.isAnnotationPresent(ImeKolone.class)) {
                    ime = f.getAnnotation(ImeKolone.class).value();
                } else if (f.isAnnotationPresent(ManyToOne.class)) {
                    ime = f.getAnnotation(ManyToOne.class).joinColumn();
                } else {
                    ime = f.getName();
                }
                select.append(quote).append(metadata.imeTabele).append(quote).append(".").append(ime).append(" AS ").append(quote).append(metadata.imeTabele).append("_").append(ime).append(quote).append(",");

            }
            select.append("\n");
        }
        for (Field f : polja) {
            if (f.isAnnotationPresent(ManyToOne.class)) {
                if (poseceneTabele.contains(f.getType())) {
                    continue;
                }
                poseceneTabele.add((Class<T>)f.getType());
                nextLvl.add(f.getType());
                ManyToOne ano = f.getAnnotation(ManyToOne.class);
                var m = EntityCache.getMetadata((Class<T>)f.getType());
                String tbl = m.imeTabele;
                from.append("LEFT JOIN ").append(quote).append(tbl).append(quote).append(" ON ")
                        .append(quote).append(tbl).append(quote).append(".").append(quote).append(m.primarniKljucevi.get(0).getName()).append(quote)
                        .append("=").append(quote).append(rtbl).append(quote).append(".").append(quote).append(ano.joinColumn()).append(quote).append(" \n");
            } else if (f.isAnnotationPresent(OneToMany.class)) {

                Class<T> y = (Class<T>) (((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0]);
                if (poseceneTabele.contains(y)) {
                    continue;
                }
                poseceneTabele.add(y);
                nextLvl.add(y);
                OneToMany ano = f.getAnnotation(OneToMany.class);
                var m = EntityCache.getMetadata((Class<T>)y);
                String tbl = m.imeTabele;
                try {
                    Field f2 = y.getDeclaredField(ano.mappedBy());
                    if (!f2.isAnnotationPresent(ManyToOne.class)) {
                        throw new Exception("Strane mapiranja se ne poklapaju");
                    }
                    String fk = f2.getAnnotation(ManyToOne.class).joinColumn();
                    from.append("LEFT JOIN ").append(quote).append(tbl).append(quote).append(" ON ")
                            .append(quote).append(tbl).append(quote).append(".").append(quote).append(fk).append(quote)
                            .append("=").append(quote).append(rtbl).append(quote).append(".").append(quote).append(metadata.primarniKljucevi.get(0).getName()).append(quote).append(" \n");

                } catch (Exception ex) {
                    throw new RuntimeException("Lose definisiano mapiranje za OneToMany referencu " + y.getName() + " klase " + root.getName() + ": " + ex.getLocalizedMessage());
                }
            }
        }
        for (var c : nextLvl) {
            var m = EntityCache.getMetadata((Class<T>)c);
            for (Field f : m.svaPolja) {
                if (f.isAnnotationPresent(OneToMany.class)) {
                    continue;
                }
                String ime = vratiImePolja(f);
                select.append(quote).append(m.imeTabele).append(quote).append(".").append(ime).append(" AS ").append(quote).append(m.imeTabele).append("_").append(ime).append(quote).append(",");
            }
            select.append("\n");
            generateJoinString((Class<T>)c, select, from, poseceneTabele);
        }

    }
}
