/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler;

import com.formdev.flatlaf.FlatLightLaf;
import iznajmljivanjeapp.domain.Zaposleni;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import util.DocumentListenerFactory;
import util.UI.DugmeUtils;
import view.ZaposleniView;

/**
 *
 * @author Djurkovic
 */
public class ZaposleniKontroler {

    private boolean promena;
    private ZaposleniView view;
    private Zaposleni zaposleni;

    private boolean mailValid = false;
    private boolean lengthValid = false;

    private Pattern p = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"); //regex za mejl format

    public ZaposleniKontroler(ZaposleniView view, boolean promena, Zaposleni zaposleni) {
        this.view = view;
        this.promena = promena;

        view.setIdVisible(promena);
        view.setObrisiVisible(promena);
        view.getCkSifra().setVisible(promena);
        
        view.getCkSifra().addActionListener(e -> {
            view.getTxtSifra().setEnabled(!view.getCkSifra().isSelected());
            proveriPolja();
            proveriDuzinu();
            
        });
        
        view.setZapamtiEnabled(false);
        view.getLblErrorEmail().setVisible(false);
        view.getLblErrorSifraLength().setVisible(false);

        if (!promena) {
            this.zaposleni = new Zaposleni();
            view.setTitle("Kreiraj zaposlenog");
            JOptionPane.showMessageDialog(null, "Sistem je kreirao zaposlenog.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
        } else {
            this.zaposleni = zaposleni;
            mailValid = true;
            view.getTxtIme().setText(zaposleni.getIme());
            view.getTxtPrezime().setText(zaposleni.getPrezime());
            view.getTxtEmail().setText(zaposleni.getEmail());
            view.getLblSifra().setText("Nova šifra:");
            view.setTitle("Promena zaposlenog");
            view.getLblGlavni().setText("Promena zaposlenog");
            view.getTxtId().setText(zaposleni.getId().toString());
            view.getCkAdmin().setSelected(zaposleni.isAdmin());
        }

        this.view.addImeListener(DocumentListenerFactory.create(d -> proveriPolja()));
        this.view.addPrezimeListener(DocumentListenerFactory.create(d -> proveriPolja()));
        this.view.addEmailListener(DocumentListenerFactory.create(d -> proveriPolja()));
        this.view.addEmailListener(DocumentListenerFactory.create(d -> proveriMail()));
        this.view.addSifraListener(DocumentListenerFactory.create(d -> proveriPolja()));
        this.view.addSifraListener(DocumentListenerFactory.create(d -> proveriDuzinu()));
        
        
        Supplier<Zaposleni> zaposleniFactory = () -> {
                Zaposleni z = new Zaposleni(promena ? zaposleni.getId() : null , view.getTxtIme().getText(),view.getTxtPrezime().getText(), view.getTxtEmail().getText(), (promena && view.getCkSifra().isSelected() ? null : view.getTxtSifra().getText()));
                z.setAdmin(view.getCkAdmin().isSelected());
                return z;
        };
        DugmeUtils.<Zaposleni>registrujZapamti(view.getBtnZapamti(),zaposleniFactory,"zaposleni","zaposlenog",promena,view);
        DugmeUtils.<Zaposleni>registrujObrisi(view.getBtnObrisi(),zaposleni,"zaposleni/obrisi","zaposlenog",view);
        
    }

    private void proveriPolja() {
        if (view.getTxtEmail().getText().isEmpty() 
                || view.getTxtIme().getText().isEmpty() 
                || view.getTxtPrezime().getText().isEmpty() 
                || (!view.getCkSifra().isSelected() && view.getTxtSifra().getText().isEmpty())) {
            view.setZapamtiEnabled(false);
        } else {
            view.setZapamtiEnabled(mailValid && (view.getCkSifra().isSelected() || lengthValid));
        }
    }

    private void proveriMail() {
        Matcher m = p.matcher(view.getTxtEmail().getText());
        mailValid = m.matches();
        if (!mailValid || view.getTxtEmail().getText().isEmpty()) {
            view.getLblErrorEmail().setVisible(true);
        } else {
            view.getLblErrorEmail().setVisible(false);
        }
    }

    private void proveriDuzinu() {
        lengthValid = view.getCkSifra().isSelected() || view.getTxtSifra().getText().length() >= 8;
        if (!lengthValid && !view.getTxtSifra().getText().isEmpty()) {
            view.getLblErrorSifraLength().setVisible(true);
        } else {
            view.getLblErrorSifraLength().setVisible(false);
        }

    }

    public static void main(String[] args) {
        try {
            UIManager.installLookAndFeel("FlatLaf Light", "com.formdev.flatlaf.FlatLightLaf");
            UIManager.installLookAndFeel("FlatLaf Dark", "com.formdev.flatlaf.FlatDarkLaf");
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        ZaposleniView v = new ZaposleniView(null, true);
        ZaposleniKontroler k = new ZaposleniKontroler(v, false, null);
        v.setVisible(true);
    }

}
