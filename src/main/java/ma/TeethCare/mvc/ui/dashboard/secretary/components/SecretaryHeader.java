package ma.TeethCare.mvc.ui.dashboard.secretary.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
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
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, TailwindPalette.BORDER)); // border-b-2
        setPreferredSize(new Dimension(100, 72)); // px-6 py-3 (approx 64-80px height)

        initUI();
    }

    private void initUI() {
        // Left: Logo
        JLabel logoLabel = new JLabel(); 
        try {
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
                 logoLabel.setForeground(TailwindPalette.GREEN_600); 
             }
        } catch (Exception e) {
             logoLabel.setText("TeethCare");
             logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
             logoLabel.setForeground(TailwindPalette.GREEN_600); 
        }
        logoLabel.setBorder(new EmptyBorder(0, 24, 0, 0)); 
        add(logoLabel, BorderLayout.WEST);

        // Right: User Info + Logout
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 12));
        rightPanel.setOpaque(false);
        rightPanel.setBorder(new EmptyBorder(0, 0, 0, 24)); // px-6

        // User Info Container
        JPanel userInfo = new JPanel(new GridLayout(2, 1));
        userInfo.setOpaque(false);
        
        JLabel connectedLabel = new JLabel("Connecté en tant que:", SwingConstants.RIGHT);
        connectedLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        connectedLabel.setForeground(Color.GRAY);
        
        JLabel userLabel = new JLabel(userName + " (Secrétaire)", SwingConstants.RIGHT);
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(Color.BLACK); // text-gray-900

        userInfo.add(connectedLabel);
        userInfo.add(userLabel);
        
        rightPanel.add(userInfo);

        // Logout Button
        ModernButton logoutBtn = new ModernButton("SE DÉCONNECTER", ModernButton.Variant.SUCCESS); // Using SUCCESS for Green button
        logoutBtn.addActionListener(e -> {
            if (logoutAction != null) logoutAction.run();
        });
        rightPanel.add(logoutBtn);

        add(rightPanel, BorderLayout.EAST);
    }
}
