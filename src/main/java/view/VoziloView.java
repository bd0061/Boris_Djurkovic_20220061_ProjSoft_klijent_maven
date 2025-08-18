/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package view;

import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import util.NumberFilter;

/**
 *
 * @author Djurkovic
 */
public class VoziloView extends javax.swing.JDialog {

    public JComboBox<Integer> getCbGodiste() {
        return cbGodiste;
    }

    public void setCbGodiste(JComboBox<Integer> cbGodiste) {
        this.cbGodiste = cbGodiste;
    }

    private void podesiProzor() {
        setIconImage(new ImageIcon(getClass().getResource("/resources/icon.png")).getImage());
        setLocationRelativeTo(null);
    }

    private void dodajSliku(String ime) {
        //crtanje slike iznad svih ostalih komponenti sa vrlo niskom transparentnoscu
        Slika slika = new Slika(new ImageIcon(getClass().getResource(ime)).getImage(), 0.06f);
        getLayeredPane().add(slika, JLayeredPane.PALETTE_LAYER);
        slika.setBounds(0, 0, getWidth(), getHeight());
        slika.setOpaque(false);
        slika.setFocusable(false);
    }

    public JButton getBtnObrisi() {
        return btnObrisi;
    }

    public void setBtnObrisi(JButton btnObrisi) {
        this.btnObrisi = btnObrisi;
    }

    public JButton getBtnZapamti() {
        return btnZapamti;
    }

    public void setBtnZapamti(JButton btnZapamti) {
        this.btnZapamti = btnZapamti;
    }

    public JComboBox<String> getCbKategorija() {
        return cbKategorija;
    }

    public void setCbKategorija(JComboBox<String> cbKategorija) {
        this.cbKategorija = cbKategorija;
    }

    public JComboBox<String> getCbKlasa() {
        return cbKlasa;
    }

    public void setCbKlasa(JComboBox<String> cbKlasa) {
        this.cbKlasa = cbKlasa;
    }

    public JComboBox<String> getCbProizvodjac() {
        return cbProizvodjac;
    }

    public void setCbProizvodjac(JComboBox<String> cbProizvodjac) {
        this.cbProizvodjac = cbProizvodjac;
    }

    public JComboBox<Integer> getjComboBox1() {
        return cbGodiste;
    }

    public void setjComboBox1(JComboBox<Integer> jComboBox1) {
        this.cbGodiste = jComboBox1;
    }

    public JLabel getLblGlavni() {
        return lblGlavni;
    }

    public void setLblGlavni(JLabel lblGlavni) {
        this.lblGlavni = lblGlavni;
    }

    public JLabel getLblGodiste() {
        return lblGodiste;
    }

    public void setLblGodiste(JLabel lblGodiste) {
        this.lblGodiste = lblGodiste;
    }

    public JLabel getLblId() {
        return lblId;
    }

    public void setLblId(JLabel lblId) {
        this.lblId = lblId;
    }

    public JLabel getLblImeModela() {
        return lblImeModela;
    }

    public void setLblImeModela(JLabel lblImeModela) {
        this.lblImeModela = lblImeModela;
    }

    public JLabel getLblKategorija() {
        return lblKategorija;
    }

    public void setLblKategorija(JLabel lblKategorija) {
        this.lblKategorija = lblKategorija;
    }

    public JLabel getLblKlasa() {
        return lblKlasa;
    }

    public void setLblKlasa(JLabel lblKlasa) {
        this.lblKlasa = lblKlasa;
    }

    public JLabel getLblKupovnaCena() {
        return lblKupovnaCena;
    }

    public void setLblKupovnaCena(JLabel lblKupovnaCena) {
        this.lblKupovnaCena = lblKupovnaCena;
    }

    public JLabel getLblProizvodjac() {
        return lblProizvodjac;
    }

    public void setLblProizvodjac(JLabel lblProizvodjac) {
        this.lblProizvodjac = lblProizvodjac;
    }

    public JTextField getTxtId() {
        return txtId;
    }

    public void setTxtId(JTextField txtId) {
        this.txtId = txtId;
    }

    public JTextField getTxtImeModela() {
        return txtImeModela;
    }

    public void setTxtImeModela(JTextField txtImeModela) {
        this.txtImeModela = txtImeModela;
    }

    public JTextField getTxtKupovnaCena() {
        return txtKupovnaCena;
    }

    /**
     * Creates new form VoziloView
     */
    public void setTxtKupovnaCena(JTextField txtKupovnaCena) {
        this.txtKupovnaCena = txtKupovnaCena;
    }

    public VoziloView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        podesiNumerickaPolja();
        podesiProzor();
        dodajSliku("/resources/logo.png");
    }

    public void setGodiste(List<Integer> l) {
        cbGodiste.removeAllItems();
        for (var i : l) {
            cbGodiste.addItem(i);
        }

    }

    public void addKupovnaCenaListener(DocumentListener l) {
        txtKupovnaCena.getDocument().addDocumentListener(l);
    }

    public void addImeModelaListener(DocumentListener l) {
        txtImeModela.getDocument().addDocumentListener(l);
    }

    public void setKreirajEnabled(boolean b) {
        btnZapamti.setEnabled(b);
    }

    public void setObrisiVisible(boolean b) {
        btnObrisi.setVisible(b);
    }

    public void setIdVisible(boolean b) {
        lblId.setVisible(b);
        txtId.setVisible(b);
    }

    public void setGlavni(String txt) {
        lblGlavni.setText(txt);
    }

    public void addKlasaListener(ActionListener l) {
        cbKlasa.addActionListener(l);
    }

    public void addProizvodjacListener(ActionListener l) {
        cbProizvodjac.addActionListener(l);
    }
    
    public void addKreirajListener(ActionListener l) {
        btnZapamti.addActionListener(l);
    }

    private void podesiNumerickaPolja() {
        ((AbstractDocument) txtKupovnaCena.getDocument()).setDocumentFilter(new NumberFilter(NumberFilter.Mode.POSITIVE_DECIMAL));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblGlavni = new javax.swing.JLabel();
        lblKlasa = new javax.swing.JLabel();
        lblProizvodjac = new javax.swing.JLabel();
        lblKupovnaCena = new javax.swing.JLabel();
        lblGodiste = new javax.swing.JLabel();
        lblImeModela = new javax.swing.JLabel();
        lblKategorija = new javax.swing.JLabel();
        txtImeModela = new javax.swing.JTextField();
        txtKupovnaCena = new javax.swing.JTextField();
        cbKategorija = new javax.swing.JComboBox<>();
        cbProizvodjac = new javax.swing.JComboBox<>();
        cbKlasa = new javax.swing.JComboBox<>();
        btnZapamti = new javax.swing.JButton();
        btnObrisi = new javax.swing.JButton();
        lblId = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        cbGodiste = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblGlavni.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        lblGlavni.setText("Kreiraj novo vozilo");

        lblKlasa.setText("Klasa:");

        lblProizvodjac.setText("Proizvođač:");

        lblKupovnaCena.setText("Kupovna cena:");

        lblGodiste.setText("Godište:");

        lblImeModela.setText("Ime Modela:");

        lblKategorija.setText("Kategorija:");

        txtImeModela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtImeModelaActionPerformed(evt);
            }
        });

        txtKupovnaCena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKupovnaCenaActionPerformed(evt);
            }
        });

        cbKategorija.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Budžet", "Srednja", "Luksuz" }));

        cbProizvodjac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mercedes", "Fiat", "Volkswagen", "Dacia", "Audi", "Skoda" }));

        cbKlasa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Automobil", "Minibus", "Motor" }));

        btnZapamti.setText("Zapamti");

        btnObrisi.setText("Obriši");

        lblId.setText("Id:");

        txtId.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblKlasa)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblProizvodjac)
                                    .addComponent(lblGodiste)
                                    .addComponent(lblImeModela)
                                    .addComponent(lblKategorija)
                                    .addComponent(lblKupovnaCena))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbKlasa, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtKupovnaCena, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                                    .addComponent(txtImeModela)
                                    .addComponent(cbKategorija, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbProizvodjac, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbGodiste, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(lblGlavni))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnObrisi, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnZapamti, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(lblId)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblId)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblGlavni)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblKlasa)
                    .addComponent(cbKlasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProizvodjac)
                    .addComponent(cbProizvodjac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblKupovnaCena)
                    .addComponent(txtKupovnaCena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGodiste)
                    .addComponent(cbGodiste, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblImeModela)
                    .addComponent(txtImeModela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblKategorija)
                    .addComponent(cbKategorija, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addComponent(btnZapamti)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnObrisi)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtImeModelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtImeModelaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtImeModelaActionPerformed

    private void txtKupovnaCenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKupovnaCenaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKupovnaCenaActionPerformed

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
            java.util.logging.Logger.getLogger(VoziloView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VoziloView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VoziloView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VoziloView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                VoziloView dialog = new VoziloView(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnObrisi;
    private javax.swing.JButton btnZapamti;
    private javax.swing.JComboBox<Integer> cbGodiste;
    private javax.swing.JComboBox<String> cbKategorija;
    private javax.swing.JComboBox<String> cbKlasa;
    private javax.swing.JComboBox<String> cbProizvodjac;
    private javax.swing.JLabel lblGlavni;
    private javax.swing.JLabel lblGodiste;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblImeModela;
    private javax.swing.JLabel lblKategorija;
    private javax.swing.JLabel lblKlasa;
    private javax.swing.JLabel lblKupovnaCena;
    private javax.swing.JLabel lblProizvodjac;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtImeModela;
    private javax.swing.JTextField txtKupovnaCena;
    // End of variables declaration//GEN-END:variables
}
