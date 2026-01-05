package ma.TeethCare.mvc.ui.palette.fields;

import ma.TeethCare.mvc.ui.palette.utils.UIConstants;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class CustomTextField extends JTextField {

    private String hint;
    private Color borderColor = UIConstants.BORDER_GRAY;
    private Color backgroundColor = Color.WHITE;

    public CustomTextField(String hint) {
        this.hint = hint;
        setOpaque(false);
        setBorder(new EmptyBorder(8, 12, 8, 12));
        setFont(UIConstants.FONT_REGULAR);
        setForeground(UIConstants.TEXT_DARK);

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                borderColor = UIConstants.ACCENT_GREEN;
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                borderColor = UIConstants.BORDER_GRAY;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIConstants.ROUNDING, UIConstants.ROUNDING);

        // Border
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIConstants.ROUNDING, UIConstants.ROUNDING);

        g2.dispose();

        // Hint handling
        if (getText().isEmpty() && !isFocusOwner()) {
            Graphics2D gHint = (Graphics2D) g.create();
            gHint.setFont(getFont());
            gHint.setColor(UIConstants.TEXT_GRAY);
            FontMetrics fm = gHint.getFontMetrics();
            int x = getInsets().left;
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            gHint.drawString(hint, x, y);
            gHint.dispose();
        }

        super.paintComponent(g);
    }
}
