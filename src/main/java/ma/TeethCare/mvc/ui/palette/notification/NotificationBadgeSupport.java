package ma.TeethCare.mvc.ui.palette.notification;


import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public final class NotificationBadgeSupport {

    private NotificationBadgeSupport() {}

    // Couleurs par niveau (tu peux mapper vers ta palette)
    public static Color colorFor(NotificationLevel level) {
        return switch (level) {
            case INFO    -> new Color(52, 152, 219);   // bleu
            case WARNING -> new Color(241, 196, 15);   // jaune
            case URGENT  -> new Color(231, 76, 60);    // rouge
        };
    }

    public static final class Controller {
        private final JButton button;
        private final Icon baseIcon;

        private int lastCount = 0;
        private NotificationLevel lastLevel = NotificationLevel.INFO;

        // pulse
        private Timer pulseTimer;
        private int pulseStep = 0;

        private Controller(JButton button) {
            this.button = Objects.requireNonNull(button);
            this.baseIcon = button.getIcon(); // icône 🔔 d'origine
            if (this.baseIcon == null)
                throw new IllegalStateException("Le bouton doit avoir une icône de base avant d’installer le badge.");
        }

        public void setCount(int count) {
            setCount(count, lastLevel);
        }

        public void setCount(int count, NotificationLevel level) {
            this.lastLevel = (level != null ? level : NotificationLevel.INFO);

            // Tooltip dynamique
            button.setToolTipText(
                    count > 0 ? "Vous avez " + count + " notification(s)" : "Aucune notification"
            );

            // Badge
            Color color = colorFor(this.lastLevel);
            button.setIcon(BadgeIcon.withCount(baseIcon, count, color));
            button.repaint();

            // Pulse si augmentation (nouvelle notif)
            if (count > lastCount) pulse();
            lastCount = count;
        }

        private void pulse() {
            if (pulseTimer != null && pulseTimer.isRunning()) pulseTimer.stop();

            pulseStep = 0;

            // On simule un "pulse" via une bordure translucide + petit scaling sur le bouton
            Insets originalMargin = button.getMargin();
            Dimension originalPref = button.getPreferredSize();

            pulseTimer = new Timer(35, e -> {
                pulseStep++;

                // 0..1..0 (triangle)
                float t = (pulseStep <= 10) ? (pulseStep / 10f) : ((20 - pulseStep) / 10f);
                t = Math.max(0f, Math.min(1f, t));

                // Bordure “glow” (pro)
                int alpha = (int) (40 + 140 * t);
                button.setBorder(BorderFactory.createLineBorder(
                        new Color(231, 76, 60, alpha), 1
                ));
                button.setBorderPainted(true);

                // Micro “pop” (taille)
                int grow = (int) (2 * t);
                button.setMargin(new Insets(
                        originalMargin.top + grow,
                        originalMargin.left + grow,
                        originalMargin.bottom + grow,
                        originalMargin.right + grow
                ));

                if (pulseStep >= 20) {
                    ((Timer) e.getSource()).stop();
                    button.setBorder(null);
                    button.setBorderPainted(false);
                    button.setMargin(originalMargin);
                    button.setPreferredSize(originalPref);
                    button.revalidate();
                    button.repaint();
                }
            });

            pulseTimer.setCoalesce(true);
            pulseTimer.start();
        }
    }

    public static Controller install(JButton btNotif) {
        return new Controller(btNotif);
    }
}
