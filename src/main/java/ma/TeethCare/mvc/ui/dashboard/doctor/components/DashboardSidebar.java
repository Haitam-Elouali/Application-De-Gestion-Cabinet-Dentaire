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
        setBackground(TailwindPalette.BLUE_100);
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, TailwindPalette.BLUE_200));
        setPreferredSize(new Dimension(250, 0));

        // Logo Area
        // Menu Items
        // We removed the logo from here as it is moved to Header.
        // We can add a simple spacer or nothing.
        // Or keep a small brand mark? User asked for header placement.
        // Let's just have a small spacer.
        add(Box.createVerticalStrut(20), BorderLayout.NORTH);

        // Menu Items
        menuPanel = new JPanel(); // Initialized field
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
                
                if (isActive) {
                    g2.setColor(Color.WHITE);
                } else if (getModel().isRollover()) {
                    g2.setColor(TailwindPalette.BLUE_200);
                } else {
                    g2.setColor(TailwindPalette.BLUE_100);
                }
                g2.fillRect(0, 0, getWidth(), getHeight());
                
                if (isActive) {
                    g2.setColor(TailwindPalette.BLUE_600);
                    g2.fillRect(0, 0, 5, getHeight());
                    setForeground(TailwindPalette.BLUE_900);
                    setFont(new Font("Segoe UI", Font.BOLD, 14));
                } else {
                    setForeground(TailwindPalette.FOREGROUND);
                    setFont(new Font("Segoe UI", Font.PLAIN, 14));
                }
                
                g2.setColor(TailwindPalette.BLUE_200);
                g2.fillRect(0, getHeight() - 1, getWidth(), 1);

                super.paintComponent(g);
            }
        };
        
        // precise mapping of icons
        ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ACTIVITY; 
        switch(m) {
            case DASHBOARD: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.HOME; break;
            case PATIENTS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.USERS; break;
            case MEDICAL_RECORDS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.CLIPBOARD; break;
            case CONSULTATION: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.STETHOSCOPE; break;
            case PRESCRIPTIONS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.PILL; break;
            case CERTIFICATES: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.FILE_TEXT; break;
            case ACTS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ACTIVITY; break;
            case BILLING: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.EURO; break;
            case FINANCIAL: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.BUILDING; break;
        }

        btn.setIcon(ma.TeethCare.mvc.ui.palette.utils.IconUtils.getIcon(iconType, 18, 
            activeModule == m ? TailwindPalette.BLUE_600 : Color.GRAY));
        
        btn.setIconTextGap(12);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(16, 24, 16, 24));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        
        final ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType finalIconType = iconType;

        btn.addActionListener(e -> {
            activeModule = m;
            onModuleSelect.accept(m);
            // Update icons colors
             for (Component comp : menuPanel.getComponents()) {
                if (comp instanceof JButton) {
                    ((JButton)comp).repaint();
                     // Re-setting icon is tricky without storing the type.
                     // Simplification: In paintComponent or cleaner logic.
                     // For now, let's just rely on repaint, but the Icon object itself is static. 
                     // The getIcon returns a cached icon. 
                     // I should technically update the icon when active changes to change color.
                     // But re-generating 10 buttons is cheap? or just setIcon again.
                }
            }
            // Update this specific button icon
            btn.setIcon(ma.TeethCare.mvc.ui.palette.utils.IconUtils.getIcon(finalIconType, 18, TailwindPalette.BLUE_600));
            
            // We need to reset others. 
            // It's better to redraw menu items or handle icon painting in paintComponent. 
            // Given the limitations, I'll force a re-init or just leave the icon color static for now (GRAY) and only text changes?
            // User asked for "match screenshots". Usually active icon receives color.
            // I'll stick to GRAY for inactive, Color for active.
            updateIcons();
        });

        return btn;
    }
    
    private void updateIcons() {
        for (Component c : ((JPanel)((JScrollPane)getComponent(1)).getViewport().getView()).getComponents()) {
            if (c instanceof JButton) {
                JButton b = (JButton)c;
                // Reverse lookup or store type? 
                // Quick hack: iterate values and find match by text
                for (Module m : Module.values()) {
                    if (m.getLabel().equals(b.getText())) {
                        ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType type = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ACTIVITY;
                        switch(m) {
                            case DASHBOARD: type = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.HOME; break;
                            case PATIENTS: type = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.USERS; break;
                            case MEDICAL_RECORDS: type = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.CLIPBOARD; break;
                            case CONSULTATION: type = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.STETHOSCOPE; break;
                            case PRESCRIPTIONS: type = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.PILL; break;
                            case CERTIFICATES: type = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.FILE_TEXT; break;
                            case ACTS: type = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ACTIVITY; break;
                            case BILLING: type = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.EURO; break;
                            case FINANCIAL: type = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.BUILDING; break;
                        }
                        b.setIcon(ma.TeethCare.mvc.ui.palette.utils.IconUtils.getIcon(type, 18, 
                            activeModule == m ? TailwindPalette.BLUE_600 : Color.GRAY));
                    }
                }
            }
        }
    }
}
