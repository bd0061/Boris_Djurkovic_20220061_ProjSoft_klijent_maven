/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler;

import framework.model.network.NetworkRequest;
import framework.model.network.NetworkResponse;
import iznajmljivanjeapp.domain.Dozvola;
import iznajmljivanjeapp.domain.Vozac;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentListener;
import network.RequestManager;
import util.DocumentListenerFactory;
import util.UI.DugmeUtils;
import view.VozacView;

/**
 *
 * @author Djurkovic
 */
public class VozacKontroler {

    private VozacView view;

    private boolean promena;

    private Vozac vozac;
    private List<Dozvola> dozvole;

    private boolean mailValid = false;

    private Pattern p = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"); //regex za mejl format

    public VozacKontroler(VozacView view) {
        this(view, false, null);
    }

    public VozacKontroler(VozacView view, boolean promena, Vozac vozac) {
        this.view = view;
        this.promena = promena;

        this.view.setIdVisible(promena);
        this.view.setObrisiVisible(promena);

        if (!promena) {
            this.vozac = new Vozac();
        } else {
            this.vozac = vozac;
            this.view.getTxtIme().setText(vozac.getIme());
            this.view.getTxtPrezime().setText(vozac.getPrezime());
            this.view.getTxtEmail().setText(vozac.getEmail());
            this.view.getTxtId().setText(vozac.getId().toString());
            this.view.getLblGlavni().setText("   Promena vozača");
            this.view.setTitle("Promena vozača");
            this.view.setKreirajEnabled(true);
            mailValid = true;
        }

        try {
            NetworkResponse resp = RequestManager.sendRequest(new NetworkRequest("dozvola/vrati_sve", null));
            if (!resp.success) {
                JOptionPane.showMessageDialog(null, "Sistem ne može da kreira vozača.", "Greška", JOptionPane.ERROR_MESSAGE);
            }
            dozvole = (List<Dozvola>) resp.payload;

            if (dozvole.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Sistem ne može da " + (promena ? "nađe" : "kreira") + " vozača.", "Greška", JOptionPane.ERROR_MESSAGE);
                view.dispose();
                return;
            }
            List<String> d = new ArrayList<>();
            for (var doz : dozvole) {
                if (doz.getKategorija() == null) {
                    continue;
                }
                d.add(doz.getKategorija() + "");
            }
            this.view.napuniComboBox(d);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Sistem ne može da " + (promena ? "nađe" : "kreira") + " vozača.", "Greška", JOptionPane.ERROR_MESSAGE);
            view.dispose();
            return;
        }

        if (!promena) {
            JOptionPane.showMessageDialog(null, "Sistem je kreirao vozača.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
        }

        if (promena) {
            this.view.getComboBoxDozvole().setSelectedItem(vozac.getDozvola().getKategorija().toString());
        }

        this.view.addImeListener(DocumentListenerFactory.create(d -> proveriPolja()));
        this.view.addPrezimeListener(DocumentListenerFactory.create(d -> proveriPolja()));
        this.view.addEmailListener(DocumentListenerFactory.create(d -> proveriPolja()));
        this.view.addEmailListener(DocumentListenerFactory.create(d -> proveriMail()));


        Supplier<Vozac> vozacFactory = () -> {
            Dozvola izabranaDozvola = null;
            for (var d : dozvole) {
                if (d.getKategorija().toString().equals(view.getComboBoxDozvole().getSelectedItem())) {
                    izabranaDozvola = d;
                    break;
                }
            }
            if (izabranaDozvola == null) {
                return null;
            }
            if (promena) {
                return new Vozac(vozac.getId(), view.getTxtIme().getText(), view.getTxtPrezime().getText(), view.getTxtEmail().getText(), izabranaDozvola);
            } else {
                return new Vozac(view.getTxtIme().getText(), view.getTxtPrezime().getText(), view.getTxtEmail().getText(), izabranaDozvola);
            }
        };
        
        DugmeUtils.<Vozac>registrujZapamti(view.getBtnKreiraj(),vozacFactory,"vozac","vozača",promena,view);
        DugmeUtils.<Vozac>registrujObrisi(view.getBtnObrisi(),vozac,"vozac/obrisi","vozača",view);

    }



    private void proveriMail() {
        Matcher m = p.matcher(view.getTxtEmail().getText());
        mailValid = m.matches();
        if (mailValid) {
            view.setError("");

        } else if (!view.getTxtEmail().getText().isEmpty()) {
            view.setError("Mejl mora biti odgovarajućeg formata.");
        } else {
            view.setError("");
        }
    }

    private void proveriPolja() {
        if (view.getTxtEmail().getText().isEmpty() || view.getTxtIme().getText().isEmpty() || view.getTxtPrezime().getText().isEmpty()) {
            view.setKreirajEnabled(false);
        } else {
            view.setKreirajEnabled(mailValid);
        }
    }

}
