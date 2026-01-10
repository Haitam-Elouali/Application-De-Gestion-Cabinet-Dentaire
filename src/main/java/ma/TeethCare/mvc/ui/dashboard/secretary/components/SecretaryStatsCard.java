package ma.TeethCare.mvc.ui.dashboard.secretary.components;

import ma.TeethCare.mvc.ui.palette.utils.IconUtils;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SecretaryStatsCard extends JPanel {

    public enum Type {
        BLUE(TailwindPalette.BLUE_600, TailwindPalette.BLUE_100, TailwindPalette.BLUE_50),
        GREEN(TailwindPalette.GREEN_600, TailwindPalette.GREEN_100, TailwindPalette.GREEN_50),
        ORANGE(new Color(234, 88, 12), TailwindPalette.ORANGE_100, new Color(255, 247, 237)), // orange-600, orange-100,
                                                                                              // orange-50
        PURPLE(new Color(147, 51, 234), new Color(243, 232, 255), new Color(250, 245, 255)); // purple-600, purple-100,
                                                                                             // purple-50

        final Color fg;
        final Color accent; // For icon background / badge background
        final Color bg; // For card background (optional, usually white now)

        Type(Color fg, Color accent, Color bg) {
            this.fg = fg;
            this.accent = accent;
            this.bg = bg;
        }
    }

    public SecretaryStatsCard(String title, String value, String badgeText, IconUtils.IconType iconType, Type type) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE); // Assuming white cards based on "badge has colored background" requirement
                                    // usually implying contrast
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TailwindPalette.BORDER),
                new EmptyBorder(20, 20, 20, 20)));

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLbl.setForeground(TailwindPalette.GRAY_800);
        titleLbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel valueLbl = new JLabel(value);
        valueLbl.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLbl.setForeground(TailwindPalette.GRAY_800);
        valueLbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        contentPanel.add(titleLbl);
        contentPanel.add(Box.createVerticalStrut(8));
        contentPanel.add(valueLbl);
        contentPanel.add(Box.createVerticalStrut(8));

        // Badge (Mini text with colored background)
        if (badgeText != null && !badgeText.isEmpty()) {
            JPanel badge = new JPanel(new BorderLayout());
            badge.setBackground(type.accent);
            badge.setBorder(new EmptyBorder(4, 8, 4, 8));
            // Rounded corners via custom paint if needed, stick to simple for now or assume
            // LookAndFeel handles it?
            // To make it rounded, we wrapp it.

            JLabel badgeLbl = new JLabel(badgeText);
            badgeLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            badgeLbl.setForeground(type.fg);

            badge.add(badgeLbl, BorderLayout.CENTER);
            badge.setMaximumSize(badge.getPreferredSize());
            badge.setAlignmentX(Component.LEFT_ALIGNMENT);

            // To support rounded corners on the badge
            JPanel roundedBadge = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(type.accent);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                    super.paintComponent(g);
                }
            };
            roundedBadge.setOpaque(false);
            roundedBadge.setBorder(new EmptyBorder(4, 8, 4, 8));
            roundedBadge.add(badgeLbl);
            roundedBadge.setMaximumSize(parsedSize(badgeLbl));
            roundedBadge.setAlignmentX(Component.LEFT_ALIGNMENT);

            contentPanel.add(roundedBadge);
        }

        add(contentPanel, BorderLayout.CENTER);

        // Icon Panel (Right)
        JPanel iconPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(type.accent);
                g2.fillOval(0, 0, getWidth(), getHeight());
            }
        };
        iconPanel.setOpaque(false);
        iconPanel.setPreferredSize(new Dimension(50, 50));
        iconPanel.setLayout(new GridBagLayout()); // Center icon

        JLabel iconLbl = new JLabel(IconUtils.getIcon(iconType, 24, type.fg));
        iconPanel.add(iconLbl);

        JPanel rightContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightContainer.setOpaque(false);
        rightContainer.add(iconPanel);

        add(rightContainer, BorderLayout.EAST);
    }

    private Dimension parsedSize(Component c) {
        Dimension d = c.getPreferredSize();
        return new Dimension(d.width + 16, d.height + 8);
    }
}
