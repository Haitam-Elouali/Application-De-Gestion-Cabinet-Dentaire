package ma.TeethCare.mvc.ui.palette.notification;


import javax.swing.*;
import java.awt.*;

public final class BadgeIcon implements Icon {

    private final Icon base;
    private final int count;
    private final Color badgeColor;

    private final int badgeSize;
    private final int offsetX;
    private final int offsetY;

    private BadgeIcon(Icon base, int count, Color badgeColor,
                      int badgeSize, int offsetX, int offsetY) {
        this.base = base;
        this.count = count;
        this.badgeColor = badgeColor;
        this.badgeSize = badgeSize;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public static Icon withCount(Icon base, int count, Color color) {
        if (base == null) return null;
        if (count <= 0) return base;

        // badgeSize et offsets ajustables ici
        return new BadgeIcon(base, count, color, 25, 0, 0);
    }

    @Override public int getIconWidth()  { return base.getIconWidth(); }
    @Override public int getIconHeight() { return base.getIconHeight(); }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1) base icon
        base.paintIcon(c, g2, x, y);

        // 2) badge position (top-right)
        int bx = x + getIconWidth() - badgeSize + offsetX;
        int by = y + offsetY;

        // 3) badge circle
        g2.setColor(badgeColor);
        g2.fillOval(bx, by, badgeSize, badgeSize);

        // petite bordure blanche (pro)
        g2.setStroke(new BasicStroke(1f));
        g2.setColor(new Color(255, 255, 255, 230));
        g2.drawOval(bx, by, badgeSize, badgeSize);

        // 4) text
        String txt = count > 99 ? "99+" : String.valueOf(count);

        // Police + auto-fit simple
        int fontSize = (txt.length() <= 2) ? 14 : 12;
        g2.setFont(new Font("Optima", Font.BOLD, fontSize));
        FontMetrics fm = g2.getFontMetrics();

        int tx = bx + (badgeSize - fm.stringWidth(txt)) / 2;
        int ty = by + (badgeSize + fm.getAscent() - fm.getDescent()) / 2;

        g2.setColor(Color.WHITE);
        g2.drawString(txt, tx, ty);

        g2.dispose();
    }
}
