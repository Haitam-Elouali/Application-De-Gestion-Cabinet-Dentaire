package ma.TeethCare.mvc.ui.palette.containers;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    private int cornerRadius = 20;
    private Color backgroundColor = Color.WHITE;
    private boolean hasShadow = true;
    private int shadowSize = 3;
    private float shadowOpacity = 0.1f;

    public RoundedPanel() {
        super();
        setOpaque(false);
    }

    public RoundedPanel(int radius) {
        this();
        this.cornerRadius = radius;
    }
    
    public RoundedPanel(LayoutManager layout) {
        this();
        setLayout(layout);
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        repaint();
    }
    
    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        this.backgroundColor = bg;
        repaint();
    }

    public void setHasShadow(boolean hasShadow) {
        this.hasShadow = hasShadow;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int shadowOffset = hasShadow ? shadowSize : 0;
        
        // Draw Shadow
        if (hasShadow) {
             g2.setColor(new Color(0, 0, 0, (int)(255 * shadowOpacity)));
             // Simple bottom-right offset shadow for performance
             g2.fillRoundRect(shadowOffset, shadowOffset, width - shadowOffset - 1, height - shadowOffset - 1, cornerRadius, cornerRadius);
        }

        // Draw Panel Background
        g2.setColor(backgroundColor);
        // Adjust bounds to leave space for shadow if needed, but typically shadow is outside or just under
        // Here we draw content slightly smaller if shadow is present to show it
        if (hasShadow) {
             g2.fillRoundRect(0, 0, width - shadowOffset - 1, height - shadowOffset - 1, cornerRadius, cornerRadius);
        } else {
             g2.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);
        }

        g2.dispose();
        // Do NOT call super.paintComponent
    }
}
