/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler;

import framework.model.KriterijumDescriptor;
import framework.model.KriterijumWrapper;
import framework.model.network.NetworkRequest;
import framework.model.network.NetworkResponse;
import framework.orm.Entitet;
import framework.simplelogger.LogLevel;
import framework.simplelogger.SimpleLogger;
import iznajmljivanjeapp.domain.Iznajmljivanje;
import iznajmljivanjeapp.domain.StavkaIznajmljivanja;
import iznajmljivanjeapp.domain.Vozac;
import iznajmljivanjeapp.domain.Vozilo;
import iznajmljivanjeapp.domain.Zaposleni;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentListener;
import model.tablemodel.EntityTableModel;
import model.tablemodel.EntityTableModelFactory;
import network.RequestManager;
import util.DateUtil;
import util.DocumentListenerFactory;
import view.IznajmljivanjeView;
import static util.DateUtil.stripTime;
import static util.DateUtil.daysBetween;
import util.UI.DugmeUtils;
import util.UI.ShortCutUtils;
import view.richdialogs.PromenaStavke;

/**
 *
 * @author Djurkovic
 */
public class IznajmljivanjeKontroler {

    private IznajmljivanjeView view;
    private boolean promena;

    private Zaposleni z;
    private Iznajmljivanje iznajmljivanje;

    private Stack<Map<Integer, StavkaIznajmljivanja>> undoBuffer = new Stack<>();
    private Stack<Map<Integer, StavkaIznajmljivanja>> redoBuffer = new Stack<>();

    private boolean mailValid = false;
    private boolean datumValid = true;
    private int rb = 1;

    private Pattern p = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"); //regex za mejl format

    private List<Vozilo> vozila;

    public IznajmljivanjeKontroler(IznajmljivanjeView view, boolean promena, Iznajmljivanje iznajmljivanje) {
        this.view = view;
        this.promena = promena;
        view.setIdVisible(promena);
        view.setObrisiVisible(promena);
        view.setKreirajEnabled(promena);
        view.setMailError(false);
        view.setDodajStavkuEnabled(false);
        view.setStavkaError("");
        view.setObrisiStavkeEnabled(false);
        view.setZaposleniInputVisible(promena);
        view.setDatumSklapanjaVisible(promena);
        z = iznajmljivanje.getZaposleni();

        try {
            view.getTblStavke().setModel(EntityTableModelFactory.<StavkaIznajmljivanja>create(StavkaIznajmljivanja.class, !promena ? new ArrayList<StavkaIznajmljivanja>() : iznajmljivanje.getStavke()));
            NetworkResponse resp = RequestManager.sendRequest(new NetworkRequest("vozilo/vrati_sve", new KriterijumWrapper(new ArrayList<>(), KriterijumWrapper.DepthLevel.NONE)));
            if (!resp.success) {
                SwingUtilities.invokeLater(() -> {
                    view.dispose();
                    JOptionPane.showMessageDialog(null, "Sistem ne može da " + (promena ? "nađe" : "kreira ") + "iznajmljivanje", "Greška", JOptionPane.ERROR_MESSAGE);
                });
                return;
            }
            this.vozila = (List<Vozilo>) resp.payload;
            view.setVozila(this.vozila);

        } catch (Exception e) {
            SwingUtilities.invokeLater(() -> {
                view.dispose();
                JOptionPane.showMessageDialog(null, "Sistem ne može da " + (promena ? "nađe" : "kreira ") + "iznajmljivanje", "Greška", JOptionPane.ERROR_MESSAGE);
            });
            return;
        }

        view.getTxtEmailZaposleni().setText(iznajmljivanje.getZaposleni().getEmail());
        if (!promena) {
            this.iznajmljivanje = new Iznajmljivanje();
        } else {
            this.iznajmljivanje = iznajmljivanje;
            view.getTxtId().setText(iznajmljivanje.getId().toString());
            view.getTxtEmail().setText(iznajmljivanje.getVozac().getEmail());
            view.getLblUkupanIznosVal().setText(iznajmljivanje.getUkupanIznos().toString());
            mailValid = true;
            view.setTitle(" Promena iznajmljivanja");
            view.getLblGlavni().setText("     Promena iznajmljivanja");
            view.getDcDatumSklapanja().setDate(iznajmljivanje.getDatumSklapanja());
            rb = iznajmljivanje.getStavke().size() + 1;
        }

        if (!promena) {
            JOptionPane.showMessageDialog(null, "Sistem je kreirao iznajmljivanje.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
        }

        view.addStavkaListener(e -> {
            ((EntityTableModel<StavkaIznajmljivanja>) view.getTblStavke().getModel()).addEntitet(
                    new StavkaIznajmljivanja(null,
                            rb++,
                            stripTime(view.getDcDatumPocetka().getDate()),
                            stripTime(view.getDcDatumZavrsetka().getDate()),
                            Double.parseDouble(view.getTxtIznos().getText()),
                            (Vozilo) (view.getCbVozilo().getSelectedItem())));

            view.getLblUkupanIznosVal().setText(Double.toString(
                    (view.getLblUkupanIznosVal().getText().isEmpty() ? 0.0 : Double.parseDouble(view.getLblUkupanIznosVal().getText()))
                    + Double.parseDouble(view.getTxtIznos().getText())));
            view.setDodajStavkuEnabled(false);
            view.getTxtIznos().setText("");
            proveri();

        });

        view.getTblStavke().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                view.setObrisiStavkeEnabled(view.getTblStavke().getSelectedRows().length > 0);
                view.getBtnPromeniStavku().setEnabled(view.getTblStavke().getSelectedRows().length == 1);
            }
        });

        view.getTxtEmail().getDocument().addDocumentListener(DocumentListenerFactory.create(d -> proveri()));
        view.getTxtEmail().getDocument().addDocumentListener(DocumentListenerFactory.create(d -> proveriMail()));

        view.getTxtEmailZaposleni().getDocument().addDocumentListener(DocumentListenerFactory.create(d -> proveri()));
        view.getTxtEmailZaposleni().getDocument().addDocumentListener(DocumentListenerFactory.create(d -> proveriMail()));

        view.getTxtIznos().getDocument().addDocumentListener(DocumentListenerFactory.create(d -> proveriStavka()));
        view.addDatumPocListener(() -> proveriDatum());
        view.addDatumZavListener(() -> proveriDatum());
        view.getBtnPromeniStavku().setEnabled(false);
        view.addObrisiStavkeListener(e -> {

            obrisiStavke();

        });

        view.getBtnPromeniStavku().addActionListener(e -> {
            EntityTableModel<StavkaIznajmljivanja> m = (EntityTableModel<StavkaIznajmljivanja>) view.getTblStavke().getModel();
            int[] rs = view.getTblStavke().getSelectedRows();
            new PromenaStavke(view.getParent(), true, m.getEntitetAt(rs[0]), vozila, m, view.getLblUkupanIznosVal()).setVisible(true);
        });

        EntityTableModel.centrirajVrednosti(view.getTblStavke());

        ShortCutUtils.addWindowShortcut(view, KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), () -> {
            handleUndo();
            proveri();
        });

        ShortCutUtils.addWindowShortcut(view, KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK), () -> {
            handleRedo();
            proveri();
        });

        view.addDatumSklapanjaListener(() -> {
            System.out.println("pusi kurac");
            proveri();
        }
        );

        view.getBtnZapamti().addActionListener(e -> {
            try {
                NetworkResponse resp = RequestManager.sendRequest(new NetworkRequest("vozac/vrati_sve", new KriterijumWrapper(List.of(new KriterijumDescriptor(Vozac.class, "email", "=", view.getTxtEmail().getText())), KriterijumWrapper.DepthLevel.SHALLOW)));
                if (!resp.success || ((List<Vozac>) resp.payload).isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Sistem ne može da " + (promena ? "promeni" : "kreira") + " iznajmljivanje: ne postoji vozač sa datim mejlom.", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                NetworkResponse respz = null;
                if (promena) {
                    respz = RequestManager.sendRequest(new NetworkRequest("zaposleni/vrati_sve", new KriterijumWrapper(List.of(new KriterijumDescriptor(Zaposleni.class, "email", "=", view.getTxtEmailZaposleni().getText())), KriterijumWrapper.DepthLevel.NONE)));
                    if (!respz.success || ((List<Zaposleni>) respz.payload).isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Sistem ne može da " + (promena ? "promeni" : "kreira") + " iznajmljivanje: ne postoji zaposleni sa datim mejlom.", "Greška", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                List<StavkaIznajmljivanja> stavke = ((EntityTableModel<StavkaIznajmljivanja>) view.getTblStavke().getModel()).getEntiteti();

                List<Integer> ids = stavke.stream()
                        .map(stavka -> stavka.getVozilo())
                        .map(vozilo -> vozilo.getId())
                        .collect(Collectors.toList());

                NetworkResponse resp2 = RequestManager.sendRequest(new NetworkRequest("vozilo/vrati_sve", new KriterijumWrapper(
                        List.of(new KriterijumDescriptor(Vozilo.class, "id", "IN", ids)),
                        KriterijumWrapper.DepthLevel.FULL)));
                if (!resp2.success || ((List<Vozilo>) resp2.payload).isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Sistem ne može da " + (promena ? "promeni" : "kreira") + " iznajmljivanje.", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                List<Vozilo> freshVozilo = (List<Vozilo>) resp2.payload;
                System.out.println("Vracena potpuna vozila:");

                Map<Integer, Vozilo> freshVoziloMap = new HashMap<>();
                for (var v : freshVozilo) {
                    freshVoziloMap.put(v.getId(), v);
                    System.out.println(v.getProizvodjac() + " " + v.getImeModela());
                }

                for (var si : stavke) {
                    Vozilo novoVozilo = freshVoziloMap.get(si.getVozilo().getId());
                    if (novoVozilo == null) {
                        //nepoklapanje inicijalno povucenih vozila sa novopovucenim vozilima
                        JOptionPane.showMessageDialog(null, "Sistem ne može da " + (promena ? "promeni" : "kreira") + " iznajmljivanje.", "Greška", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    si.setVozilo(novoVozilo);
                    System.out.println(si.getDatumPocetka() + " " + si.getDatumZavrsetka() + " " + si.getVozilo() + " " + si.getVozilo().getKlasa()
                    );
                }

                Zaposleni nz = promena ? ((List<Zaposleni>) respz.payload).getFirst() : z;
                
                Vozac v = ((List<Vozac>) resp.payload).getFirst();
                DugmeUtils.<Iznajmljivanje>zapamtiCall(() -> {
                    Iznajmljivanje i = new Iznajmljivanje(
                            promena ? Integer.parseInt(view.getTxtId().getText()) : null,
                            promena ? view.getDcDatumSklapanja().getDate() : new Date(),
                            Double.parseDouble(view.getLblUkupanIznosVal().getText()),
                            nz,
                            v);
                    for (int j = 0; j < stavke.size(); j++) {
                        stavke.get(j).setRb(j + 1);
                        if (promena) {
                            stavke.get(j).setIznajmljivanje(new Iznajmljivanje(Integer.parseInt(view.getTxtId().getText())));
                        }
                    }
                    i.setStavke(stavke);
                    return i;

                },
                        "iznajmljivanje",
                        "iznajmljivanje",
                        promena,
                        view
                );

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Sistem ne može da " + (promena ? "promeni" : "kreira") + " iznajmljivanje", "Greška", JOptionPane.ERROR_MESSAGE);
            }

        });

        DugmeUtils.<Iznajmljivanje>registrujObrisi(view.getBtnObrisi(), iznajmljivanje, "iznajmljivanje/obrisi", "vozilo", view);

        SwingUtilities.invokeLater(() -> {
            view.setVisible(true);
        });
    }

    private void obrisiStavke() {
        Map<Integer, StavkaIznajmljivanja> saved = new TreeMap<>();
        int[] rs = view.getTblStavke().getSelectedRows();
        Arrays.sort(rs);

        EntityTableModel<StavkaIznajmljivanja> m = (EntityTableModel<StavkaIznajmljivanja>) view.getTblStavke().getModel();
        List<StavkaIznajmljivanja> l = m.getEntiteti();

        double iznosObrisanih = 0.0;

        for (int i = rs.length - 1; i >= 0; i--) {
            iznosObrisanih += m.getEntitetAt(rs[i]).getIznos();
            saved.put(rs[i], m.getEntitetAt(rs[i]));
            m.removeRow(rs[i]);
        }

        view.getLblUkupanIznosVal().setText(Double.toString(clampEpsilon(Double.parseDouble(view.getLblUkupanIznosVal().getText()) - iznosObrisanih, 1e-12)));
        proveri();
        undoBuffer.push(saved);
        redoBuffer.clear();
    }

    private void handleUndo() {
        if (undoBuffer.isEmpty()) {
            SimpleLogger.log(LogLevel.LOG_WARN, "undo buffer prazan");
            return;
        }
        var l = undoBuffer.pop();
        var toRedo = new TreeMap<Integer, StavkaIznajmljivanja>(Comparator.reverseOrder());
        toRedo.putAll(l);
        redoBuffer.push(toRedo);

        ((EntityTableModel<StavkaIznajmljivanja>) view.getTblStavke().getModel()).undoEntiteti(l);

        view.getLblUkupanIznosVal().setText(Double.toString(clampEpsilon(Double.parseDouble(
                view.getLblUkupanIznosVal().getText()) + l.values().stream().mapToDouble(StavkaIznajmljivanja::getIznos).sum(),
                1e-12)));
    }

    private void handleRedo() {
        if (redoBuffer.isEmpty()) {
            SimpleLogger.log(LogLevel.LOG_WARN, "redo buffer prazan");
            return;
        }
        var l = redoBuffer.pop();
        var toUndo = new TreeMap<Integer, StavkaIznajmljivanja>();
        toUndo.putAll(l);
        undoBuffer.push(toUndo);

        ((EntityTableModel<StavkaIznajmljivanja>) view.getTblStavke().getModel()).redoEntiteti(l);

        view.getLblUkupanIznosVal().setText(Double.toString(clampEpsilon(Double.parseDouble(
                view.getLblUkupanIznosVal().getText()) - l.values().stream().mapToDouble(StavkaIznajmljivanja::getIznos).sum(),
                1e-12)));
    }

    private void proveri() {
        if ((promena && (view.getTxtEmailZaposleni().getText().isEmpty() || view.getDcDatumSklapanja().getDate() == null)) || view.getTxtEmail().getText().isEmpty() || view.getTblStavke().getModel().getRowCount() == 0) {
            view.getBtnZapamti().setEnabled(false);
        } else {
            view.getBtnZapamti().setEnabled(mailValid);
        }
    }

    private void proveriMail() {
        Matcher m = p.matcher(view.getTxtEmail().getText());
        Matcher m2 = p.matcher(view.getTxtEmailZaposleni().getText());
        mailValid = m.matches() && m2.matches();

        if (!mailValid && ((!m.matches() && !view.getTxtEmail().getText().isEmpty()) || (!m2.matches() && !view.getTxtEmailZaposleni().getText().isEmpty()))) {
            view.setMailError(true);
        } else {
            view.setMailError(false);
        }
    }

    private void proveriStavka() {
        if (view.getTxtIznos().getText().isEmpty()) {
            view.setDodajStavkuEnabled(false);
        } else {
            view.setDodajStavkuEnabled(datumValid);
        }
    }

    private void proveriDatum() {
        Date poc = stripTime(view.getDcDatumPocetka().getDate());
        Date zav = stripTime(view.getDcDatumZavrsetka().getDate());

        if (poc != null && zav != null) {
            if (poc.equals(zav) || poc.after(zav)) {
                datumValid = false;
                view.setStavkaError("Datum početka mora biti pre datuma završetka.");
            } else if (daysBetween(poc, zav) < StavkaIznajmljivanja.MIN_DANA || daysBetween(poc, zav) > StavkaIznajmljivanja.MAX_DANA) {
                datumValid = false;
                view.setStavkaError("    Trajanje mora biti izmedju " + StavkaIznajmljivanja.MIN_DANA + " i " + StavkaIznajmljivanja.MAX_DANA + " dana.");
            } else {
                datumValid = true;
                view.setStavkaError("");
            }
        } else {
            datumValid = false;
            view.setStavkaError("");
        }
        proveriStavka();
    }

    public static double clampEpsilon(double value, double epsilon) {
        if (Math.abs(value) < epsilon) {
            return 0.0;
        }
        return value;
    }

}
