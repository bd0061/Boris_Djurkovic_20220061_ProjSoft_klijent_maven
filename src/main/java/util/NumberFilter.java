/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import iznajmljivanjeapp.domain.enumeracije.KategorijaEnum;
import java.util.Map;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Djurkovic
 */
import javax.swing.text.*;

public class NumberFilter extends DocumentFilter {

    
    
    
            
            
    
    public enum Mode {
        INTEGER_ONLY,
        POSITIVE_DECIMAL
    }

    private final Mode mode;

    public NumberFilter(Mode mode) {
        this.mode = mode;
    }

    @Override
    public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        if (string == null) return;

        if (isNumericAfterInsert(fb, offset, 0, string)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String string, AttributeSet attr)
            throws BadLocationException {
        if (string == null) return;

        if (isNumericAfterInsert(fb, offset, length, string)) {
            super.replace(fb, offset, length, string, attr);
        }
    }

    private boolean isNumericAfterInsert(DocumentFilter.FilterBypass fb, int offset, int length, String string)
            throws BadLocationException {
        String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
        String newText = currentText.substring(0, offset) + string + currentText.substring(offset + length);

        switch (mode) {
            case INTEGER_ONLY:
                return newText.matches("\\d*");
            case POSITIVE_DECIMAL:
                return newText.matches("\\d*(\\.\\d*)?");
            default:
                return false;
        }
    }
}

