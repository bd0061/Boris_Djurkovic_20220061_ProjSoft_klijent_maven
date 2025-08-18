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

/**
 *
 * @author Djurkovic
 */
public class VozacView extends javax.swing.JDialog {
    
    public void addObrisiListener(ActionListener l) {
        btnObrisi.addActionListener(l);
    }
    
    public void setObrisiVisible(boolean b) {
        btnObrisi.setVisible(b);
    }
    
    public void setIdVisible(boolean b) {
        txtId.setVisible(b);
        lblId.setVisible(b);
    }

    public void addZapamtiListener(ActionListener l) {
        btnKreiraj.addActionListener(l);
    }
    
    public void setKreirajEnabled(boolean v) {
        btnKreiraj.setEnabled(v);
    }
    
    public JButton getBtnKreiraj() {
        return btnKreiraj;
    }

    public void setBtnKreiraj(JButton btnKreiraj) {
        this.btnKreiraj = btnKreiraj;
    }

    public JComboBox<String> getComboBoxDozvole() {
        return comboBoxDozvole;
    }

    public void setComboBoxDozvole(JComboBox<String> comboBoxDozvole) {
        this.comboBoxDozvole = comboBoxDozvole;
    }

    public JLabel getjLabel1() {
        return lblGlavni;
    }

    public void setjLabel1(JLabel jLabel1) {
        this.lblGlavni = jLabel1;
    }

    public JLabel getLblDozvola() {
        return lblDozvola;
    }

    public void setLblDozvola(JLabel lblDozvola) {
        this.lblDozvola = lblDozvola;
    }

    public JLabel getLblEmail() {
        return lblEmail;
    }

    public void setLblEmail(JLabel lblEmail) {
        this.lblEmail = lblEmail;
    }

    public JLabel getLblIme() {
        return lblIme;
    }

    public void setLblIme(JLabel lblIme) {
        this.lblIme = lblIme;
    }

    public JLabel getLblPrezime() {
        return lblPrezime;
    }

    public void setLblPrezime(JLabel lblPrezime) {
        this.lblPrezime = lblPrezime;
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

    /**
     * Creates new form VozacView
     */
    public void setTxtPrezime(JTextField txtPrezime) {    
        this.txtPrezime = txtPrezime;
    }

    public VozacView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        podesiProzor();
        dodajSliku("/resources/logo.png");
    }

    public void napuniComboBox(List<String> s) {
        comboBoxDozvole.removeAllItems();
        for (var i : s) {
            comboBoxDozvole.addItem(i);
        }
    }
    
    public void setError(String msg) {
        lblError.setText(msg);
    }

    public void addImeListener(DocumentListener l) {
        txtIme.getDocument().addDocumentListener(l);
    }

    public JLabel getLblGlavni() {
        return lblGlavni;
    }

    public void setLblGlavni(JLabel lblGlavni) {
        this.lblGlavni = lblGlavni;
    }

    public JButton getBtnObrisi() {
        return btnObrisi;
    }

    public void setBtnObrisi(JButton btnObrisi) {
        this.btnObrisi = btnObrisi;
    }

    public JLabel getLblError() {
        return lblError;
    }

    public void setLblError(JLabel lblError) {
        this.lblError = lblError;
    }

    public JLabel getLblId() {
        return lblId;
    }

    public void setLblId(JLabel lblId) {
        this.lblId = lblId;
    }

    public JTextField getTxtId() {
        return txtId;
    }

    public void setTxtId(JTextField txtId) {
        this.txtId = txtId;
    }

    public void addPrezimeListener(DocumentListener l) {
        txtPrezime.getDocument().addDocumentListener(l);
    }

    public void addEmailListener(DocumentListener l) {
        txtEmail.getDocument().addDocumentListener(l);
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

        lblGlavni = new javax.swing.JLabel();
        lblIme = new javax.swing.JLabel();
        lblPrezime = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblDozvola = new javax.swing.JLabel();
        txtIme = new javax.swing.JTextField();
        txtPrezime = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        comboBoxDozvole = new javax.swing.JComboBox<>();
        btnKreiraj = new javax.swing.JButton();
        lblError = new javax.swing.JLabel();
        btnObrisi = new javax.swing.JButton();
        lblId = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Kreiraj novog vozača");
        setResizable(false);

        lblGlavni.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        lblGlavni.setText("Kreiraj novog vozača");

        lblIme.setText("Ime:");

        lblPrezime.setText("Prezime:");

        lblEmail.setText("Email:");

        lblDozvola.setText("Dozvola:");

        txtIme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtImeActionPerformed(evt);
            }
        });

        txtPrezime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrezimeActionPerformed(evt);
            }
        });

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        comboBoxDozvole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));

        btnKreiraj.setText("Zapamti");
        btnKreiraj.setEnabled(false);

        lblError.setForeground(new java.awt.Color(255, 0, 0));

        btnObrisi.setText("Obriši");
        btnObrisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnObrisiActionPerformed(evt);
            }
        });

        lblId.setText("Id:");

        txtId.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(123, 123, 123)
                .addComponent(lblGlavni)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblIme)
                                .addGap(34, 34, 34)
                                .addComponent(txtIme, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblPrezime)
                                    .addComponent(lblEmail))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtPrezime)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblDozvola)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(comboBoxDozvole, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lblId)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(130, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblError)
                        .addGap(107, 107, 107))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnKreiraj, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnObrisi, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(174, 174, 174))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblId)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblGlavni)
                .addGap(34, 34, 34)
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
                    .addComponent(comboBoxDozvole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDozvola))
                .addGap(15, 15, 15)
                .addComponent(btnKreiraj)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnObrisi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblError)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtImeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtImeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtImeActionPerformed

    private void txtPrezimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrezimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrezimeActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void btnObrisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObrisiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnObrisiActionPerformed

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
            java.util.logging.Logger.getLogger(VozacView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VozacView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VozacView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VozacView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                VozacView dialog = new VozacView(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnKreiraj;
    private javax.swing.JButton btnObrisi;
    private javax.swing.JComboBox<String> comboBoxDozvole;
    private javax.swing.JLabel lblDozvola;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblError;
    private javax.swing.JLabel lblGlavni;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblIme;
    private javax.swing.JLabel lblPrezime;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtIme;
    private javax.swing.JTextField txtPrezime;
    // End of variables declaration//GEN-END:variables
}
