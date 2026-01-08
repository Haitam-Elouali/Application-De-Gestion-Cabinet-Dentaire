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
        setBackground(Color.WHITE);
        // border-b-2 border-blue-100 px-6 py-3
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, TailwindPalette.ACCENT), // Blue-100
                new EmptyBorder(12, 24, 12, 24)
        ));

        // Logo (Left)
        JLabel logoLabel = new JLabel(); 
        try {
             // Robust loading
             java.io.File logoFile = new java.io.File("c:/Users/Choukhairi/Desktop/Maquette JAVA FINAL/LOGO.jpeg");
             if (logoFile.exists()) {
                 java.awt.image.BufferedImage img = javax.imageio.ImageIO.read(logoFile);
                 if (img != null) {
                     Image scaled = img.getScaledInstance(150, -1, Image.SCALE_SMOOTH);
                     logoLabel.setIcon(new ImageIcon(scaled));
                 } else {
                      logoLabel.setText("TeethCare");
                 }
             } else {
                 logoLabel.setText("TeethCare");
                 logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
                 logoLabel.setForeground(TailwindPalette.PRIMARY);
             }
        } catch (Exception e) {
             e.printStackTrace();
             logoLabel.setText("TeethCare");
             logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
             logoLabel.setForeground(TailwindPalette.PRIMARY);
        }

        add(logoLabel, BorderLayout.WEST);

        // Right Side (User Info + Logout)
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightPanel.setOpaque(false);

        // User Info Container
        JPanel userPanel = new JPanel(new GridLayout(2, 1));
        userPanel.setOpaque(false);
        
        JLabel connectedAs = new JLabel("Connecté en tant que:", SwingConstants.RIGHT);
        connectedAs.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        connectedAs.setForeground(Color.GRAY); // text-gray-500
        
        JLabel userNameLabel = new JLabel(username + " (Médecin)", SwingConstants.RIGHT);
        userNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userNameLabel.setForeground(TailwindPalette.SECONDARY_FOREGROUND); // text-blue-900

        userPanel.add(connectedAs);
        userPanel.add(userNameLabel);
        
        rightPanel.add(userPanel);

        // Logout Button
        ModernButton logoutBtn = new ModernButton("Se déconnecter", ModernButton.Variant.DEFAULT);
        logoutBtn.addActionListener(e -> {
            if (logoutAction != null) logoutAction.run();
        });
        rightPanel.add(logoutBtn);

        add(rightPanel, BorderLayout.EAST);
    }
}
