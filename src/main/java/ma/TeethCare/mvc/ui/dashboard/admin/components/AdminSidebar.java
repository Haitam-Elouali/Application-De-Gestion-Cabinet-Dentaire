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
        setBackground(TailwindPalette.RED_100);
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, new Color(254, 202, 202))); // border-red-200
        setPreferredSize(new Dimension(256, 0)); // w-64

        // Logo
        // Removed as it is now in Header
        add(Box.createVerticalStrut(20), BorderLayout.NORTH);

        // Menu
        menuContainer = new JPanel();
        menuContainer.setLayout(new BoxLayout(menuContainer, BoxLayout.Y_AXIS));
        menuContainer.setOpaque(false);
        
        for (Module m : Module.values()) {
            menuContainer.add(createMenuItem(m));
        }

        add(new JScrollPane(menuContainer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
    }

    private JButton createMenuItem(Module module) {
        JButton btn = new JButton(module.getLabel()) {
            @Override
            protected void paintComponent(Graphics g) {
                if (activeModule == module) {
                    g.setColor(Color.WHITE);
                    g.fillRect(0, 0, getWidth(), getHeight());
                    
                    // Left Red Border
                    g.setColor(TailwindPalette.RED_600);
                    g.fillRect(0, 0, 4, getHeight());
                } else if (getModel().isRollover()) {
                    g.setColor(TailwindPalette.RED_200);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
                
                super.paintComponent(g);
            }
        };

        // Icons
        ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ACTIVITY; 
        switch(module) {
            case USERS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.USERS; break;
            case ROLES: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.SHIELD; break;
            case CATALOG_ACTS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ACTIVITY; break;
            case CATALOG_MEDS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.PILL; break;
            case CATALOG_ANTECEDENTS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.FILE_WARNING; break;
            case INSURANCE: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.BUILDING; break;
            case SECURITY: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.LOCK; break;
        }

        btn.setIcon(ma.TeethCare.mvc.ui.palette.utils.IconUtils.getIcon(iconType, 18, 
            activeModule == module ? TailwindPalette.RED_600 : Color.GRAY));
        btn.setIconTextGap(12);

        btn.setFont(new Font("Segoe UI", activeModule == module ? Font.BOLD : Font.PLAIN, 14));
        btn.setForeground(activeModule == module ? TailwindPalette.RED_600 : TailwindPalette.FOREGROUND);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));
        
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
