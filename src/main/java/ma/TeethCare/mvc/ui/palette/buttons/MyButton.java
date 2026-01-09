package ma.TeethCare.mvc.ui.palette.buttons;

import ma.TeethCare.mvc.ui.palette.utils.UIConstants;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class MyButton extends JButton {

    private final ImageIcon normalIcon;
    private final Color backgroundColor;
    private final Color hoverColor;

    public MyButton(String text, ImageIcon icon, Font font) {
        this.normalIcon = icon;
        this.backgroundColor = UIConstants.ACCENT_GREEN;
        this.hoverColor = UIConstants.ACCENT_GREEN_800;

        setText(text);
        setFont(font != null ? font : UIConstants.FONT_BOLD);
        setIcon(normalIcon);
        setForeground(Color.WHITE);

        setFocusable(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);

        setHorizontalAlignment(SwingConstants.CENTER);
        setIconTextGap(10);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isPressed()) {
            g2.setColor(hoverColor.darker());
        } else if (getModel().isRollover()) {
            g2.setColor(hoverColor);
        } else {
            g2.setColor(backgroundColor);
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), UIConstants.ROUNDING, UIConstants.ROUNDING);
        g2.dispose();

        super.paintComponent(g);
    }
}
