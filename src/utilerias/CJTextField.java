/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilerias;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;

/**
 *
 * @author Noe
 */
public class CJTextField extends JTextField implements KeyListener {

    private String only;
    public static final String NUMBERS = "numbers";
    public static final String FLOATS = "floats";

    public interface CJTextFieldDelegate {

        void cjtextFieldDelegate(CJTextField cjtextField, KeyEvent keyEvent);
    }
    private CJTextFieldDelegate cJTextFieldDelegateIns;

    public CJTextField() {
    }

    public void setDelegate(CJTextFieldDelegate pDelegate) {
        cJTextFieldDelegateIns = pDelegate;
    }

    public void setOnly(String pOnly) {
        this.addKeyListener(this);
        this.only = pOnly;
    }

    @Override
    public void keyTyped(KeyEvent e) {

        if (cJTextFieldDelegateIns != null) {
            cJTextFieldDelegateIns.cjtextFieldDelegate(this, e);
        }

        if (only.equalsIgnoreCase(NUMBERS)) {
            char c = e.getKeyChar();
            if (!((c >= '0') && (c <= '9')
                    || (c == KeyEvent.VK_BACK_SPACE)
                    || (c == KeyEvent.VK_DELETE))) {
                getToolkit().beep();
                e.consume();
            }
        } else if (only.equalsIgnoreCase(FLOATS)) {
            char c = e.getKeyChar(); // Get the typed character  
            // Don't ignore backspace or delete  
            if (c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                // If the key was not a number then discard it (this is a sloppy way to check)  
                if (!(c == '0' || c == '1' || c == '2' || c == '3' || c == '4'
                        || c == '5' || c == '6' || c == '7' || c == '8' || c == '9' || c == '.')) {
                    e.consume();  // Ignore this key  
                }
                else if(c == '.'){
                    //si es el puno verifica si ya hay punto
                    if(this.getText().contains(".")){
                        e.consume();
                    }
                }
                
                
            }
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
