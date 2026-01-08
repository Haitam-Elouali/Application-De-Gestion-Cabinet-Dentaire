package ma.TeethCare.mvc.ui.palette.fields;

import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ModernTextField extends JTextField {

    private boolean isFocused = false;

    public ModernTextField(String text) {
        super(text);
        init();
    }

    public ModernTextField() {
        super();
        init();
    }

    private void init() {
        setOpaque(false); 
        // globals.css: --input-background: #f3f3f5
        setBackground(TailwindPalette.INPUT_BACKGROUND);
        setForeground(TailwindPalette.FOREGROUND);
        setCaretColor(TailwindPalette.FOREGROUND);
        setFont(new Font("Segoe UI", Font.PLAIN, 14)); // text-sm
        
        // px-3 (12px), py-1 (4px)
        // h-9 (36px). If font size is 14 (+line height ~19), vertical padding needs to sum to ~17px.
        // py-1 in Tailwind is 0.25rem = 4px.
        // 4+19+4 = 27px. That's too small for h-9. 
        // Shadcn Input is `flex h-9 py-1`. The flex alignment centers it.
        // In Swing, to get 36px height, we need padding.
        // Let's use 8px vertical padding to get closer to 36px total.
        setBorder(new EmptyBorder(6, 12, 6, 12));

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                isFocused = true;
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                isFocused = false;
                repaint();
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        if (d.height < 36) d.height = 36; // h-9
        return d;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, w, h, 8, 8); // rounded-md

        // Border / Ring
        if (isFocused) {
            // Ring (Blue 500 equivalent)
            g2.setColor(new Color(TailwindPalette.RING.getRed(), TailwindPalette.RING.getGreen(), TailwindPalette.RING.getBlue(), 128)); 
            g2.setStroke(new BasicStroke(3f));
            g2.drawRoundRect(1, 1, w - 3, h - 3, 8, 8);
            
            g2.setColor(TailwindPalette.RING);
            g2.setStroke(new BasicStroke(1f));
            g2.drawRoundRect(0, 0, w - 1, h - 1, 8, 8);
        } else {
            g2.setColor(TailwindPalette.BORDER_INPUT);
            g2.setStroke(new BasicStroke(1f));
            g2.drawRoundRect(0, 0, w - 1, h - 1, 8, 8);
        }

        g2.dispose();
        super.paintComponent(g);
    }
}
