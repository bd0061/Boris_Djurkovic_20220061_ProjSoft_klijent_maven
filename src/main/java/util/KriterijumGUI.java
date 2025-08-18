/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.component to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import com.toedter.calendar.JDateChooser;
import framework.model.KriterijumDescriptor;
import framework.orm.Entitet;
import iznajmljivanjeapp.domain.enumeracije.KategorijaEnum;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author Djurkovic
 */
public class KriterijumGUI {

    private Kriterijum tipKriterijuma;
    private JComboBox<?> cb;
    private JComponent component;
    private JCheckBox ck;
    private Class<?> klasa;
    private String atributIme;

    private static final Map<String, KategorijaEnum> kategorijaMap = Map.of("Budžet", KategorijaEnum.BUDZET, "Luksuz", KategorijaEnum.LUKSUZ, "Srednja", KategorijaEnum.SREDNJA);

    private static final List<String> gtlt = List.of(">","<");
    private static final Map<String, String> stringMap = Map.of("Jednako", "=", "Različito od", "!=", "Sadrži", "LIKE", "Pripada", "IN");
    private static final Map<String, String> brojdateMap = Map.of("Jednako", "=", "Različito od", "!=", "Veće od", ">", "Manje od", "<");

    public Kriterijum getTipKriterijuma() {
        return tipKriterijuma;
    }

    public JCheckBox getCk() {
        return ck;
    }

    public void setCk(JCheckBox ck) {
        this.ck = ck;
    }

    public void setTipKriterijuma(Kriterijum tipKriterijuma) {
        this.tipKriterijuma = tipKriterijuma;
    }

    public JComboBox<?> getCb() {
        return cb;
    }

    public void setCb(JComboBox<?> cb) {
        this.cb = cb;
    }

    public JComponent getComponent() {
        return component;
    }

    public void setComponent(JComponent component) {
        this.component = component;
    }

    public Class<?> getKlasa() {
        return klasa;
    }

    public void setKlasa(Class<?> klasa) {
        this.klasa = klasa;
    }

    public String getAtributIme() {
        return atributIme;
    }

    public void setAtributIme(String atributIme) {
        this.atributIme = atributIme;
    }

    public KriterijumGUI(Kriterijum tipKriterijuma, JComboBox<?> cb, JComponent txt, Class<?> klasa, String atributIme, JCheckBox ck) {
        this.tipKriterijuma = tipKriterijuma;
        this.cb = cb;
        this.component = txt;
        this.klasa = klasa;
        this.atributIme = atributIme;
        this.ck = ck;
    }

    public static List<KriterijumDescriptor> processKriterijums(List<KriterijumGUI> krits) throws Exception {
        List<KriterijumDescriptor> kds = new ArrayList<>();
        for (var k : krits) {
            if (!Entitet.class.isAssignableFrom(k.getKlasa())) {
                throw new Exception("Losa klasa");
            }
            if (k.getComponent() instanceof JTextField txt) {
                if (txt.getText().isEmpty()) {
                    continue;
                }

                Object val;
                String op;
                if (k.getTipKriterijuma() == Kriterijum.STRING || k.getTipKriterijuma() == Kriterijum.CHAR || k.getTipKriterijuma() == Kriterijum.KATEGORIJAENUM) {
                    op = stringMap.get(k.getCb().getSelectedItem());

                    if (op == null) {
                        throw new Exception("Losa operacija");
                    }

                    if (op.equals("LIKE")) {
                        txt.setText(txt.getText().replace("%", "\\%").replace("_", "\\_").replace("\\", "\\\\"));
                        val = "%" + txt.getText() + "%";
                    } else if (op.equals("IN")) {
                        val = Arrays.asList(txt.getText().split(","));
                        if (k.getTipKriterijuma() == Kriterijum.CHAR) {
                            List<Character> c = new ArrayList<>();
                            for (var s : (List<String>) val) {
                                c.add(s.charAt(0));
                            }
                            val = c;
                        } else if (k.getTipKriterijuma() == Kriterijum.KATEGORIJAENUM) {
                            List<KategorijaEnum> e = new ArrayList<>();
                            for (var s : (List<String>) val) {
                                var ke = kategorijaMap.get(s);
                                if (ke == null) {
                                    throw new Exception("los parametar");
                                }
                                e.add(ke);
                            }
                            val = e;
                        }
                    } else {
                        val = txt.getText();
                        if (k.getTipKriterijuma() == Kriterijum.CHAR && val instanceof String s) {

                            if (s.length() == 1) {
                                val = s.charAt(0);
                            }
                        } else if (k.getTipKriterijuma() == Kriterijum.KATEGORIJAENUM && val instanceof String s) {
                            System.out.println("?");
                            var ke = kategorijaMap.get(s);
                            if (ke == null) {
                                throw new Exception("los parametar");
                            }
                            val = ke;
                        }
                    }
                } else if (k.getTipKriterijuma() == Kriterijum.INTEGER || k.getTipKriterijuma() == Kriterijum.DECIMAL) {
                    op = brojdateMap.get(k.getCb().getSelectedItem());
                    if (op == null) {
                        throw new Exception("Losa operacija");
                    }
                    if (k.getTipKriterijuma() == Kriterijum.DECIMAL) {
                        val = Double.parseDouble(txt.getText());
                    } else {
                        val = Integer.parseInt(txt.getText());
                    }
                } else {
                    throw new Exception("Nepodrzan kriterijum");
                }

                kds.add(new KriterijumDescriptor(k.getCk() == null ? false : k.getCk().isSelected(), (Class<? extends Entitet>) k.getKlasa(), k.getAtributIme(), op, val));
            } else if (k.getComponent() instanceof JDateChooser dc) {
                if (dc.getDate() == null) {
                    continue;
                }
                if (k.getTipKriterijuma() != Kriterijum.DATE) {
                    throw new Exception("Nepodrzan kriterijum");
                }
                String op = brojdateMap.get(k.getCb().getSelectedItem());
                System.out.println("op je: " + op);

                if (op == null || !gtlt.contains(op)) {
                    throw new Exception("Losa operacija");
                }
                kds.add(new KriterijumDescriptor(false, (Class<? extends Entitet>) k.getKlasa(), k.getAtributIme(), op, dc.getDate()));

            }
        }
        return kds;
    }

}
