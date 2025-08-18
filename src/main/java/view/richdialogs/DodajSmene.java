/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package view.richdialogs;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import framework.model.KriterijumWrapper;
import framework.model.enumeracije.InsertBehaviour;
import framework.model.network.NetworkRequest;
import framework.model.network.NetworkResponse;
import framework.orm.anotacije.vrednosnaogranicenja.Between;
import framework.simplelogger.LogLevel;
import framework.simplelogger.SimpleLogger;
import iznajmljivanjeapp.domain.Smena;
import iznajmljivanjeapp.domain.TerminDezurstva;
import iznajmljivanjeapp.domain.Zaposleni;
import iznajmljivanjeapp.domain.insertwrappers.SmenaInsertWrapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import model.tablemodel.EntityTableModel;
import model.tablemodel.EntityTableModelFactory;
import network.RequestManager;
import util.DateUtil;
import view.DodajZaposleni;

/**
 *
 * @author Djurkovic
 */
public class DodajSmene extends javax.swing.JDialog {

    /**
     * Creates new form DodajSmene
     */
    private java.awt.Frame parent;

    boolean dateValid = false;

    boolean redosledValid = false;
    boolean buducnostValid = false;

    public void updateSlider() {
        sliderFiksniBonus.setMinimum((int) spBrojSati.getValue() * 150);
        sliderFiksniBonus.setMaximum((int) spBrojSati.getValue() * 250);
        sliderFiksniBonus.setValue((int) spBrojSati.getValue() * 150);
    }
    private List<Zaposleni> sviZaposleni;
    private List<Zaposleni> izabraniZaposleni;
    private EntityTableModel<Zaposleni> m;

    public DodajSmene(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        buttonGroup1.add(rbError);
        buttonGroup1.add(rbOverwrite);
        buttonGroup1.add(rbIgnore);
        
        rbIgnore.setSelected(true);
        
        
        ((JTextField) dcPrvaSmena.getDateEditor().getUiComponent()).setEditable(false);
        ((JTextField) dcPoslednjaSmena.getDateEditor().getUiComponent()).setEditable(false);

        dcPrvaSmena.getDateEditor().addPropertyChangeListener("date", e -> {
            dateCheck();
        });

        dcPoslednjaSmena.getDateEditor().addPropertyChangeListener("date", e -> {
            dateCheck();
        });

        lblError.setVisible(false);
        lblErrorBuducnost.setVisible(false);

        this.parent = parent;
        Between b = Smena.class.getAnnotation(Between.class);
        int min, max;
        if (b == null) {
            min = 4;
            max = 8;
        } else {
            min = (int) b.donjaGranica();
            max = (int) b.gornjaGranica();
            if (!b.equal()) {
                min++;
                max--;
            }
        }
        spBrojSati.setModel(new SpinnerNumberModel(4, 4, 8, 1));
        sliderFiksniBonus.addChangeListener(e -> {
            lblFiksniBonusVal.setText(sliderFiksniBonus.getValue() + "");

        });
        spBrojSati.addChangeListener(e -> {
            updateSlider();
        });
        updateSlider();

        btnIzbaci.setEnabled(false);
        sliderFiksniBonus.setVisible(false);
        lblFiksniBonus.setVisible(false);
        lblFiksniBonusVal.setVisible(false);

        izabraniZaposleni = new ArrayList<>();

        tblZaposleni.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean hasSelection = tblZaposleni.getSelectedRowCount() > 0;
                btnIzbaci.setEnabled(hasSelection);
            }
        });

        try {
            tblZaposleni.setModel(EntityTableModelFactory.<Zaposleni>create(Zaposleni.class, izabraniZaposleni));
            this.m = (EntityTableModel<Zaposleni>) tblZaposleni.getModel();
            NetworkResponse resp = RequestManager.sendRequest(new NetworkRequest("zaposleni/vrati_sve", new KriterijumWrapper(List.of(), KriterijumWrapper.DepthLevel.NONE)));
            if (!resp.success) {
                SwingUtilities.invokeLater(() -> dispose());
                JOptionPane.showMessageDialog(null, "Došlo je do greške pri kreiranju forme za dodelu smena.", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }
            sviZaposleni = (List<Zaposleni>) resp.payload;

            NetworkResponse resp2 = RequestManager.sendRequest(new NetworkRequest("termindezurstva/vrati_sve", new KriterijumWrapper(List.of(), KriterijumWrapper.DepthLevel.NONE)));
            if (!resp2.success) {
                SwingUtilities.invokeLater(() -> dispose());
                JOptionPane.showMessageDialog(null, "Došlo je do greške pri kreiranju forme za dodelu smena.", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }
            List<TerminDezurstva> tds = (List<TerminDezurstva>) resp2.payload;
            for (var td : tds) {
                cbTerDez.addItem(td);
            }

        } catch (Exception ex) {
            SwingUtilities.invokeLater(() -> dispose());
            JOptionPane.showMessageDialog(null, "Došlo je do greške pri kreiranju forme za dodelu smena.", "Greška", JOptionPane.ERROR_MESSAGE);

        }

        btnKreiraj.setEnabled(false);

        tblZaposleni.getModel().addTableModelListener(e -> {
            btnKreiraj.setEnabled(!m.getEntiteti().isEmpty() && dateValid);

        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        dcPoslednjaSmena = new com.toedter.calendar.JDateChooser();
        dcPrvaSmena = new com.toedter.calendar.JDateChooser();
        ckVikend = new javax.swing.JCheckBox();
        ckVanredan = new javax.swing.JCheckBox();
        sliderFiksniBonus = new javax.swing.JSlider();
        jLabel4 = new javax.swing.JLabel();
        spBrojSati = new javax.swing.JSpinner();
        lblFiksniBonus = new javax.swing.JLabel();
        lblFiksniBonusVal = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblZaposleni = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        btnKreiraj = new javax.swing.JButton();
        btnIzbaciSve = new javax.swing.JButton();
        btnIzbaci = new javax.swing.JButton();
        btnDodaj = new javax.swing.JButton();
        btnDodajSve = new javax.swing.JButton();
        lblError = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbTerDez = new javax.swing.JComboBox<>();
        lblErrorBuducnost = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        rbIgnore = new javax.swing.JRadioButton();
        rbOverwrite = new javax.swing.JRadioButton();
        rbError = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Kreacija Smena");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel1.setText("Kreacija smena");

        jLabel2.setText("Datum prve smene:");

        jLabel3.setText("Datum poslednje smene:");

        ckVikend.setText("Uključi vikende?");

        ckVanredan.setText("Vanredne smene?");
        ckVanredan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckVanredanActionPerformed(evt);
            }
        });

        sliderFiksniBonus.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderFiksniBonusStateChanged(evt);
            }
        });

        jLabel4.setText("Broj sati:");

        lblFiksniBonus.setText("Fiksni bonus: ");

        lblFiksniBonusVal.setText("0");

        tblZaposleni.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblZaposleni);

        jLabel5.setText("Zaposleni za dodeljivanje smena");

        btnKreiraj.setText("Kreiraj Smene");
        btnKreiraj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKreirajActionPerformed(evt);
            }
        });

        btnIzbaciSve.setText("Izbaci sve zaposlene");
        btnIzbaciSve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIzbaciSveActionPerformed(evt);
            }
        });

        btnIzbaci.setText("Izbaci izabrane zaposlene");
        btnIzbaci.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIzbaciActionPerformed(evt);
            }
        });

        btnDodaj.setText("Dodaj zaposlene");
        btnDodaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDodajActionPerformed(evt);
            }
        });

        btnDodajSve.setText("Dodaj sve zaposlene");
        btnDodajSve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDodajSveActionPerformed(evt);
            }
        });

        lblError.setForeground(new java.awt.Color(255, 51, 0));
        lblError.setText("Datum prve smene mora biti pre poslednje.");

        jLabel6.setText("Termin dežurstva:");

        lblErrorBuducnost.setForeground(new java.awt.Color(255, 51, 0));
        lblErrorBuducnost.setText("Datumi se moraju odnositi na budućnost.");

        jLabel7.setText("Ponašanje pri kreaciji:");

        rbIgnore.setText("Ignoriši konflikte");
        rbIgnore.setToolTipText("Ukoliko dođe do pokušaja ubacivanja smene koja na taj dan i za tog zaposlenog već postoji, ignoriši pokušaj i nastavi sa ubacivanjem.");

        rbOverwrite.setText("Update konflikte");
        rbOverwrite.setToolTipText("Ukoliko dođe do pokušaja ubacivanja smene koja na taj dan i za tog zaposlenog već postoji, ažuriraj smenu sa novom vrednošću i nastavi sa ubacivanjem.");

        rbError.setText("Error pri konfliktu");
        rbError.setToolTipText("Ukoliko dođe do pokušaja ubacivanja smene koja na taj dan i za tog zaposlenog već postoji, prikaži grešku i obustavi ceo proces ubacivanja.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(63, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sliderFiksniBonus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbTerDez, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dcPrvaSmena, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dcPoslednjaSmena, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(ckVikend)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(spBrojSati, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(ckVanredan)
                            .addComponent(jLabel6)
                            .addComponent(lblError)
                            .addComponent(lblErrorBuducnost)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnIzbaci, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnIzbaciSve, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDodaj, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDodajSve, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblFiksniBonus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblFiksniBonusVal)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addGap(176, 176, 176))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel7))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(rbIgnore)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbOverwrite)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbError)))
                .addContainerGap(485, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(321, 321, 321)
                        .addComponent(btnKreiraj, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(326, 326, 326)
                        .addComponent(jLabel1)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel1)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblErrorBuducnost)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblError, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(dcPrvaSmena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dcPoslednjaSmena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cbTerDez, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(spBrojSati, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(ckVikend)
                        .addGap(18, 18, 18)
                        .addComponent(ckVanredan)
                        .addGap(18, 18, 18)
                        .addComponent(sliderFiksniBonus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblFiksniBonus)
                            .addComponent(lblFiksniBonusVal))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnIzbaci)
                                .addGap(18, 18, 18)
                                .addComponent(btnIzbaciSve)
                                .addGap(82, 82, 82))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnDodaj)
                                .addGap(18, 18, 18)
                                .addComponent(btnDodajSve)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbIgnore)
                    .addComponent(rbOverwrite)
                    .addComponent(rbError))
                .addGap(18, 18, 18)
                .addComponent(btnKreiraj, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sliderFiksniBonusStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderFiksniBonusStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_sliderFiksniBonusStateChanged

    private void ckVanredanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckVanredanActionPerformed
        sliderFiksniBonus.setVisible(ckVanredan.isSelected());
        lblFiksniBonusVal.setVisible(ckVanredan.isSelected());
        lblFiksniBonus.setVisible(ckVanredan.isSelected());
    }//GEN-LAST:event_ckVanredanActionPerformed

    private void btnDodajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDodajActionPerformed

        new DodajZaposleni(parent, true, sviZaposleni, (EntityTableModel<Zaposleni>) tblZaposleni.getModel()).setVisible(true);
    }//GEN-LAST:event_btnDodajActionPerformed

    private void btnDodajSveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDodajSveActionPerformed

        m.addEntiteti(sviZaposleni);
        sviZaposleni.clear();
    }//GEN-LAST:event_btnDodajSveActionPerformed

    private void btnIzbaciSveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIzbaciSveActionPerformed

        var zs = m.getEntiteti();
        sviZaposleni.addAll(zs);
        zs.clear();
        m.fireTableDataChanged();
    }//GEN-LAST:event_btnIzbaciSveActionPerformed

    private void btnIzbaciActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIzbaciActionPerformed
        int[] rs = tblZaposleni.getSelectedRows();
        Arrays.sort(rs);
        var zs = m.getEntiteti();
        for (int i = rs.length - 1; i >= 0; i--) {
            int r = rs[i];
            sviZaposleni.add(zs.get(r));
            zs.remove(r);
            m.fireTableRowsDeleted(r, r);
        }
    }//GEN-LAST:event_btnIzbaciActionPerformed

    private void btnKreirajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKreirajActionPerformed
        List<Smena> smene = new ArrayList<>();
        boolean vanredan = ckVanredan.isSelected();
        boolean ukljuciVIkende = ckVikend.isSelected();
        var zs = m.getEntiteti();
        InsertBehaviour ib;
        if(rbError.isSelected()) {
            ib = InsertBehaviour.NORMAL;
        }
        else if(rbIgnore.isSelected()) {
            ib = InsertBehaviour.IGNORE_DUPS;
        }
        else if(rbOverwrite.isSelected()) {
            ib = InsertBehaviour.OVERWRITE_DUPS;
        }
        else {
            //nikad nije moguce ali compiler to ne moze da zakljuci...
            ib = InsertBehaviour.NORMAL;
        }
        
        for (var z : zs) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(DateUtil.stripTime(dcPrvaSmena.getDate()));
            Date end = DateUtil.stripTime(dcPoslednjaSmena.getDate());

            while (!cal.getTime().after(end)) {
                Date current = cal.getTime();
                if(!ukljuciVIkende && DateUtil.isWeekend(current)) {
                    cal.add(Calendar.DATE, 1);
                    continue;
                }
                smene.add(new Smena(
                        current, z, (TerminDezurstva) cbTerDez.getSelectedItem(), vanredan, (int) spBrojSati.getValue(), !vanredan ? 0 : sliderFiksniBonus.getValue())
                );
                cal.add(Calendar.DATE, 1);
            }
        }

        try {
            SmenaInsertWrapper siw = new SmenaInsertWrapper();
            siw.smene = smene;
            siw.ib = ib;
            NetworkResponse resp = RequestManager.sendRequest(new NetworkRequest("smena/kreiraj", siw));
            if (!resp.success) {
                JOptionPane.showMessageDialog(null, "Došlo je do greške pri kreiranju smena.", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(null, "Uspešno kreiranje smena.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Došlo je do greške pri kreiranju smena.", "Greška", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnKreirajActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            UIManager.installLookAndFeel("FlatLaf Light", "com.formdev.flatlaf.FlatLightLaf");
            UIManager.installLookAndFeel("FlatLaf Dark", "com.formdev.flatlaf.FlatDarkLaf");
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DodajSmene dialog = new DodajSmene(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDodaj;
    private javax.swing.JButton btnDodajSve;
    private javax.swing.JButton btnIzbaci;
    private javax.swing.JButton btnIzbaciSve;
    private javax.swing.JButton btnKreiraj;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<TerminDezurstva> cbTerDez;
    private javax.swing.JCheckBox ckVanredan;
    private javax.swing.JCheckBox ckVikend;
    private com.toedter.calendar.JDateChooser dcPoslednjaSmena;
    private com.toedter.calendar.JDateChooser dcPrvaSmena;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblError;
    private javax.swing.JLabel lblErrorBuducnost;
    private javax.swing.JLabel lblFiksniBonus;
    private javax.swing.JLabel lblFiksniBonusVal;
    private javax.swing.JRadioButton rbError;
    private javax.swing.JRadioButton rbIgnore;
    private javax.swing.JRadioButton rbOverwrite;
    private javax.swing.JSlider sliderFiksniBonus;
    private javax.swing.JSpinner spBrojSati;
    private javax.swing.JTable tblZaposleni;
    // End of variables declaration//GEN-END:variables

    private void dateCheck() {

        Date now = DateUtil.stripTime(new Date());

        Date d1 = DateUtil.stripTime(dcPrvaSmena.getDate());
        Date d2 = DateUtil.stripTime(dcPoslednjaSmena.getDate());

        if (d1 == null && d2 == null) {
            dateValid = false;
            buducnostValid = false;
            redosledValid = false;
            lblError.setVisible(false);
            lblErrorBuducnost.setVisible(false);
            return;
        }
        if (d1 == null ^ d2 == null) {
            dateValid = false;
            redosledValid = false;
            buducnostValid = false;
            lblError.setVisible(false);

            lblErrorBuducnost.setVisible((d1 != null && d1.before(now)) || (d2 != null && d2.before(now)));

            return;
        }

        redosledValid = dcPrvaSmena.getDate().before(dcPoslednjaSmena.getDate()) || dcPrvaSmena.getDate().equals(dcPoslednjaSmena.getDate());
        lblError.setVisible(!redosledValid);

        buducnostValid = (dcPrvaSmena.getDate().equals(now) || dcPrvaSmena.getDate().after(now)) && (dcPoslednjaSmena.getDate().equals(now) || dcPoslednjaSmena.getDate().after(now));
        lblErrorBuducnost.setVisible(!buducnostValid);

        dateValid = redosledValid && buducnostValid;

        btnKreiraj.setEnabled(!m.getEntiteti().isEmpty() && dateValid);
    }
}
