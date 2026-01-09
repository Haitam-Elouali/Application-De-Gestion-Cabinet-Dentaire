package ma.TeethCare.mvc.ui.palette.buttons;

import ma.TeethCare.mvc.ui.palette.utils.UIConstants;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PillNavButtonLight extends JButton {

    private boolean active = false;

    public PillNavButtonLight(String text, ImageIcon icon) {
        setText(text);
        setIcon(icon);

        setFont(UIConstants.FONT_BOLD);
        setForeground(UIConstants.TEXT_DARK);

        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setFocusable(false);

        setHorizontalAlignment(SwingConstants.LEFT);
        setHorizontalTextPosition(SwingConstants.RIGHT);
        setIconTextGap(15);

        setBorder(new EmptyBorder(10, 24, 10, 24));
        setPreferredSize(new Dimension(260, 48));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
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

    public void setActive(boolean active) {
        this.active = active;
        repaint();
    }

    public boolean isActive() {
        return active;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (active) {
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, getWidth(), getHeight());

            // Text color for active state
            setForeground(UIConstants.ACCENT_GREEN_800);

            // Specific indicator on the left (optional, can be right or background-based)
            // g2.setColor(UIConstants.ACCENT_GREEN);
            // g2.fillRect(0, 0, 4, getHeight());
        } else if (getModel().isRollover()) {
            g2.setColor(new Color(255, 255, 255, 80));
            g2.fillRect(0, 0, getWidth(), getHeight());
            setForeground(UIConstants.TEXT_DARK);
        } else {
            setForeground(UIConstants.TEXT_DARK);
        }

        g2.dispose();
        super.paintComponent(g);
    }
}
