package ma.TeethCare.mvc.ui.dashboard.doctor.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardSidebar extends JPanel {

    public enum Module {
        DASHBOARD("Tableau de Bord"),
        PATIENTS("Patients"),
        MEDICAL_RECORDS("Dossier Médical"),
        CONSULTATION("Consultation"),
        PRESCRIPTIONS("Ordonnances"),
        CERTIFICATES("Certificats"),
        ACTS("Gestion des Actes"),
        BILLING("Caisse"),
        FINANCIAL("Situation Financière");

        private final String label;
        Module(String label) { this.label = label; }
        public String getLabel() { return label; }
    }

    private Module activeModule = Module.DASHBOARD;
    private final java.util.function.Consumer<Module> onModuleSelect;
    private JPanel menuPanel; // Promoted to field

    public DashboardSidebar(java.util.function.Consumer<Module> onModuleSelect) {
        this.onModuleSelect = onModuleSelect;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        setBackground(new Color(239, 246, 255)); // Blue-50 (#EFF6FF)
        setBorder(BorderFactory.createEmptyBorder()); // No border
        setPreferredSize(new Dimension(260, 0));

        // Spacer at top
        add(Box.createVerticalStrut(20), BorderLayout.NORTH);

        // Menu Items
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setOpaque(false);
        
        for (Module m : Module.values()) {
            menuPanel.add(createNavItem(m));
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
                    g2.setColor(new Color(219, 234, 254)); // #DBEAFE (Blue-100)
                    g2.fillRoundRect(12, 0, getWidth() - 24, getHeight(), 12, 12);
                } else if (isRollover) {
                     g2.setColor(new Color(239, 246, 255)); // #EFF6FF (Blue-50) Rollover
                     g2.fillRoundRect(12, 0, getWidth() - 24, getHeight(), 12, 12);
                }
                
                // Text Color
                if (isActive) {
                     setForeground(new Color(37, 99, 235)); // #2563EB (Royal Blue)
                     setFont(new Font("Segoe UI", Font.BOLD, 14));
                } else {
                     setForeground(new Color(75, 85, 99)); // Gray-600
                     setFont(new Font("Segoe UI", Font.PLAIN, 14));
                }
                
                super.paintComponent(g);
            }
        };
        
        // Icon Mapping
        ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ACTIVITY; 
        switch(m) {
            case DASHBOARD: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_DASHBOARD; break;
            case PATIENTS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_PATIENTS; break;
            case MEDICAL_RECORDS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_MEDICAL_RECORDS; break;
            case CONSULTATION: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_CONSULTATION; break;
            case PRESCRIPTIONS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_PRESCRIPTION; break;
            case CERTIFICATES: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_CERTIFICATE; break;
            case ACTS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_ACTS; break;
            case BILLING: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_CASH; break;
            case FINANCIAL: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_FINANCIAL; break;
        }

        Color iconColor = (activeModule == m) ? new Color(37, 99, 235) : new Color(107, 114, 128); // #2563EB | Gray-500
        btn.setIcon(ma.TeethCare.mvc.ui.palette.utils.IconUtils.getIcon(iconType, 20, iconColor)); // 20px Standard
        
        btn.setIconTextGap(16); // 16px Standard
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(12, 20, 12, 20)); // Standardized Padding
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52)); // 52px Height
        
        final ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType finalIconType = iconType;

        btn.addActionListener(e -> {
            activeModule = m;
            onModuleSelect.accept(m);
            updateIcons();
            repaint();
        });

        return btn;
    }
    
    private void updateIcons() {
        for (Component c : menuPanel.getComponents()) {
            if (c instanceof JButton) {
                JButton b = (JButton)c;
                b.repaint(); // Force repaint to update styling (bg/text)
                // Update Icon color logic needs re-binding if we don't store Type in Button.
                // Simple workaround: re-create sidebar? No.
                // Re-bind icon based on label:
                 for (Module m : Module.values()) {
                    if (m.getLabel().equals(b.getText())) {
                        ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType type = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ACTIVITY;
                        switch(m) {
                            case DASHBOARD: type = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_DASHBOARD; break;
                            case PATIENTS: type = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_PATIENTS; break;
                            case MEDICAL_RECORDS: type = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_MEDICAL_RECORDS; break;
                            case CONSULTATION: type = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_CONSULTATION; break;
                            case PRESCRIPTIONS: type = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_PRESCRIPTION; break;
                            case CERTIFICATES: type = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_CERTIFICATE; break;
                            case ACTS: type = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_ACTS; break;
                            case BILLING: type = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_CASH; break;
                            case FINANCIAL: type = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_FINANCIAL; break;
                        }
                        Color color = (activeModule == m) ? new Color(37, 99, 235) : new Color(107, 114, 128); // #2563EB
                        b.setIcon(ma.TeethCare.mvc.ui.palette.utils.IconUtils.getIcon(type, 18, color));
                    }
                }
            }
        }
    }
}
