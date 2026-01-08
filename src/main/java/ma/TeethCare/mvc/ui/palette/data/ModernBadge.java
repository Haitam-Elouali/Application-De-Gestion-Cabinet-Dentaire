package ma.TeethCare.mvc.ui.palette.data;

import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ModernBadge extends JLabel {

    public enum Variant {
        DEFAULT,
        SECONDARY,
        DESTRUCTIVE,
        OUTLINE,
        SUCCESS, // Custom for dashboard (Green)
        WARNING, // Custom (Orange)
        INFO     // Custom (Blue)
    }

    private final Variant variant;

    public ModernBadge(String text, Variant variant) {
        super(text);
        this.variant = variant != null ? variant : Variant.DEFAULT;
        init();
    }

    private void init() {
        setOpaque(false);
        setFont(new Font("Segoe UI", Font.BOLD, 12)); // text-xs font-semibold
        // px-2.5 py-0.5
        setBorder(new EmptyBorder(2, 10, 2, 10)); 
        setHorizontalAlignment(SwingConstants.CENTER);
        
        updateColors();
    }

    private void updateColors() {
        switch (variant) {
            case DEFAULT: // bg-primary text-primary-foreground
                setForeground(TailwindPalette.PRIMARY_FOREGROUND);
                break;
            case SECONDARY: // bg-secondary text-secondary-foreground
                setForeground(TailwindPalette.SECONDARY_FOREGROUND);
                break;
            case DESTRUCTIVE: // bg-destructive text-destructive-foreground
                setForeground(TailwindPalette.DESTRUCTIVE_FOREGROUND);
                break;
            case OUTLINE: // text-foreground
                setForeground(TailwindPalette.FOREGROUND);
                break;
            case SUCCESS: // Green
                setForeground(new Color(21, 128, 61)); // green-700
                break;
            case WARNING: // Orange/Yellow
                setForeground(new Color(194, 65, 12)); // orange-700
                break;
            case INFO: // Blue (diff from primary/secondary usually)
                setForeground(new Color(29, 78, 216)); // blue-700
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        Color bg = getBackgroundColor();
        
        if (bg != null) {
            g2.setColor(bg);
            // rounded-full
            g2.fillRoundRect(0, 0, w, h, h, h);
        }

        if (variant == Variant.OUTLINE) {
            g2.setColor(TailwindPalette.BORDER);
            g2.drawRoundRect(0, 0, w - 1, h - 1, h, h);
        } else if (variant == Variant.SUCCESS) {
             g2.setColor(new Color(187, 247, 208)); // green-200 border maybe? Or just bg.
             // Usually badges has no border unless outline.
        }

        g2.dispose();
        super.paintComponent(g);
    }

    private Color getBackgroundColor() {
        switch (variant) {
            case DEFAULT:
                return TailwindPalette.PRIMARY;
            case SECONDARY:
                return TailwindPalette.SECONDARY;
            case DESTRUCTIVE:
                return TailwindPalette.DESTRUCTIVE;
            case OUTLINE:
                return null;
            case SUCCESS:
                return new Color(220, 252, 231); // green-100
            case WARNING:
                return new Color(255, 237, 213); // orange-100
            case INFO:
                return new Color(219, 234, 254); // blue-100
        }
        return TailwindPalette.PRIMARY;
    }
}
