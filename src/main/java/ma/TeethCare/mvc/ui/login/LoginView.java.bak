package ma.TeethCare.mvc.ui.login;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;
import ma.TeethCare.mvc.ui.palette.containers.ModernCard;
import ma.TeethCare.mvc.ui.palette.fields.ModernTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class LoginView extends JFrame {

    public LoginView() {
        setTitle("TeethCare - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1366, 768);
        setLocationRelativeTo(null);
        
        initUI();
    }

    private void initUI() {
        // Main Container with Background
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(TailwindPalette.BLUE_50); // Light blue background
        setContentPane(mainPanel);

        // Custom Rounded Panel for the card effect
        JPanel loginCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                
                // Shadow
                g2.setColor(new Color(0,0,0, 20)); // Soft shadow
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 16, 16);
            }
        };
        loginCard.setLayout(new BoxLayout(loginCard, BoxLayout.Y_AXIS));
        loginCard.setBorder(new EmptyBorder(40, 48, 48, 48)); // Increased Padding
        loginCard.setPreferredSize(new Dimension(420, 580)); // Slightly taller/wider
        loginCard.setOpaque(false);

        // Logo
        JLabel logoLabel = new JLabel();
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        try {
             java.io.File logoFile = new java.io.File("c:/Users/Choukhairi/Desktop/Maquette JAVA FINAL/LOGO.jpeg");
             if (logoFile.exists()) {
                 java.awt.image.BufferedImage img = javax.imageio.ImageIO.read(logoFile);
                 if (img != null) {
                     Image scaled = img.getScaledInstance(180, -1, Image.SCALE_SMOOTH); 
                     logoLabel.setIcon(new ImageIcon(scaled));
                 }
             } else {
                 logoLabel.setText("TeethCare");
                 logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
                 logoLabel.setForeground(TailwindPalette.BLUE_600);
             }
        } catch (Exception e) {
             logoLabel.setText("TeethCare");
        }
        
        // Welcome Text
        JLabel welcomeLabel = new JLabel("Bienvenue");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 26)); // Larger
        welcomeLabel.setForeground(TailwindPalette.FOREGROUND);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subLabel = new JLabel("Connectez-vous à votre compte");
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subLabel.setForeground(TailwindPalette.MUTED_FOREGROUND);
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Inputs Container
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setOpaque(false);
        fieldsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Email Field
        JLabel l1 = new JLabel("Email");
        l1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        l1.setForeground(TailwindPalette.FOREGROUND);
        l1.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        ModernTextField userField = new ModernTextField("Email ou Nom d'utilisateur");
        userField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44)); // Taller field
        userField.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        fieldsPanel.add(l1);
        fieldsPanel.add(Box.createVerticalStrut(8));
        fieldsPanel.add(userField);
        fieldsPanel.add(Box.createVerticalStrut(20));
        
        // Password Field
        JLabel l2 = new JLabel("Mot de passe");
        l2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        l2.setForeground(TailwindPalette.FOREGROUND);
        l2.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPasswordField passField = new JPasswordField(); 
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(TailwindPalette.BORDER),
            BorderFactory.createEmptyBorder(10, 14, 10, 14) // More padding
        ));
        passField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        passField.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        fieldsPanel.add(l2);
        fieldsPanel.add(Box.createVerticalStrut(8));
        fieldsPanel.add(passField);
        
        // Button
        ModernButton loginBtn = new ModernButton("Se Connecter", ModernButton.Variant.DEFAULT);
        loginBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44)); // Taller button
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        
        loginBtn.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());
            
            dispose(); // Close login
            
             if (user.contains("secretaire")) {
                 JOptionPane.showMessageDialog(LoginView.this, "Login Successful: Redirecting to Secretary Dashboard (Mock)");
             } else if (user.contains("admin")) {
                 JOptionPane.showMessageDialog(LoginView.this, "Login Successful: Redirecting to Admin Dashboard (Mock)");
             } else {
                 JOptionPane.showMessageDialog(LoginView.this, "Login Successful: Redirecting to Doctor Dashboard (Mock)");
             }
        });
        
        // Forgot Password
        JLabel forgotLabel = new JLabel("Mot de passe oublié?");
        forgotLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        forgotLabel.setForeground(TailwindPalette.BLUE_600);
        forgotLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgotLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        // Add to card
        loginCard.add(Box.createVerticalStrut(10));
        loginCard.add(logoLabel);
        loginCard.add(Box.createVerticalStrut(24));
        loginCard.add(welcomeLabel);
        loginCard.add(Box.createVerticalStrut(6));
        loginCard.add(subLabel);
        loginCard.add(Box.createVerticalStrut(36));
        loginCard.add(fieldsPanel);
        loginCard.add(Box.createVerticalStrut(32));
        loginCard.add(loginBtn);
        loginCard.add(Box.createVerticalStrut(20));
        loginCard.add(forgotLabel);
        loginCard.add(Box.createVerticalGlue()); // Push content
        
        // Add card to main
        mainPanel.add(loginCard);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
