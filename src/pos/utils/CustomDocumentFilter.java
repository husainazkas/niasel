/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.utils;

import javax.swing.text.*;
import pos.interfaces.TestSatisfaction;

/**
 *
 * @author husainazkas
 */
public class CustomDocumentFilter extends DocumentFilter {

    private boolean isCanEmpty = true;
    private boolean isMustNumber = true;
    private Integer minLength;
    private Integer maxLength;
    private TestSatisfaction testSatisfaction;

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
            if (testSatisfaction != null) {
                testSatisfaction.orElse();
            }
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
            if (testSatisfaction != null) {
                testSatisfaction.orElse();
            }
        }
    }

    public void setIsCanEmpty(boolean isCanEmpty) {
        this.isCanEmpty = isCanEmpty;
    }

    public void setIsMustNumber(boolean isMustNumber) {
        this.isMustNumber = isMustNumber;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public void setTestSatisfaction(TestSatisfaction testSatisfaction) {
        this.testSatisfaction = testSatisfaction;
    }

    private boolean test(String text) {
        if (!isCanEmpty && text.isBlank()) {
            return false;
        }

        if (isMustNumber) {
            try {
                Long.valueOf(text);
            } catch (NumberFormatException ex) {
                if (!text.isBlank()) {
                    return false;
                }
            }
        }

        if (minLength != null && text.length() < minLength && !isCanEmpty) {
            return false;
        }

        if (maxLength != null && text.length() > maxLength) {
            return false;
        }

        return !(testSatisfaction != null && !testSatisfaction.test(text));
    }

}
