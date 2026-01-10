package ma.TeethCare.mvc.ui.dashboard.doctor.components;

import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

    public StatsCard(String title, String value, Type type, ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType iconType) {
        setLayout(new BorderLayout());
        setOpaque(false); // Custom painting
        setBorder(new EmptyBorder(16, 16, 16, 16));
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        
        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLbl.setForeground(Color.GRAY); // Neutral gray for title
        
        JLabel valueLbl = new JLabel(value);
        valueLbl.setFont(new Font("Segoe UI", Font.BOLD, 28)); // Large
        valueLbl.setForeground(new Color(30, 41, 59)); // #1E293B Slate-800
        
        textPanel.add(titleLbl);
        textPanel.add(valueLbl);
        
        add(textPanel, BorderLayout.CENTER);
        
        // Icon circle
        JPanel iconCircle = new JPanel(new BorderLayout()); 
        iconCircle.setOpaque(false);
        iconCircle.setPreferredSize(new Dimension(56, 56)); // Fixed size
        iconCircle.setMinimumSize(new Dimension(56, 56));
        iconCircle.setMaximumSize(new Dimension(56, 56));

        JLabel iconLabel = new JLabel(ma.TeethCare.mvc.ui.palette.utils.IconUtils.getIcon(iconType, 28, type.fg)) {
             @Override
             protected void paintComponent(Graphics g) {
                 Graphics2D g2 = (Graphics2D)g.create();
                 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                 g2.setColor(type.bg); // Light colored circle
                 g2.fillOval(0, 0, getWidth(), getHeight());
                 g2.dispose();
                 super.paintComponent(g);
             }
        };
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setPreferredSize(new Dimension(56, 56));
        
        iconCircle.add(iconLabel, BorderLayout.CENTER);
        add(iconCircle, BorderLayout.EAST);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // White Rounded Card
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        
        super.paintComponent(g);
    }
    

}
