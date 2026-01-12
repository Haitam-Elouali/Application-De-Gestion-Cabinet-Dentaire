package ma.TeethCare.mvc.ui.palette.buttons;

import ma.TeethCare.mvc.ui.palette.utils.ImageTools;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class ModernButton extends JButton {

    public enum Variant {
        DEFAULT,
        DESTRUCTIVE,
        OUTLINE,
        SECONDARY,
        GHOST,
        LINK,
        SUCCESS
    }

    private Variant variant;
    private boolean isHovered = false;
    private boolean isPressed = false;

    public ModernButton(String text) {
        this(text, null, Variant.DEFAULT);
    }

    public ModernButton(String text, Variant variant) {
        this(text, null, variant);
    }

    public ModernButton(String text, Icon icon, Variant variant) {
        super(text);
        this.variant = (variant != null) ? variant : Variant.DEFAULT;
        if (icon != null) {
            setIcon(icon);
        }

        // text-sm (14px), font-medium (500)
        setFont(new Font("Segoe UI", Font.PLAIN, 14)); 
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // h-9 (36px) means vertical padding approx 8px if font is 14px line-height.
        // px-4 (16px) horizontal.
        // We set a preferred size or margin to emulate this, but border padding is safest.
        setMargin(new Insets(8, 16, 8, 16));
        setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                isPressed = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                repaint();
            }
        });
        
        updateForeground();
    }

    public void setVariant(Variant variant) {
        this.variant = (variant != null) ? variant : Variant.DEFAULT;
        updateForeground();
        repaint();
    }

    private void updateForeground() {
        switch (variant) {
            case DEFAULT:
                setForeground(TailwindPalette.PRIMARY_FOREGROUND);
                break;
            case DESTRUCTIVE:
                setForeground(TailwindPalette.DESTRUCTIVE_FOREGROUND);
                break;
            case OUTLINE:
            case GHOST:
            case SECONDARY:
            case LINK:
                setForeground(TailwindPalette.PRIMARY); 
                break;
            case SUCCESS:
                setForeground(Color.WHITE);
                break;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        // Force height to be at least 36px (h-9)
        if (d.height < 36) d.height = 36;
        return d;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        Color bg = getBackgroundColor();
        Color border = getBorderColor();

        // rounded-md = 8px radius
        int radius = 8;

        if (bg != null) {
            g2.setColor(bg);
            g2.fillRoundRect(0, 0, w, h, radius, radius);
        }

        if (border != null) {
            g2.setColor(border);
            g2.drawRoundRect(0, 0, w - 1, h - 1, radius, radius);
        }
        
        if (variant == Variant.LINK && isHovered) {
             g2.setColor(getForeground());
             FontMetrics fm = g2.getFontMetrics();
             int textWidth = fm.stringWidth(getText());
             
             // Simple centering calculation
             Rectangle iconRect = new Rectangle();
             Rectangle textRect = new Rectangle();
             Rectangle viewRect = new Rectangle(0, 0, w, h);
             
             SwingUtilities.layoutCompoundLabel(this, fm, getText(), getIcon(), 
                 getVerticalAlignment(), getHorizontalAlignment(),
                 getVerticalTextPosition(), getHorizontalTextPosition(),
                 viewRect, iconRect, textRect, getIconTextGap());
                 
             int y = textRect.y + fm.getAscent() + 2;
             g2.drawLine(textRect.x, y, textRect.x + textRect.width, y);
        }

        g2.dispose();
        super.paintComponent(g);
    }

    private Color getBackgroundColor() {
        switch (variant) {
            case DEFAULT:
                // hover:bg-primary/90
                if (isPressed) return new Color(TailwindPalette.PRIMARY.getRed(), TailwindPalette.PRIMARY.getGreen(), TailwindPalette.PRIMARY.getBlue(), 200);
                if (isHovered) return new Color(TailwindPalette.PRIMARY.getRed(), TailwindPalette.PRIMARY.getGreen(), TailwindPalette.PRIMARY.getBlue(), 230); // ~90%
                return TailwindPalette.PRIMARY;
                
            case DESTRUCTIVE:
                if (isHovered) return new Color(TailwindPalette.DESTRUCTIVE.getRed(), TailwindPalette.DESTRUCTIVE.getGreen(), TailwindPalette.DESTRUCTIVE.getBlue(), 230);
                return TailwindPalette.DESTRUCTIVE;
                
            case OUTLINE:
                // hover:bg-accent
                if (isHovered) return TailwindPalette.ACCENT;
                return TailwindPalette.BACKGROUND; // bg-background (white usually)
                
            case SECONDARY:
                // hover:bg-secondary/80
                if (isHovered) return new Color(TailwindPalette.SECONDARY.getRed(), TailwindPalette.SECONDARY.getGreen(), TailwindPalette.SECONDARY.getBlue(), 204); // ~80%
                return TailwindPalette.SECONDARY;

            case GHOST:
                if (isHovered) return TailwindPalette.ACCENT;
                return null;

            case LINK:
                return null;
                
            case SUCCESS:
                if (isPressed) return new Color(16, 185, 129, 200); // #10B981
                if (isHovered) return new Color(16, 185, 129, 230);
                return new Color(16, 185, 129); // Emerald Green #10B981
        }
        return TailwindPalette.PRIMARY;
    }

    private Color getBorderColor() {
        if (variant == Variant.OUTLINE) {
            // border-input (which is standard border color usually)
            return TailwindPalette.BORDER; 
        }
        return null;
    }
}
