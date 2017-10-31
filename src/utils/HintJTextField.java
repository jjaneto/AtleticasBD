package utils;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;

/**
 *
 * @author jjaneto
 */
public class HintJTextField extends JTextField implements FocusListener{

    private final String hint;
    private boolean showHint;

    public HintJTextField(String hint) {
        super(hint);
        super.setForeground(Color.LIGHT_GRAY);
        this.hint = hint;    
        this.showHint = true;
        super.addFocusListener(this);
    }   

    @Override
    public void focusGained(FocusEvent e) {
        if(this.getText().isEmpty()){
            super.setForeground(Color.BLACK);
            super.setText("");
            showHint = false;
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if(this.getText().isEmpty()){
            super.setForeground(Color.LIGHT_GRAY);
            super.setText(hint);
            showHint = true;
        }
    }
    
    @Override
    public String getText(){
        return showHint ? "" : super.getText();
    }

    public boolean isShowHint() {
        return showHint;
    }
    
    
    
}
