package ma.TeethCare.mvc.ui.pages.dashboardPages;

import ma.TeethCare.service.modules.auth.dto.UserPrincipal;
import ma.TeethCare.mvc.ui.palette.cards.StatCard;
import ma.TeethCare.mvc.ui.palette.utils.UIConstants;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SecretaryDashboardPanel extends JPanel {

        public SecretaryDashboardPanel(UserPrincipal principal) {
                setLayout(new BorderLayout(0, 24));
                setBackground(UIConstants.SURFACE_MAIN);
                setBorder(new EmptyBorder(32, 40, 32, 40));

                // Header Title
                JLabel title = new JLabel(
                                "Bienvenue, " + (principal.username() != null ? principal.username() : "Secrétaire"));
                title.setFont(UIConstants.FONT_TITLE.deriveFont(32f));
                title.setForeground(UIConstants.TEXT_DARK);
                add(title, BorderLayout.NORTH);

                // Stats Grid
                JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
                statsPanel.setOpaque(false);

                statsPanel.add(new StatCard(
                                "Patients", "1,284", "+12% ce mois",
                                new ImageIcon(), UIConstants.SURFACE_GREEN, UIConstants.ACCENT_GREEN));

                statsPanel.add(new StatCard(
                                "Rendez-vous", "42", "Aujourd'hui",
                                new ImageIcon(), new Color(239, 246, 255), new Color(29, 78, 216) // Blue variant
                ));

                statsPanel.add(new StatCard(
                                "CA Mensuel", "12,500 DH", "+5% vs mois dernier",
                                new ImageIcon(), new Color(255, 251, 235), new Color(180, 83, 9) // Amber variant
                ));

                // Main Content Area (Placeholder for upcoming appointments/list)
                JPanel mainContent = new JPanel(new BorderLayout());
                mainContent.setOpaque(false);
                mainContent.add(statsPanel, BorderLayout.NORTH);

                // You could add a specialized JTable or list here for "Upcoming Appointments"

                add(mainContent, BorderLayout.CENTER);
        }
}
