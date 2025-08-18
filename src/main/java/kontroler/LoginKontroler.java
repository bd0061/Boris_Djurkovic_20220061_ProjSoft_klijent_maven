/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler;

import iznajmljivanjeapp.domain.Zaposleni;
import framework.model.network.NetworkResponse;
import framework.simplelogger.LogLevel;
import framework.simplelogger.SimpleLogger;
import view.LoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentListener;
import model.LoginModel;
import util.DocumentListenerFactory;
import view.GlavniMeniView;

/**
 *
 * @author Djurkovic
 */
public class LoginKontroler {

    private LoginView view;
    private LoginModel model;
    private boolean mailValid = false;
    private Pattern p = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"); //regex za mejl format

    public LoginKontroler(LoginView view, LoginModel model) {

        this.view = view;
        this.model = model;

        this.view.addLoginListener(new LoginListener());
        this.view.addEmailListener(DocumentListenerFactory.create(e -> proveraPolja()));
        this.view.addEmailListener(DocumentListenerFactory.create(e -> proveraEmail()));
        this.view.addSifraListener(DocumentListenerFactory.create(e -> proveraPolja()));

    }
    
    

    private void proveraPolja() {
        if (view.getTxtEmail().getText().isEmpty() || new String(view.getTxtSifra().getPassword()).isEmpty()) {
            view.setLoginButtonEnabled(false);
        } else {
            view.setLoginButtonEnabled(mailValid);
        }
    }

    private void proveraEmail() {
        Matcher m = p.matcher(view.getTxtEmail().getText());
        mailValid = m.matches();
        if (mailValid) {
            view.setError("");
        } else if (!view.getTxtEmail().getText().isEmpty()) {
            view.setError("Mejl mora biti odgovarajućeg formata.");
        }
        else {
            view.setError("");
        }
    }

    private class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                NetworkResponse r = model.login(view.getTxtEmail().getText(), new String(view.getTxtSifra().getPassword()));
                if (r.success) {
                    JOptionPane.showMessageDialog(null, r.responseMessage, "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    SimpleLogger.log(LogLevel.LOG_INFO, "Uspesan zahtev prijave");
                    Zaposleni z = (Zaposleni) r.payload;
                    view.dispose();
                    GlavniMeniView newview = new GlavniMeniView();
                    GlavniMeniKontroler newkontroler = new GlavniMeniKontroler(newview, z);
                    newview.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, r.responseMessage, "Greška", JOptionPane.ERROR_MESSAGE);
                    SimpleLogger.log(LogLevel.LOG_ERROR, "Neuspesan zahtev prijave");
                }
            } catch (Exception ex) {
                SimpleLogger.log(LogLevel.LOG_ERROR, "Neuspesan zahtev prijave: " + ex);
                JOptionPane.showMessageDialog(null, "Došlo je do nepoznate greške pri obradi prijave. Molimo pokušajte kasnije.", "Greška", JOptionPane.ERROR_MESSAGE);
            }

        }

    }

}
