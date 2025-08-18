/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler;

import framework.model.network.NetworkRequest;
import framework.model.network.NetworkResponse;
import iznajmljivanjeapp.domain.Vozilo;
import iznajmljivanjeapp.domain.enumeracije.KategorijaEnum;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentListener;
import network.RequestManager;
import util.DocumentListenerFactory;
import util.UI.DugmeUtils;
import view.VoziloView;

/**
 *
 * @author Djurkovic
 */
public class VoziloKontroler {

    private boolean promena;
    private VoziloView view;

    private static final Map<KategorijaEnum, String> map = Map.of(KategorijaEnum.BUDZET, "Budžet", KategorijaEnum.LUKSUZ, "Luksuz", KategorijaEnum.SREDNJA, "Srednja");
    private static final Map<String, KategorijaEnum> map2 = Map.of("Budžet", KategorijaEnum.BUDZET, "Luksuz", KategorijaEnum.LUKSUZ, "Srednja", KategorijaEnum.SREDNJA);

    private Vozilo vozilo;

    public VoziloKontroler(VoziloView view) {
        this(view, false, null);
    }

    public VoziloKontroler(VoziloView view, boolean promena, Vozilo vozilo) {
        this.view = view;
        this.promena = promena;

        view.setIdVisible(promena);
        view.setObrisiVisible(promena);
        view.setKreirajEnabled(promena);

        List<Integer> godine = new ArrayList<>();
        for (int i = 2004; i <= Year.now().getValue(); i++) {
            godine.add(i);
        }
        view.setGodiste(godine);

        if (!promena) {
            this.vozilo = new Vozilo();
            view.setTitle("Kreiraj vozilo");
            JOptionPane.showMessageDialog(null, "Sistem je kreirao vozilo.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
        } else {
            this.vozilo = vozilo;
            view.getCbKlasa().setSelectedItem(vozilo.getKlasa());
            view.getCbProizvodjac().setSelectedItem(vozilo.getProizvodjac());
            view.getTxtKupovnaCena().setText(vozilo.getKupovnaCena().toString());
            view.getCbGodiste().setSelectedItem(vozilo.getGodiste());
            view.getTxtImeModela().setText(vozilo.getImeModela());
            view.getCbKategorija().setSelectedItem(map.get(vozilo.getKategorija()));
            view.getLblGlavni().setText("  Promena vozila");
            view.setTitle("Promena vozila");

            view.getTxtId().setText(vozilo.getId().toString());

        }

        view.addKupovnaCenaListener(DocumentListenerFactory.create(c -> proveriPolja()));
        view.addImeModelaListener(DocumentListenerFactory.create(c -> proveriPolja()));
        view.addKlasaListener(new KlasaListener());
        view.addProizvodjacListener(new ProizvodjacListener());

        Supplier<Vozilo> voziloFactory = () -> {
            if (promena) {
                return new Vozilo(
                        vozilo.getId(), (String) (view.getCbKlasa().getSelectedItem()), (String) (view.getCbProizvodjac().getSelectedItem()),
                        Double.parseDouble(view.getTxtKupovnaCena().getText()),
                        (Integer) (view.getCbGodiste().getSelectedItem()), view.getTxtImeModela().getText(),
                        map2.get((String) (view.getCbKategorija().getSelectedItem())));
            } else {
                return new Vozilo(
                        (String) (view.getCbKlasa().getSelectedItem()), (String) (view.getCbProizvodjac().getSelectedItem()),
                        Double.parseDouble(view.getTxtKupovnaCena().getText()),
                        (Integer) (view.getCbGodiste().getSelectedItem()), view.getTxtImeModela().getText(),
                        map2.get((String) (view.getCbKategorija().getSelectedItem())));
            }
        };
        
        DugmeUtils.<Vozilo>registrujZapamti(view.getBtnZapamti(),voziloFactory,"vozilo","vozilo",promena,view);
        DugmeUtils.<Vozilo>registrujObrisi(view.getBtnObrisi(),vozilo,"vozilo/obrisi","vozilo",view);

    }

    private void proveriPolja() {
        view.setKreirajEnabled(!(view.getTxtImeModela().getText().isEmpty() || view.getTxtKupovnaCena().getText().isEmpty()));
    }



    private static boolean comboBoxContains(JComboBox<?> comboBox, Object item) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            if (item.equals(comboBox.getItemAt(i))) {
                return true;
            }
        }
        return false;
    }

    private class KlasaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (((String) view.getCbKlasa().getSelectedItem()).equals("Motor")) {
                view.getCbProizvodjac().removeItem("Dacia");
            } else {
                if (!comboBoxContains(view.getCbProizvodjac(), "Dacia")) {
                    view.getCbProizvodjac().addItem("Dacia");
                }
            }
        }

    }

    private class ProizvodjacListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (((String) view.getCbProizvodjac().getSelectedItem()).equals("Dacia")) {
                view.getCbKlasa().removeItem("Motor");
            } else {
                if (!comboBoxContains(view.getCbKlasa(), "Motor")) {
                    view.getCbKlasa().addItem("Motor");
                }
            }
        }

    }

    public static void main(String[] args) {
        VoziloView view = new VoziloView(null, true);
        VoziloKontroler kont = new VoziloKontroler(view);
        view.setVisible(true);
    }

}
