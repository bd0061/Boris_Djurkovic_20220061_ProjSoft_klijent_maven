/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Djurkovic
 */
public class ZaposleniView extends javax.swing.JDialog {

    public void setIdVisible(boolean b) {
        lblId.setVisible(b);
        txtId.setVisible(b);
    }

    public void setObrisiVisible(boolean b) {
        btnObrisi.setVisible(b);
    }

    public void setZapamtiEnabled(boolean b) {
        btnZapamti.setEnabled(b);
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

    public JLabel getjLabel1() {
        return lblGlavni;
    }

    public void setjLabel1(JLabel jLabel1) {
        this.lblGlavni = jLabel1;
    }

    public JTextField getjTextField5() {
        return jTextField5;
    }

    public void setjTextField5(JTextField jTextField5) {
        this.jTextField5 = jTextField5;
    }

    public JLabel getLblEmail() {
        return lblEmail;
    }

    public void setLblEmail(JLabel lblEmail) {
        this.lblEmail = lblEmail;
    }

    public JLabel getLblErrorEmail() {
        return lblErrorEmail;
    }

    public void setLblErrorEmail(JLabel lblErrorEmail) {
        this.lblErrorEmail = lblErrorEmail;
    }

    public JLabel getLblErrorSifraLength() {
        return lblErrorSifraLength;
    }

    public void setLblErrorSifraLength(JLabel lblErrorSifraLength) {
        this.lblErrorSifraLength = lblErrorSifraLength;
    }

    public JLabel getLblId() {
        return lblId;
    }

    public void setLblId(JLabel lblId) {
        this.lblId = lblId;
    }

    public JLabel getLblIme() {
        return lblIme;
    }

    public void setLblIme(JLabel lblIme) {
        this.lblIme = lblIme;
    }

    public JCheckBox getCkSifra() {
        return ckSifra;
    }

    public void setCkSifra(JCheckBox ckSifra) {
        this.ckSifra = ckSifra;
    }

    public JLabel getLblPrezime() {
        return lblPrezime;
    }

    public void setLblPrezime(JLabel lblPrezime) {
        this.lblPrezime = lblPrezime;
    }

    public JLabel getLblSifra() {
        return lblSifra;
    }

    public void setLblSifra(JLabel lblSifra) {
        this.lblSifra = lblSifra;
    }

    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public void setTxtEmail(JTextField txtEmail) {
        this.txtEmail = txtEmail;
    }

    public JTextField getTxtIme() {
        return txtIme;
    }

    public void setTxtIme(JTextField txtIme) {
        this.txtIme = txtIme;
    }

    public JTextField getTxtPrezime() {
        return txtPrezime;
    }

    public void setTxtPrezime(JTextField txtPrezime) {
        this.txtPrezime = txtPrezime;
    }

    public JTextField getTxtSifra() {
        return txtSifra;
    }

    public void setTxtSifra(JTextField txtSifra) {
        this.txtSifra = txtSifra;
    }

    public JLabel getLblGlavni() {
        return lblGlavni;
    }

    public void setLblGlavni(JLabel lblGlavni) {
        this.lblGlavni = lblGlavni;
    }

    public JTextField getTxtId() {
        return txtId;
    }

    public void setTxtId(JTextField txtId) {
        this.txtId = txtId;
    }

    public void setTxtSifra(JPasswordField txtSifra) {
        this.txtSifra = txtSifra;
    }

    public void addImeListener(DocumentListener l) {
        txtIme.getDocument().addDocumentListener(l);
    }

    public void addSifraListener(DocumentListener l) {
        txtSifra.getDocument().addDocumentListener(l);
    }

    public void addPrezimeListener(DocumentListener l) {
        txtPrezime.getDocument().addDocumentListener(l);
    }

    public void addEmailListener(DocumentListener l) {
        txtEmail.getDocument().addDocumentListener(l);
    }

    public JTextField getTxtid() {
        return txtId;
    }

    public JCheckBox getCkAdmin() {
        return ckAdmin;
    }

    public void setCkAdmin(JCheckBox ckAdmin) {
        this.ckAdmin = ckAdmin;
    }
    

    /**
     * Creates new form ZaposleniView
     */
    public void setTxtid(JTextField txtid) {
        this.txtId = txtid;
    }

    public ZaposleniView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        podesiProzor();
        dodajSliku("/resources/logo.png");
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField5 = new javax.swing.JTextField();
        lblGlavni = new javax.swing.JLabel();
        lblId = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        lblIme = new javax.swing.JLabel();
        lblPrezime = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblSifra = new javax.swing.JLabel();
        txtIme = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtPrezime = new javax.swing.JTextField();
        btnZapamti = new javax.swing.JButton();
        btnObrisi = new javax.swing.JButton();
        lblErrorSifraLength = new javax.swing.JLabel();
        lblErrorEmail = new javax.swing.JLabel();
        txtSifra = new javax.swing.JTextField();
        ckSifra = new javax.swing.JCheckBox();
        ckAdmin = new javax.swing.JCheckBox();

        jTextField5.setText("jTextField3");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Kreiranje zaposlenog");
        setResizable(false);

        lblGlavni.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        lblGlavni.setText("Kreiraj novog zaposlenog");

        lblId.setText("Id:");

        txtId.setEnabled(false);

        lblIme.setText("Ime:");

        lblPrezime.setText("Prezime:");

        lblEmail.setText("Email:");

        lblSifra.setText("Šifra:");

        btnZapamti.setText("Zapamti");

        btnObrisi.setText("Obriši");

        lblErrorSifraLength.setForeground(new java.awt.Color(255, 51, 0));
        lblErrorSifraLength.setText("min. 8 karaktera");

        lblErrorEmail.setForeground(new java.awt.Color(255, 51, 0));
        lblErrorEmail.setText("Mejl mora biti validnog formata.");

        ckSifra.setText("Ne menjaj šifru");

        ckAdmin.setText("Admin?");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(lblId)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblIme)
                                    .addComponent(lblEmail)
                                    .addComponent(lblSifra)
                                    .addComponent(lblPrezime))
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtIme)
                                        .addComponent(txtPrezime)
                                        .addComponent(txtEmail)
                                        .addComponent(txtSifra, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(ckSifra)
                                            .addComponent(ckAdmin)))))
                            .addComponent(lblErrorEmail))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblErrorSifraLength)))
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblGlavni)
                        .addGap(117, 117, 117))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnZapamti, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnObrisi, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(178, 178, 178))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblId)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addComponent(lblGlavni)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIme)
                    .addComponent(txtIme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrezime)
                    .addComponent(txtPrezime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSifra)
                    .addComponent(lblErrorSifraLength)
                    .addComponent(txtSifra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ckAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ckSifra)
                .addGap(18, 18, 18)
                .addComponent(btnZapamti)
                .addGap(18, 18, 18)
                .addComponent(btnObrisi)
                .addGap(18, 18, 18)
                .addComponent(lblErrorEmail)
                .addGap(18, 18, 18))
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
            java.util.logging.Logger.getLogger(ZaposleniView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ZaposleniView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ZaposleniView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ZaposleniView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ZaposleniView dialog = new ZaposleniView(new javax.swing.JFrame(), true);
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
    private javax.swing.JCheckBox ckAdmin;
    private javax.swing.JCheckBox ckSifra;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblErrorEmail;
    private javax.swing.JLabel lblErrorSifraLength;
    private javax.swing.JLabel lblGlavni;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblIme;
    private javax.swing.JLabel lblPrezime;
    private javax.swing.JLabel lblSifra;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtIme;
    private javax.swing.JTextField txtPrezime;
    private javax.swing.JTextField txtSifra;
    // End of variables declaration//GEN-END:variables
}
