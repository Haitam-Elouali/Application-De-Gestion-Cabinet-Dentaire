package ma.TeethCare.mvc.ui.palette.buttons;


import javax.swing.*;
import java.awt.*;

public final class BadgeIconV2 {

    private BadgeIconV2(){}

    public static Icon withCount(Icon base, int count) {
        int c = Math.max(0, count);

        return new Icon() {
            @Override public int getIconWidth() { return base.getIconWidth(); }
            @Override public int getIconHeight() { return base.getIconHeight(); }

            @Override public void paintIcon(Component comp, Graphics g, int x, int y) {
                base.paintIcon(comp, g, x, y);
                if (c <= 0) return;

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                String text = (c > 99) ? "99+" : String.valueOf(c);
                int r = 14; // rayon badge
                int bx = x + getIconWidth() - r - 2;
                int by = y + 2;

                // cercle rouge
                g2.setColor(new Color(220, 53, 69));
                g2.fillOval(bx, by, r, r);

                // texte blanc
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Dialog", Font.BOLD, 10));
                FontMetrics fm = g2.getFontMetrics();
                int tw = fm.stringWidth(text);
                int th = fm.getAscent();

                int tx = bx + (r - tw) / 2;
                int ty = by + (r + th) / 2 - 2;
                g2.drawString(text, tx, ty);

                g2.dispose();
            }
        };
    }
}
