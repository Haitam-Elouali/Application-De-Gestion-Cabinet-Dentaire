package ma.TeethCare.mvc.ui.pages.otherPages.common;

import ma.TeethCare.service.modules.auth.dto.UserPrincipal;
import ma.TeethCare.mvc.ui.palette.utils.UIConstants;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BasePlaceholderPanel extends JPanel {

    public BasePlaceholderPanel(String title, UserPrincipal principal) {
        setLayout(new BorderLayout(0, 20));
        setBackground(UIConstants.SURFACE_MAIN);
        setBorder(new EmptyBorder(32, 40, 32, 40));

        // Header Section
        JPanel head = new JPanel();
        head.setOpaque(false);
        head.setLayout(new BoxLayout(head, BoxLayout.Y_AXIS));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(UIConstants.FONT_TITLE.deriveFont(28f));
        lblTitle.setForeground(UIConstants.TEXT_DARK);
        head.add(lblTitle);

        if (principal != null) {
            String details = String.format("Session: %s • Rôle: %s",
                    safe(principal.username()),
                    (principal.roles() != null && !principal.roles().isEmpty()) ? principal.roles().get(0) : "—");
            JLabel lblUser = new JLabel(details);
            lblUser.setFont(UIConstants.FONT_REGULAR.deriveFont(14f));
            lblUser.setForeground(UIConstants.TEXT_GRAY);
            head.add(Box.createVerticalStrut(5));
            head.add(lblUser);
        }

        // Content Card (Mock)
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIConstants.BORDER_GRAY, 1),
                new EmptyBorder(40, 40, 40, 40)));

        JTextArea info = new JTextArea(
                "Ce module est prêt à être implémenté.\n\n" +
                        "Vous pouvez maintenant commencer à ajouter les fonctionnalités spécifiques à " + title +
                        " (Tableaux, formulaires de recherche, filtres avancés, etc.) en suivant le nouveau design system.");
        info.setEditable(false);
        info.setLineWrap(true);
        info.setWrapStyleWord(true);
        info.setFont(UIConstants.FONT_REGULAR.deriveFont(15f));
        info.setForeground(UIConstants.TEXT_GRAY);
        info.setBackground(Color.WHITE);

        card.add(info, BorderLayout.CENTER);

        add(head, BorderLayout.NORTH);
        add(card, BorderLayout.CENTER);
    }

    private String safe(String s) {
        return s == null ? "—" : s;
    }
}
