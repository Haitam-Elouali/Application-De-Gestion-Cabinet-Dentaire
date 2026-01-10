package ma.TeethCare.mvc.ui.dashboard.doctor.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardHeader extends JPanel {

    private final String username;
    private final Runnable logoutAction;

    public DashboardHeader(String username, Runnable logoutAction) {
        this.username = username;
        this.logoutAction = logoutAction;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(TailwindPalette.BLUE_50); // Doctor Theme
        // Adjust border to match style
        setBorder(new EmptyBorder(0, 0, 10, 0));
        setPreferredSize(new Dimension(100, 80));

        // Logo (Left)
        JLabel logoLabel = new JLabel(); 
        // Standardized Size 180
        Icon logoIcon = ma.TeethCare.mvc.ui.palette.utils.IconUtils.getIcon(
                ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_LOGO, 180, -1, null);
        logoLabel.setIcon(logoIcon);
        logoLabel.setBorder(new EmptyBorder(0, 24, 0, 0));
        logoLabel.setVerticalAlignment(SwingConstants.CENTER);
        add(logoLabel, BorderLayout.WEST);

        // Right Side (User Info + Logout)
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 20));
        rightPanel.setOpaque(false);
        rightPanel.setBorder(new EmptyBorder(0, 0, 0, 24));

        // User Info Container
        JPanel userPanel = new JPanel(new GridLayout(2, 1));
        userPanel.setOpaque(false);
        
        JLabel connectedAs = new JLabel("Connecté en tant que:", SwingConstants.RIGHT);
        connectedAs.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        connectedAs.setForeground(Color.GRAY);
        
        JLabel userNameLabel = new JLabel(username + " (Médecin)", SwingConstants.RIGHT);
        userNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userNameLabel.setForeground(new Color(30, 58, 138)); // Blue-900

        userPanel.add(connectedAs);
        userPanel.add(userNameLabel);
        
        rightPanel.add(userPanel);

        // Logout Button - Standardized
        ModernButton logoutBtn = new ModernButton("Se déconnecter", ModernButton.Variant.DEFAULT); // Default Blue
        logoutBtn.addActionListener(e -> {
            if (logoutAction != null) logoutAction.run();
        });
        
        rightPanel.add(logoutBtn);

        add(rightPanel, BorderLayout.EAST);
    }
}
