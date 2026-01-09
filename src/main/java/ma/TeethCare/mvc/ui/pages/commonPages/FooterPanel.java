package ma.TeethCare.mvc.ui.pages.commonPages;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class FooterPanel extends JPanel {

    public FooterPanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(8, 14, 8, 14));
        setBackground(new Color(250, 250, 250));
        setOpaque(true);

        JLabel left = new JLabel("© EMSI — DentalTech");
        left.setFont(new Font("Optima", Font.PLAIN, 13));
        left.setForeground(new Color(110, 110, 110));

        JLabel right = new JLabel("v1.0");
        right.setFont(new Font("Optima", Font.PLAIN, 13));
        right.setForeground(new Color(110, 110, 110));

        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
    }
}
