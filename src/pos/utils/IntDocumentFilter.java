/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.utils;

import javax.swing.text.*;

/**
 *
 * @author husainazkas
 */
public class IntDocumentFilter extends DocumentFilter {
    
    final Runnable onInvalid;
    
    public IntDocumentFilter() {
        this.onInvalid = null;
    }
    
    public IntDocumentFilter(Runnable onInvalid) {
        this.onInvalid = onInvalid;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string,
            AttributeSet attr) throws BadLocationException {

        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.insert(offset, string);

        if (test(sb.toString())) {
            super.insertString(fb, offset, string, attr);
        } else {
            onInvalid.run();
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text,
            AttributeSet attrs) throws BadLocationException {

        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.replace(offset, offset + length, text);

        if (test(sb.toString())) {
            super.replace(fb, offset, length, text, attrs);
        } else {
            onInvalid.run();
        }

    }
    
    private boolean test(String text) {
        try {
            Integer.valueOf(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
