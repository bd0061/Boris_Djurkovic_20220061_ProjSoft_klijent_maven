/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import com.formdev.flatlaf.FlatDarkLaf;
import kontroler.LoginKontroler;
import view.LoginView;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;
import model.LoginModel;

/**
 *
 * @author Djurkovic
 */




public class Main {

    public static void main(String[] args) {
        try {
            UIManager.installLookAndFeel("FlatLaf Light", "com.formdev.flatlaf.FlatLightLaf");
            UIManager.installLookAndFeel("FlatLaf Dark", "com.formdev.flatlaf.FlatDarkLaf");
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginView view = new LoginView();
                LoginModel model = new LoginModel();
                LoginKontroler p = new LoginKontroler(view, model);
                view.setVisible(true);
            }
        });
    }
}
