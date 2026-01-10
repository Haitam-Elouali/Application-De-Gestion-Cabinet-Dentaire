package ma.TeethCare.mvc.ui.pages.dashboardPages;

import ma.TeethCare.service.modules.auth.dto.UserPrincipal;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdminDashboardPanel extends JPanel {

    public AdminDashboardPanel(UserPrincipal principal) {
        setLayout(new BorderLayout(16, 16));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(22, 22, 22, 22));

        JLabel title = new JLabel("Dashboard — Administrateur");
        title.setFont(new Font("Optima", Font.BOLD, 28));

        // Exemple: KPIs + raccourcis
        JPanel cards = new JPanel(new GridLayout(1, 3, 14, 14));
        cards.setOpaque(false);

        cards.add(kpiCard("Utilisateurs", "Gestion des comptes"));
        cards.add(kpiCard("Cabinets", "Paramétrage & staff"));
        cards.add(kpiCard("Audit", "Logs & sécurité"));

        add(title, BorderLayout.NORTH);
        add(cards, BorderLayout.CENTER);
    }

    private JComponent kpiCard(String head, String sub) {
        JPanel p = new JPanel(new GridLayout(0, 1, 0, 6));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                new EmptyBorder(16, 16, 16, 16)));

        JLabel h = new JLabel(head);
        h.setFont(new Font("Optima", Font.BOLD, 18));

        JLabel s = new JLabel(sub);
        s.setFont(new Font("Optima", Font.PLAIN, 14));
        s.setForeground(new Color(90, 90, 90));

        p.add(h);
        p.add(s);
        return p;
    }
}
