/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.UI;

import java.awt.Window;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

/**
 *
 * @author Djurkovic
 */
public class ShortCutUtils {

    public static void addWindowShortcut(Window window, KeyStroke keyStroke, Runnable action) {
        JRootPane rootPane = null;
        if (window instanceof JFrame) {
            rootPane = ((JFrame) window).getRootPane();
        } else if (window instanceof JDialog) {
            rootPane = ((JDialog) window).getRootPane();
        } else {
            throw new IllegalArgumentException("nepoznat prozozr: " + window.getClass());
        }

        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = rootPane.getActionMap();

        String actionKey = "shortcut-" + keyStroke.toString();

        inputMap.put(keyStroke, actionKey);
        actionMap.put(actionKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.run();
            }
        });
    }
}
