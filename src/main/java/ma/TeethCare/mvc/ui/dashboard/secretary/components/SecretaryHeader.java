package ma.TeethCare.mvc.ui.dashboard.secretary.components;

import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SecretaryHeader extends JPanel {

    private final String userName;
    private final Runnable logoutAction;

    public SecretaryHeader(String userName, Runnable logoutAction) {
        this.userName = userName;
        this.logoutAction = logoutAction;
        
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(TailwindPalette.GREEN_50); // Secretary Theme
        setBorder(new EmptyBorder(0, 0, 10, 0)); // No bottom border, just spacing if needed
        setPreferredSize(new Dimension(100, 80)); // Taller header

        initUI();
    }

    private void initUI() {
        // Left: Logo
        JLabel logoLabel = new JLabel();
        // Standardized Size 180
        Icon logoIcon = ma.TeethCare.mvc.ui.palette.utils.IconUtils.getIcon(
                ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_LOGO, 180, -1, null);
        logoLabel.setIcon(logoIcon);
        logoLabel.setBorder(new EmptyBorder(0, 24, 0, 0)); 
        logoLabel.setVerticalAlignment(SwingConstants.CENTER); // Ensure vertical center
        add(logoLabel, BorderLayout.WEST);

        // Right: User Info + Logout
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 20)); // Align vertically better
        rightPanel.setOpaque(false);
        rightPanel.setBorder(new EmptyBorder(0, 0, 0, 24));

        // User Info Container
        JPanel userInfo = new JPanel(new GridLayout(2, 1));
        userInfo.setOpaque(false);
        
        JLabel connectedLabel = new JLabel("Connecté en tant que:", SwingConstants.RIGHT);
        connectedLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        connectedLabel.setForeground(Color.GRAY);
        
        JLabel userLabel = new JLabel(userName + " (Secrétaire)", SwingConstants.RIGHT);
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(new Color(31, 41, 55)); // Gray 800

        userInfo.add(connectedLabel);
        userInfo.add(userLabel);
        
        rightPanel.add(userInfo);

        // Logout Button - Standardized to ModernButton
        ma.TeethCare.mvc.ui.palette.buttons.ModernButton logoutBtn = new ma.TeethCare.mvc.ui.palette.buttons.ModernButton("Se déconnecter", ma.TeethCare.mvc.ui.palette.buttons.ModernButton.Variant.SUCCESS);
        logoutBtn.addActionListener(e -> {
            if (logoutAction != null) logoutAction.run();
        });
        
        rightPanel.add(logoutBtn);

        add(rightPanel, BorderLayout.EAST);
    }
}
