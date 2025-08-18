/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.tablemodel;

import framework.orm.Entitet;
import framework.orm.EntityManager;
import framework.orm.anotacije.NePrikazuj;
import framework.orm.anotacije.Transient;
import framework.orm.anotacije.kljuc.ManyToOne;
import framework.orm.anotacije.kljuc.OneToMany;
import framework.orm.anotacije.kljuc.PrimarniKljuc;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import util.DateUtil;
import util.StringUtil;

/**
 *
 * @author Djurkovic
 */
public class EntityTableModelFactory {

    
    
    private static <T extends Entitet> Kolona<T> vratiKolonu(Class<T> glavnaKlasa, Field f) throws Exception {
        String imeKolone = StringUtil.camelCaseToSentence(EntityManager.vratiImePolja(f));
        String imeGetera = (f.getType().equals(boolean.class) || f.getType().equals(Boolean.class) ? "is" : "get") + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
        Method m = glavnaKlasa.getDeclaredMethod(imeGetera);
        Kolona<T> kol = new Kolona(imeKolone, entitetInstance -> {
            try {
                return m.invoke(entitetInstance);
            } catch (Exception ex) {
                throw new RuntimeException("Exception u lambdi: " + ex);
            }
        });
        return kol;
    }

    public static <T extends Entitet> EntityTableModel<T> create(Class<T> clazz, List<T> entiteti) throws Exception {
        Field[] fs = clazz.getDeclaredFields();
        List<Kolona<T>> kolone = new ArrayList<>();

        for (Field f : fs) {
            if (f.isAnnotationPresent(Transient.class) || f.isAnnotationPresent(OneToMany.class) || f.isAnnotationPresent(NePrikazuj.class) || Modifier.isStatic(f.getModifiers())) {
                continue;
            }

            if (f.isAnnotationPresent(ManyToOne.class)) {
                ManyToOne ano = f.getAnnotation(ManyToOne.class);

                for (String s : ano.poljaZaPrikazivanje()) {
                    System.out.println("novo: " + s);
                    Class<?> klasaSpoljnogKljuca = f.getType();
                    if (!Entitet.class.isAssignableFrom(klasaSpoljnogKljuca)) {
                        throw new Exception("Los spoljni kljuc: " + f.getName());
                    }

                    String imeGeteraSpoljnogKljuca = "get" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);

                    Method spoljniKljucGeter = clazz.getDeclaredMethod(imeGeteraSpoljnogKljuca);

                    String[] ss = s.split(":");
                    Field poljeUSpoljnomKljucu = klasaSpoljnogKljuca.getDeclaredField(ss.length >= 2 ? ss[0] : s);
                    
                    String imeKolone = ss.length >= 2 ? ss[1] : StringUtil.camelCaseToSentence(EntityManager.vratiImePolja(poljeUSpoljnomKljucu));
                    System.out.println("ime kolone je " + imeKolone);
                    String imeGetera = (poljeUSpoljnomKljucu.getType().equals(boolean.class) || poljeUSpoljnomKljucu.getType().equals(Boolean.class) ? "is" : "get") + poljeUSpoljnomKljucu.getName().substring(0, 1).toUpperCase() + poljeUSpoljnomKljucu.getName().substring(1);

                    Method m = klasaSpoljnogKljuca.getDeclaredMethod(imeGetera);

                    Kolona<T> kol = new Kolona(imeKolone, entitetInstance -> {
                        try {
                            Object fk = spoljniKljucGeter.invoke(entitetInstance);
                            return m.invoke(klasaSpoljnogKljuca.cast(fk));
                        } catch (Exception ex) {
                            throw new RuntimeException("Exception u lambdi: " + ex);
                        }
                    });
                    kolone.add(kol);

                }

            } else {
                
                //tehnicki mozemo samo f.setAccessible(true); return f.get(entitetInstance) ali time bi prekrsili enkapsulaciju 
                //zato idemo preko getera(sporije) i pretpostavljamo da geteri imaju standardna imena kakva obicno generise IDE
                
                String imeKolone = StringUtil.camelCaseToSentence(EntityManager.vratiImePolja(f));
                String imeGetera = (f.getType().equals(boolean.class) || f.getType().equals(Boolean.class) ? "is" : "get") + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
                Method m = clazz.getDeclaredMethod(imeGetera);
                Kolona<T> kol = new Kolona(imeKolone, entitetInstance -> {
                    try {
                        Object val = m.invoke(entitetInstance);
                        return val instanceof java.util.Date d ? DateUtil.formatDate(d) : val;
                    } catch (Exception ex) {
                        throw new RuntimeException("Exception u lambdi: " + ex);
                    }
                });
                kolone.add(kol);
            }

        }

        return new EntityTableModel<T>(entiteti == null ? new ArrayList<T>() : entiteti, kolone);
    }
}
