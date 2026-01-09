package ma.TeethCare.mvc.ui.palette.buttons;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NavButton extends MyButton {

    private boolean active = false;

    private final Color activeBg  = new Color(230, 242, 255);
    private final Color activeBar = new Color(25, 118, 210);

    public NavButton(String text, ImageIcon icon) {
        super(text, icon, new Font("Optima", Font.BOLD, 20));
        setOpaque(true);
    }

    public void setActive(boolean active) {
        this.active = active;

        if (active) {
            setBackground(activeBg);
            setForeground(activeBar);
        } else {
            setBackground(new Color(255, 255, 255));
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (active) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(activeBar);
            g2.fillRoundRect(4, 6, 6, getHeight() - 12, 6, 6);
            g2.dispose();
        }
    }
}
