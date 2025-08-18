/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package view;

import com.toedter.calendar.JDateChooser;
import iznajmljivanjeapp.domain.StavkaIznajmljivanja;
import iznajmljivanjeapp.domain.Vozilo;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import model.tablemodel.EntityTableModelFactory;
import util.NumberFilter;

/**
 *
 * @author Djurkovic
 */
public class IznajmljivanjeView extends javax.swing.JDialog {

    public void setDatumSklapanjaVisible(boolean b) {
        lblDatumSklapanja.setVisible(b);
        dcDatumSklapanja.setVisible(b);

    }

    public JDateChooser getDcDatumSklapanja() {
        return dcDatumSklapanja;
    }

    public void setDcDatumSklapanja(JDateChooser dcDatumSklapanja) {
        this.dcDatumSklapanja = dcDatumSklapanja;
    }

    public void setZaposleniInputVisible(boolean b) {
        lblEmailZaposleni.setVisible(b);
        txtEmailZaposleni.setVisible(b);
    }

    public void setVozila(List<Vozilo> l) {
        cbVozilo.removeAllItems();
        for (var v : l) {
            cbVozilo.addItem(v);
        }
    }

    public JButton getBtnPromeniStavku() {
        return btnPromeniStavku;
    }

    public void setBtnPromeniStavku(JButton btnPromeniStavku) {
        this.btnPromeniStavku = btnPromeniStavku;
    }

    public void addStavkaListener(ActionListener l) {
        btnDodajStavku.addActionListener(l);
    }

    public void setMailError(boolean b) {
        lblMailError.setVisible(b);
    }

    public void setKreirajEnabled(boolean b) {
        btnZapamti.setEnabled(b);
    }

    public void setIdVisible(boolean b) {
        lblId.setVisible(b);
        txtId.setVisible(b);
    }

    public void setObrisiVisible(boolean b) {
        btnObrisi.setVisible(b);
    }

    public JButton getBtnObrisi() {
        return btnObrisi;
    }

    public void setBtnObrisi(JButton btnObrisi) {
        this.btnObrisi = btnObrisi;
    }

    public JButton getBtnDodajStavku() {
        return btnDodajStavku;
    }

    public void setBtnDodajStavku(JButton btnDodajStavku) {
        this.btnDodajStavku = btnDodajStavku;
    }

    public JButton getBtnObrisiStavke() {
        return btnObrisiStavke;
    }

    public void setBtnObrisiStavke(JButton btnObrisiStavke) {
        this.btnObrisiStavke = btnObrisiStavke;
    }

    public JButton getBtnZapamti() {
        return btnZapamti;
    }

    public void setBtnZapamti(JButton btnZapamti) {
        this.btnZapamti = btnZapamti;
    }

    public JComboBox<Vozilo> getCbVozilo() {
        return cbVozilo;
    }

    public void setCbVozilo(JComboBox<Vozilo> cbVozilo) {
        this.cbVozilo = cbVozilo;
    }

    public void setObrisiStavkeEnabled(boolean b) {
        btnObrisiStavke.setEnabled(b);
    }

    public void addObrisiStavkeListener(ActionListener l) {
        btnObrisiStavke.addActionListener(l);
    }

    public JDateChooser getDcDatumPocetka() {
        return dcDatumPocetka;
    }

    public void setDcDatumPocetka(JDateChooser dcDatumPocetka) {
        this.dcDatumPocetka = dcDatumPocetka;
    }

    public JDateChooser getDcDatumZavrsetka() {
        return dcDatumZavrsetka;
    }

    public void setDcDatumZavrsetka(JDateChooser dcDatumZavrsetka) {
        this.dcDatumZavrsetka = dcDatumZavrsetka;
    }

    public JLabel getLblDatumPocetka() {
        return lblDatumPocetka;
    }

    public void setLblDatumPocetka(JLabel lblDatumPocetka) {
        this.lblDatumPocetka = lblDatumPocetka;
    }

    public JLabel getLblDatumZavrsetka() {
        return lblDatumZavrsetka;
    }

    public void setLblDatumZavrsetka(JLabel lblDatumZavrsetka) {
        this.lblDatumZavrsetka = lblDatumZavrsetka;
    }

    public JLabel getLblEmail() {
        return lblEmail;
    }

    public void setLblEmail(JLabel lblEmail) {
        this.lblEmail = lblEmail;
    }

    public JLabel getLblGlavni() {
        return lblGlavni;
    }

    public void setDodajStavkuEnabled(boolean b) {
        btnDodajStavku.setEnabled(b);
    }

    public void setLblGlavni(JLabel lblGlavni) {
        this.lblGlavni = lblGlavni;
    }

    public void addDatumSklapanjaListener(Runnable r) {
        dcDatumSklapanja.getDateEditor().addPropertyChangeListener("date", e -> {
            r.run();
        });
    }

    public void addDatumPocListener(Runnable r) {
        dcDatumPocetka.getDateEditor().addPropertyChangeListener("date", e -> {
            r.run();
        });
    }

    public void addDatumZavListener(Runnable r) {
        dcDatumZavrsetka.getDateEditor().addPropertyChangeListener("date", e -> {
            r.run();
        });
    }

    public void setStavkaError(String txt) {
        lblStavkaError.setText(txt);
    }

    public JLabel getLblId() {
        return lblId;
    }

    public void setLblId(JLabel lblId) {
        this.lblId = lblId;
    }

    public JLabel getLblIznos() {
        return lblIznos;
    }

    public void setLblIznos(JLabel lblIznos) {
        this.lblIznos = lblIznos;
    }

    public JLabel getLblUkupanIznos() {
        return lblUkupanIznos;
    }

    public void setLblUkupanIznos(JLabel lblUkupanIznos) {
        this.lblUkupanIznos = lblUkupanIznos;
    }

    public JLabel getLblVozilo() {
        return lblVozilo;
    }

    public void setLblVozilo(JLabel lblVozilo) {
        this.lblVozilo = lblVozilo;
    }

    public JTable getTblStavke() {
        return tblStavke;
    }

    public void setTblStavke(JTable tblStavke) {
        this.tblStavke = tblStavke;
    }

    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public void setTxtEmail(JTextField txtEmail) {
        this.txtEmail = txtEmail;
    }

    public JTextField getTxtId() {
        return txtId;
    }

    public void setTxtId(JTextField txtId) {
        this.txtId = txtId;
    }

    public JTextField getTxtIznos() {
        return txtIznos;
    }

    public void setTxtIznos(JTextField txtIznos) {
        this.txtIznos = txtIznos;
    }

    public JLabel getLblUkupanIznosVal() {
        return lblUkupanIznosVal;
    }

    public void setLblUkupanIznosVal(JLabel lblUkupanIznosVal) {
        this.lblUkupanIznosVal = lblUkupanIznosVal;
    }

    /**
     * Creates new form IznajmljivanjeView
     */
    private java.awt.Frame parent;

    public IznajmljivanjeView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.parent = parent;
        setLocationRelativeTo(parent);
        ((AbstractDocument) txtIznos.getDocument()).setDocumentFilter(new NumberFilter(NumberFilter.Mode.POSITIVE_DECIMAL));
        JTextField dateField = (JTextField) dcDatumSklapanja.getDateEditor().getUiComponent();
        dateField.setEditable(false);
        JTextField dateField2 = (JTextField) dcDatumPocetka.getDateEditor().getUiComponent();
        dateField2.setEditable(false);
        JTextField dateField3 = (JTextField) dcDatumZavrsetka.getDateEditor().getUiComponent();
        dateField3.setEditable(false);
    }

    public Frame getParent() {
        return parent;
    }

    public void setParent(Frame parent) {
        this.parent = parent;
    }

    public JTextField getTxtEmailZaposleni() {
        return txtEmailZaposleni;
    }

    public void setTxtEmailZaposleni(JTextField txtEmailZaposleni) {
        this.txtEmailZaposleni = txtEmailZaposleni;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblStavke = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        lblDatumPocetka = new javax.swing.JLabel();
        lblDatumZavrsetka = new javax.swing.JLabel();
        lblVozilo = new javax.swing.JLabel();
        lblIznos = new javax.swing.JLabel();
        dcDatumPocetka = new com.toedter.calendar.JDateChooser();
        cbVozilo = new javax.swing.JComboBox<>();
        txtIznos = new javax.swing.JTextField();
        btnDodajStavku = new javax.swing.JButton();
        lblStavkaError = new javax.swing.JLabel();
        dcDatumZavrsetka = new com.toedter.calendar.JDateChooser();
        btnPromeniStavku = new javax.swing.JButton();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        btnZapamti = new javax.swing.JButton();
        lblId = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        btnObrisiStavke = new javax.swing.JButton();
        lblUkupanIznos = new javax.swing.JLabel();
        btnObrisi = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblMailError = new javax.swing.JLabel();
        lblGlavni = new javax.swing.JLabel();
        lblUkupanIznosVal = new javax.swing.JLabel();
        lblEmailZaposleni = new javax.swing.JLabel();
        txtEmailZaposleni = new javax.swing.JTextField();
        lblDatumSklapanja = new javax.swing.JLabel();
        dcDatumSklapanja = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Kreiraj iznajmljivanje");
        setResizable(false);

        tblStavke.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblStavke);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Stavka Iznajmljivanja", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        lblDatumPocetka.setText("Datum Početka:");

        lblDatumZavrsetka.setText("Datum Završetka:");

        lblVozilo.setText("Vozilo:");

        lblIznos.setText("Iznos:");

        btnDodajStavku.setText("Dodaj Stavku");

        lblStavkaError.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        lblStavkaError.setForeground(new java.awt.Color(255, 0, 0));
        lblStavkaError.setText("Datum početka mora biti pre datuma završetka.");

        btnPromeniStavku.setText("Promeni selektovanu stavku");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblDatumZavrsetka)
                                .addGap(9, 9, 9)
                                .addComponent(dcDatumZavrsetka, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblDatumPocetka)
                                .addGap(18, 18, 18)
                                .addComponent(dcDatumPocetka, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblVozilo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbVozilo, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblIznos)
                                .addGap(18, 18, 18)
                                .addComponent(txtIznos, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(btnDodajStavku))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(btnPromeniStavku))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(lblStavkaError)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblDatumPocetka)
                    .addComponent(dcDatumPocetka, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(lblDatumZavrsetka))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(dcDatumZavrsetka, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVozilo)
                    .addComponent(cbVozilo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIznos)
                    .addComponent(txtIznos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblStavkaError)
                .addGap(18, 18, 18)
                .addComponent(btnPromeniStavku)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDodajStavku)
                .addContainerGap())
        );

        lblEmail.setText("Email vozača:");

        btnZapamti.setText("Zapamti Iznajmljivanje");

        lblId.setText("Id:");

        txtId.setEnabled(false);

        btnObrisiStavke.setText("Obriši izabrane stavke");

        lblUkupanIznos.setText("Ukupan iznos:");

        btnObrisi.setText("Obriši iznajmljivanje");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 12, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        lblMailError.setForeground(new java.awt.Color(255, 0, 0));
        lblMailError.setText("Mejl mora biti validnog formata.");

        lblGlavni.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        lblGlavni.setText(" Kreiraj Iznajmljivanje");

        lblUkupanIznosVal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUkupanIznosVal.setText("0.0");

        lblEmailZaposleni.setText("Email zaposlenog:");

        lblDatumSklapanja.setText("Datum sklapanja:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(lblUkupanIznos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUkupanIznosVal))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(btnObrisiStavke))
                            .addComponent(btnZapamti, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(btnObrisi))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblMailError)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblId)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblEmailZaposleni)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtEmailZaposleni, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblEmail)
                                .addGap(30, 30, 30)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblGlavni)
                        .addGap(66, 66, 66)
                        .addComponent(lblDatumSklapanja)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dcDatumSklapanja, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lblGlavni)
                            .addGap(27, 27, 27)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblId))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblEmailZaposleni)
                                .addComponent(txtEmailZaposleni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblMailError)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(lblDatumSklapanja))
                    .addComponent(dcDatumSklapanja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEmail)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblUkupanIznos)
                            .addComponent(lblUkupanIznosVal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnObrisiStavke, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addComponent(btnZapamti, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnObrisi, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IznajmljivanjeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IznajmljivanjeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IznajmljivanjeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IznajmljivanjeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                IznajmljivanjeView dialog = new IznajmljivanjeView(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnDodajStavku;
    private javax.swing.JButton btnObrisi;
    private javax.swing.JButton btnObrisiStavke;
    private javax.swing.JButton btnPromeniStavku;
    private javax.swing.JButton btnZapamti;
    private javax.swing.JComboBox<Vozilo> cbVozilo;
    private com.toedter.calendar.JDateChooser dcDatumPocetka;
    private com.toedter.calendar.JDateChooser dcDatumSklapanja;
    private com.toedter.calendar.JDateChooser dcDatumZavrsetka;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDatumPocetka;
    private javax.swing.JLabel lblDatumSklapanja;
    private javax.swing.JLabel lblDatumZavrsetka;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEmailZaposleni;
    private javax.swing.JLabel lblGlavni;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblIznos;
    private javax.swing.JLabel lblMailError;
    private javax.swing.JLabel lblStavkaError;
    private javax.swing.JLabel lblUkupanIznos;
    private javax.swing.JLabel lblUkupanIznosVal;
    private javax.swing.JLabel lblVozilo;
    private javax.swing.JTable tblStavke;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEmailZaposleni;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtIznos;
    // End of variables declaration//GEN-END:variables
}
