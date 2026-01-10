package ma.TeethCare.mvc.ui.dashboard.admin.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdminHeader extends JPanel {

    private final String userName;
    private final Runnable logoutAction;

    public AdminHeader(String userName, Runnable logoutAction) {
        this.userName = userName;
        this.logoutAction = logoutAction;
        
        setLayout(new BorderLayout());
        setBackground(TailwindPalette.ROSE_50); // Admin Theme
        setBorder(new EmptyBorder(0, 0, 10, 0)); // Standardized spacing
        setPreferredSize(new Dimension(100, 80)); // Standardized Height 80px

        initUI();
    }

    private void initUI() {
        // Left: Logo
        JLabel logoLabel = new JLabel(); 
        // Use IconUtils for Logo (Width 180, Auto Height) - Standardized Size
        Icon logoIcon = ma.TeethCare.mvc.ui.palette.utils.IconUtils.getIcon(
                ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_LOGO, 180, -1, null);
        logoLabel.setIcon(logoIcon);
        
        logoLabel.setBorder(new EmptyBorder(0, 24, 0, 0)); 
        logoLabel.setVerticalAlignment(SwingConstants.CENTER);
        add(logoLabel, BorderLayout.WEST);

        // Right: User Info + Logout
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 20)); // Align vertically better (standard 20 gap)
        rightPanel.setOpaque(false);
        rightPanel.setBorder(new EmptyBorder(0, 0, 0, 24)); // px-6

        // User Info Container
        JPanel userInfo = new JPanel(new GridLayout(2, 1));
        userInfo.setOpaque(false);
        
        JLabel connectedLabel = new JLabel("Connecté en tant que:", SwingConstants.RIGHT);
        connectedLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        connectedLabel.setForeground(Color.GRAY);
        
        JLabel userLabel = new JLabel(userName + " (ADMIN)", SwingConstants.RIGHT);
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(TailwindPalette.RED_900); // text-red-900

        userInfo.add(connectedLabel);
        userInfo.add(userLabel);
        
        rightPanel.add(userInfo);

        // Logout Button - Standardized to ModernButton
        ModernButton logoutBtn = new ModernButton("Se déconnecter", ModernButton.Variant.DESTRUCTIVE); // Red
        logoutBtn.addActionListener(e -> {
            if (logoutAction != null) logoutAction.run();
        });
        rightPanel.add(logoutBtn);

        add(rightPanel, BorderLayout.EAST);
    }
}
