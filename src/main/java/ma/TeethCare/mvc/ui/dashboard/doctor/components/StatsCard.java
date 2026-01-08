package ma.TeethCare.mvc.ui.dashboard.doctor.components;

import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import java.awt.*;

public class StatsCard extends JPanel {

    public enum Type {
        BLUE(TailwindPalette.PRIMARY, new Color(239, 246, 255)), // blue-50
        GREEN(new Color(22, 163, 74), new Color(240, 253, 244)), // green-600, green-50
        PURPLE(new Color(147, 51, 234), new Color(250, 245, 255)), // purple-600, purple-50
        ORANGE(new Color(234, 88, 12), new Color(255, 247, 237)); // orange-600, orange-50

        final Color fg;
        final Color bg;
        Type(Color fg, Color bg) { this.fg = fg; this.bg = bg; }
    }

    public StatsCard(String title, String value, Type type) {
        setLayout(new BorderLayout());
        setBackground(type.bg); // bg-color-50
        // border border-color-100 rounded-lg
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(type.bg.getRed()-10, type.bg.getGreen()-10, type.bg.getBlue()-10)), // darker border
            BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        
        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLbl.setForeground(type.fg.darker()); // text-color-800
        
        JLabel valueLbl = new JLabel(value);
        valueLbl.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLbl.setForeground(type.fg); // text-color-600
        
        textPanel.add(titleLbl);
        textPanel.add(valueLbl);
        
        add(textPanel, BorderLayout.CENTER);
        
        // Icon circle (Placeholder visual)
        JPanel iconCircle = new JPanel() {
             @Override
             protected void paintComponent(Graphics g) {
                 super.paintComponent(g);
                 Graphics2D g2 = (Graphics2D)g;
                 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                 g2.setColor(new Color(type.fg.getRed(), type.fg.getGreen(), type.fg.getBlue(), 50)); // light circle
                 g2.fillOval(0, 0, getWidth(), getHeight());
             }
        };
        iconCircle.setOpaque(false);
        iconCircle.setPreferredSize(new Dimension(48, 48));
        add(iconCircle, BorderLayout.EAST);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Custom rounded border painting if needed, but standard border is okay for now.
        // To match rounded-lg (8px), we might need custom painting if BorderFactory doesn't look round enough.
        // For standard JLabel/JPanel, setBackground handles rect.
    }
}
