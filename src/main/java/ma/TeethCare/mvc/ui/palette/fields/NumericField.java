package ma.TeethCare.mvc.ui.palette.fields;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class NumericField extends JTextField {
    Font gainFont = new Font("Optima", Font.BOLD, 17);
    Font lostFont = new Font("Optima", Font.PLAIN, 14);
    Color gainColor = new Color(186, 85, 211);
    Color lostColor = Color.GRAY;
    String hint;
    Integer value;
    public NumericField(final String hint){
        this.hint = hint;
        setText(hint);
        setFont(lostFont);
        setForeground(lostColor);
        setHorizontalAlignment(JTextField.CENTER);
        setBorder(BorderFactory.createLineBorder(Color.black, 2));

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(hint)) {
                    setText("");
                    setFont(gainFont);
                    setForeground(lostColor);
                } else {
                    setText(getText());
                    setFont(gainFont);
                    setForeground(gainColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().equals(hint)|| getText().length()==0) {
                    setText(hint);
                    setFont(lostFont);
                    setForeground(lostColor);
                } else {
                    setText(getText());
                    setFont(gainFont);
                    setForeground(gainColor);
                }
            }
        });
        addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE))
                        {
                            e.consume();  // ignorer l'événement
                        }
            }
        });


    }

    public Integer getValue() {
        if(getText().equals(hint)|| getText().length()==0){
            return null;
        }
        else return Integer.parseInt(getText());
    }

    @Override
    public void setText(String t) {
        setFont(gainFont);
        setForeground(gainColor);
        super.setText(t);
    }
}
