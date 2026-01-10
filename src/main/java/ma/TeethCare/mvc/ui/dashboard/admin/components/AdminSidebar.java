package ma.TeethCare.mvc.ui.dashboard.admin.components;

import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class AdminSidebar extends JPanel {

    public enum Module {
        USERS("Gestion Utilisateurs"),
        ROLES("Gestion Rôles"),
        CATALOG_ACTS("Actes Médicaux"),
        CATALOG_MEDS("Médicaments"),
        CATALOG_ANTECEDENTS("Antécédents"),
        INSURANCE("Assurances"),
        SECURITY("Sécurité & Sauvegardes");

        private final String label;
        Module(String label) {
            this.label = label;
        }
        public String getLabel() { return label; }
    }

    private final Consumer<Module> onSelection;
    private Module activeModule = Module.USERS;
    private JPanel menuContainer;

    public AdminSidebar(Consumer<Module> onSelection) {
        this.onSelection = onSelection;
        initMenu();
    }

    private void initMenu() {
        setLayout(new BorderLayout());
        setBackground(TailwindPalette.ROSE_50); // #FFF1F2 (Pale Pink)
        setBorder(null); // No border or minimal
        setPreferredSize(new Dimension(260, 0));

        // Logo Space (header handles logo)
        add(Box.createVerticalStrut(20), BorderLayout.NORTH);

        // Menu
        menuContainer = new JPanel();
        menuContainer.setLayout(new BoxLayout(menuContainer, BoxLayout.Y_AXIS));
        menuContainer.setOpaque(false);
        
        for (Module m : Module.values()) {
            menuContainer.add(createMenuItem(m));
        }

        JScrollPane sp = new JScrollPane(menuContainer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setBorder(null);
        sp.setOpaque(false);
        sp.getViewport().setOpaque(false);
        add(sp, BorderLayout.CENTER);
    }

    private JButton createMenuItem(Module module) {
        JButton btn = new JButton(module.getLabel()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (activeModule == module) {
                    // Active: Rose-100 BG with Rose-600 text
                    g2.setColor(TailwindPalette.ROSE_100); 
                    g2.fillRoundRect(12, 0, getWidth()-24, getHeight(), 12, 12);
                } else if (getModel().isRollover()) {
                    // Rollover: Rose-50 or slightly darker
                    g2.setColor(new Color(255, 228, 230, 100)); // Subtle Rose tint
                    g2.fillRoundRect(12, 0, getWidth()-24, getHeight(), 12, 12);
                }
                
                super.paintComponent(g);
            }
        };

        // Icons
        // Icons - specific mappings
        // Icons - specific mappings
        ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ACTIVITY; 
        switch(module) {
            case USERS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_USERS_ADMIN; break; // utilisateur_png.png
            case ROLES: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_ROLES; break; // role_img.png
            case CATALOG_ACTS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_ACTS; break; // actes.png
            case CATALOG_MEDS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_PRESCRIPTION; break; // medicament_img.png
            case CATALOG_ANTECEDENTS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_ANTECEDENTS; break; // antecedent_img.png
            case INSURANCE: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_INSURANCE; break; // assurance.png
            case SECURITY: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_SECURITY; break; // security_img.png
        }

        btn.setIcon(ma.TeethCare.mvc.ui.palette.utils.IconUtils.getIcon(iconType, 20, 
            activeModule == module ? TailwindPalette.ROSE_600 : TailwindPalette.GRAY_500));
        btn.setIconTextGap(16); // Standard 16

        btn.setFont(new Font("Segoe UI", activeModule == module ? Font.BOLD : Font.PLAIN, 14));
        btn.setForeground(activeModule == module ? TailwindPalette.ROSE_600 : TailwindPalette.GRAY_600);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20)); // Standard 12,20
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52)); // Standard 52
        
        btn.addActionListener(e -> {
            activeModule = module;
            onSelection.accept(module);
            updateMenuVisuals();
        });

        return btn;
    }

    private void updateMenuVisuals() {
        menuContainer.removeAll();
        for (Module m : Module.values()) {
            menuContainer.add(createMenuItem(m));
        }
        menuContainer.revalidate();
        menuContainer.repaint();
    }
}
