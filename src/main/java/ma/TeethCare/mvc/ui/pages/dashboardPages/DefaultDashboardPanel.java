package ma.TeethCare.mvc.ui.pages.dashboardPages;

import ma.TeethCare.service.modules.auth.dto.UserPrincipal;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DefaultDashboardPanel extends JPanel {
    public DefaultDashboardPanel(UserPrincipal principal) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(22, 22, 22, 22));

        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("Optima", Font.BOLD, 28));
        add(title, BorderLayout.NORTH);

        JLabel msg = new JLabel("Aucun dashboard spécifique pour ce rôle.");
        msg.setFont(new Font("Optima", Font.PLAIN, 16));
        add(msg, BorderLayout.CENTER);
    }
}
