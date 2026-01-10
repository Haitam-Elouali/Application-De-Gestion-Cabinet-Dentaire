package ma.TeethCare.mvc.ui.dashboard.secretary.components;

import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.Consumer;

public class SecretarySidebar extends JPanel {

    public enum Module {
        DASHBOARD("Tableau de Bord"),
        PATIENTS("Gestion Patients"),
        APPOINTMENTS("Rendez-vous"),
        MEDICAL_RECORDS("Dossiers Médicaux"),
        CASH_MANAGEMENT("Gestion Caisse"),
        AGENDA("Agenda Médecin"),
        ANTECEDENTS("Antécédents"),
        FINANCIAL_SITUATION("Situation Financière");

        private final String label;
        Module(String label) {
            this.label = label;
        }
        public String getLabel() { return label; }
    }

    private final Consumer<Module> onModuleSelect;
    private Module activeModule = Module.DASHBOARD;
    private JPanel menuPanel;

    public SecretarySidebar(java.util.function.Consumer<Module> onModuleSelect) {
        this.onModuleSelect = onModuleSelect;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#F0FDF4")); // Mint Green Background
        setPreferredSize(new Dimension(260, 0));

        // 1. Logo Section REMOVED (Moved to Header)
        // Just add some top padding
        JPanel topSpacer = new JPanel();
        topSpacer.setOpaque(false);
        topSpacer.setPreferredSize(new Dimension(10, 20));
        add(topSpacer, BorderLayout.NORTH);

        // Menu container
        menuPanel = new JPanel(); 
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setOpaque(false);
        menuPanel.setBorder(new EmptyBorder(20, 16, 20, 16));
        
        for (Module m : Module.values()) {
            menuPanel.add(createNavItem(m));
            // Removed Spacer to match other Sidebars
        }

        JScrollPane scroll = new JScrollPane(menuPanel);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        add(scroll, BorderLayout.CENTER);
    }

    private JButton createNavItem(Module m) {
        JButton btn = new JButton(m.getLabel()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                boolean isActive = (activeModule == m);
                boolean isRollover = getModel().isRollover();
                
                if (isActive) {
                    // Green-200 for better visibility (#BBF7D0)
                    g2.setColor(new Color(187, 247, 208)); 
                    // Match Doctor Sidebar Geometry: floating pill
                    g2.fillRoundRect(12, 0, getWidth() - 24, getHeight(), 12, 12);
                    
                    setForeground(new Color(20, 83, 45)); // Green-900 or similar dark
                    setFont(new Font("Segoe UI", Font.BOLD, 14));
                } else {
                    if (isRollover) {
                         // Green-100 (#DCFCE7)
                         g2.setColor(new Color(220, 252, 231));
                         g2.fillRoundRect(12, 0, getWidth() - 24, getHeight(), 12, 12);
                    }
                    setForeground(new Color(75, 85, 99)); // Gray-600
                    setFont(new Font("Segoe UI", Font.PLAIN, 14));
                }
                
                super.paintComponent(g);
            }
        };

        // Update Icons to use new TYPES
        ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_DASHBOARD; 
        switch(m) {
            case DASHBOARD: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_DASHBOARD; break;
            case APPOINTMENTS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_RDV; break;
            case PATIENTS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_PATIENTS; break;
            case MEDICAL_RECORDS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_MEDICAL_RECORDS; break;
            case CASH_MANAGEMENT: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_CASH; break; // Add to switch
            case AGENDA: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_AGENDA; break;
            case ANTECEDENTS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_ANTECEDENTS; break;
            case FINANCIAL_SITUATION: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_FINANCIAL; break;
        }

        // Color might matter less if loading images, but keep logic
        Color iconColor = (activeModule == m) ? new Color(5, 150, 105) : new Color(107, 114, 128);
        btn.setIcon(ma.TeethCare.mvc.ui.palette.utils.IconUtils.getIcon(iconType, 20, iconColor)); // 20px Standard
        
        btn.setIconTextGap(16);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(12, 20, 12, 20)); // Standardized
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52)); // 52px Height
        
        btn.addActionListener(e -> {
            activeModule = m;
            onModuleSelect.accept(m);
            updateIcons(menuPanel);
            menuPanel.repaint();
        });

        return btn;
    }
    
    private void updateIcons(JPanel panel) {
        for (Component c : panel.getComponents()) {
            if (c instanceof JButton) {
                JButton b = (JButton)c;
                for (Module m : Module.values()) {
                    if (m.getLabel().equals(b.getText())) {
                        ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_DASHBOARD; 
                         switch(m) {
                            case DASHBOARD: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_DASHBOARD; break;
                            case APPOINTMENTS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_RDV; break;
                            case PATIENTS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_PATIENTS; break;
                            case MEDICAL_RECORDS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_MEDICAL_RECORDS; break;
                            case CASH_MANAGEMENT: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_CASH; break; // Add
                            case AGENDA: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_AGENDA; break;
                            case ANTECEDENTS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_ANTECEDENTS; break;
                            case FINANCIAL_SITUATION: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_FINANCIAL; break;
                        }
                        
                        Color iconColor = (activeModule == m) ? new Color(5, 150, 105) : new Color(107, 114, 128);
                        b.setIcon(ma.TeethCare.mvc.ui.palette.utils.IconUtils.getIcon(iconType, 24, iconColor));
                    }
                }
            }
        }
    }
}
