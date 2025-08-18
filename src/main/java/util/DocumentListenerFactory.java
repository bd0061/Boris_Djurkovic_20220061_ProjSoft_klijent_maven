/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.util.function.Consumer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
/**
 *
 * @author Djurkovic
 */
public class DocumentListenerFactory {
    
    public static DocumentListener create(Consumer<DocumentEvent> c) {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                c.accept(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                c.accept(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                c.accept(e);
            }
            
        };
    }
}
