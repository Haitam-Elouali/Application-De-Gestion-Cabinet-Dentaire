package ma.TeethCare.mvc.ui.palette.containers;

import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import java.awt.*;

public class ModernCard extends JPanel {

    public ModernCard() {
        setBackground(TailwindPalette.CARD);
        setOpaque(false); // We paint manually
        // Shadcn Card: items items-start gap-1.5 px-6 pt-6 etc.
        // Base card padding is 0 usually, but content has padding.
        // To be safe and simple, we apply p-6 (24px) to the panel itself.
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24)); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int radius = 12; // rounded-xl

        // Shadow calculation (shadow-sm: 0 1px 2px 0 rgb(0 0 0 / 0.05))
        // We simulate a small shadow by drawing a faint rect below
        g2.setColor(new Color(0, 0, 0, 15)); // 5-6% opacity
        g2.fillRoundRect(1, 2, w - 2, h - 2, radius, radius);

        // Background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, w - 1, h - 2, radius, radius); // Shift height to account for shadow area if needed, but simple overlay is fine for small shadow

        // Border
        g2.setColor(TailwindPalette.BORDER);
        g2.drawRoundRect(0, 0, w - 1, h - 2, radius, radius);

        g2.dispose();
        // Do NOT call super.paintComponent as we handle it all
    }
}
