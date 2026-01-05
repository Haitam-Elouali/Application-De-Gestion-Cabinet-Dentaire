package ma.TeethCare.mvc.ui.palette.cards;

import ma.TeethCare.mvc.ui.palette.utils.UIConstants;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StatCard extends JPanel {

    public StatCard(String title, String value, String subtitle, ImageIcon icon, Color bgColor, Color textColor) {
        setLayout(new BorderLayout(15, 0));
        setBackground(bgColor);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 1),
                new EmptyBorder(15, 20, 15, 20)));

        // Info Panel (Title, Value, Subtitle)
        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JLabel lblTitle = new JLabel(title.toUpperCase());
        lblTitle.setFont(UIConstants.FONT_BOLD.deriveFont(12f));
        lblTitle.setForeground(textColor.darker());

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(UIConstants.FONT_TITLE.deriveFont(28f));
        lblValue.setForeground(textColor);

        JLabel lblSubtitle = new JLabel(subtitle);
        lblSubtitle.setFont(UIConstants.FONT_REGULAR.deriveFont(12f));
        lblSubtitle.setForeground(textColor.darker());

        infoPanel.add(lblTitle);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(lblValue);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(lblSubtitle);

        // Icon Panel (Right side)
        JLabel lblIcon = new JLabel(icon);
        lblIcon.setOpaque(true);
        lblIcon.setBackground(Color.WHITE);
        lblIcon.setPreferredSize(new Dimension(50, 50));
        lblIcon.setHorizontalAlignment(SwingConstants.CENTER);

        // Circular icon container workaround
        JPanel iconPanel = new JPanel(new GridBagLayout());
        iconPanel.setOpaque(false);
        iconPanel.add(lblIcon);

        add(infoPanel, BorderLayout.CENTER);
        add(iconPanel, BorderLayout.EAST);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), UIConstants.ROUNDING * 2, UIConstants.ROUNDING * 2);
        g2.dispose();
    }
}
