/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler;

import iznajmljivanjeapp.domain.Iznajmljivanje;
import iznajmljivanjeapp.domain.Zaposleni;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import model.LoginModel;
import view.GlavniMeniView;
import view.IznajmljivanjeView;
import view.LoginView;
import view.StatistikaPrikaz;
import view.VozacView;
import view.VoziloView;
import view.ZaposleniView;
import view.richdialogs.DodajSmene;
import view.richdialogs.KriterijumIznajmljivanje;
import view.richdialogs.KriterijumVozac;
import view.richdialogs.KriterijumVozilo;
import view.richdialogs.KriterijumZaposleni;
import view.richdialogs.ObrisiSmene;
import view.richdialogs.Podesavanja;
import view.richdialogs.PromenaPodataka;

/**
 *
 * @author Djurkovic
 */
public class GlavniMeniKontroler {

    
    private final Map<YearMonth, List<Iznajmljivanje>> iznajmljivanjeCache = new HashMap<>();
    
    private GlavniMeniView view;
    private Zaposleni zaposleni;

    public GlavniMeniKontroler(GlavniMeniView view, Zaposleni zaposleni) {
        this.view = view;
        this.zaposleni = zaposleni;
        this.view.setUlogovanoIme(zaposleni.getIme() + " " + zaposleni.getPrezime());
        this.view.addLogoutListener(new LogOutListener());
        this.view.addOProgramuListener(new OProgramuListener());
        this.view.addPodesavanjaListener(new PodesavanjaListener());
        this.view.setSmenaVisible(zaposleni.isAdmin());
        this.view.setMenuItemZaposleniKreirajVisible(zaposleni.isAdmin());
        this.view.setMenuItemZaposleniPretraziVisible(zaposleni.isAdmin());
        this.view.setMenuItemZaposleniPromeniPodatkeVisible(!zaposleni.isAdmin());
        this.view.addMenuItemVozacKreirajListener(new MenuItemVozacKreirajListener());
        this.view.addMenuItemVozacPregledListener(new MenuItemVozacPregledListener());
        this.view.addMenuItemVoziloKreirajListener(new MenuItemVoziloKreirajListener());
        this.view.setAdmin(zaposleni.isAdmin());
        this.view.addMenuItemVoziloPretraziListener(new MenuItemVoziloPretraziListener());

        
        view.setStatistikaVisible(zaposleni.isAdmin());
        view.addStatistikaListener(e -> {
            YearMonth ym = YearMonth.now();
            new StatistikaPrikaz(null, ym.getMonthValue(), ym.getYear(),iznajmljivanjeCache).setVisible(true);
        });
        
        view.addDodeliSmeneListener(e -> new DodajSmene(view,true).setVisible(true));
        view.addMenuItemObrisiSmeneListener(e -> new ObrisiSmene(view,true).setVisible(true));
        
        view.addMenuItemZaposleniKreirajListener((e) -> {
            ZaposleniView v = new ZaposleniView(view, true);
            ZaposleniKontroler k = new ZaposleniKontroler(v, false, null);
            v.setVisible(true);
        });

        view.addMenuItemZaposleniPretraziListener((e) -> {
            new KriterijumZaposleni(view, true).setVisible(true);
        });

        view.addMenuItemZaposleniPromenaPodatakaListener(e -> {
            var pp = new PromenaPodataka(view, true, zaposleni);
            pp.setVisible(true);

        });
        
        view.addMenuItemIznajmljivanjeKreirajListener( e -> {
            IznajmljivanjeView v = new IznajmljivanjeView(view,true);
            Iznajmljivanje i = new Iznajmljivanje();
            i.setZaposleni(zaposleni);
            IznajmljivanjeKontroler k = new IznajmljivanjeKontroler(v,false,i);
        
        });
        
        view.addMenuItemIznajmljivanjePretraziActionListener(e -> {
            new KriterijumIznajmljivanje(view,true).setVisible(true);
        
        
        
        
        });
    }

    private class MenuItemVoziloPretraziListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new KriterijumVozilo(view, true).setVisible(true);
        }

    }

    private class MenuItemVozacPregledListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new KriterijumVozac(view, true).setVisible(true);
        }

    }

    private class LogOutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int choice = JOptionPane.showConfirmDialog(
                    null,
                    "Da li ste sigurni da želite da se odlogujete?",
                    "Potvrda izlaska",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (choice == JOptionPane.YES_OPTION) {
                view.dispose();
                LoginView v = new LoginView();
                LoginModel m = new LoginModel();
                LoginKontroler lk = new LoginKontroler(v, m);
                v.setEmail(zaposleni.getEmail());
                v.setVisible(true);
            }
        }

    }

    private class MenuItemVoziloKreirajListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            VoziloView kview = new VoziloView(view, true);
            VoziloKontroler kkontroler = new VoziloKontroler(kview);
            kview.setVisible(true);
        }

    }

    private class MenuItemVozacKreirajListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            VozacView kview = new VozacView(view, true);
            VozacKontroler kkontroler = new VozacKontroler(kview);
            kview.setVisible(true);
        }

    }

    private class OProgramuListener implements MenuListener {

        @Override
        public void menuSelected(MenuEvent e) {
            JOptionPane.showMessageDialog(null, "Sistem za iznajmljivanje vozila\nVerzija: 1.0.0\nAutor: Boris Đurković", "O Programu", JOptionPane.INFORMATION_MESSAGE);
        }

        @Override
        public void menuDeselected(MenuEvent e) {

        }

        @Override
        public void menuCanceled(MenuEvent e) {

        }
    }

    private class PodesavanjaListener implements MenuListener {

        @Override
        public void menuSelected(MenuEvent e) {
            new Podesavanja(view, true).setVisible(true);
        }

        @Override
        public void menuDeselected(MenuEvent e) {

        }

        @Override
        public void menuCanceled(MenuEvent e) {

        }
    }
}
