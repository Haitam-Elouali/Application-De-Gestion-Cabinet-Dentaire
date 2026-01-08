package ma.TeethCare.mvc.ui.dashboard.secretary.components;

import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.MatteBorder;
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
    private JPanel menuPanel; // Renamed from menuContainer

    public SecretarySidebar(java.util.function.Consumer<Module> onModuleSelect) {
        this.onModuleSelect = onModuleSelect;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        setBackground(TailwindPalette.GREEN_100);
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, TailwindPalette.GREEN_200));
        setPreferredSize(new Dimension(250, 0)); // w-64

        // Logo
        // Removed as it is now in Header
        add(Box.createVerticalStrut(20), BorderLayout.NORTH);

        // Menu container
        menuPanel = new JPanel(); // Initialized here
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
                    g2.setColor(TailwindPalette.GREEN_200);
                } else {
                    g2.setColor(TailwindPalette.GREEN_100);
                }
                g2.fillRect(0, 0, getWidth(), getHeight());
                
                if (isActive) {
                    g2.setColor(TailwindPalette.GREEN_600);
                    g2.fillRect(0, 0, 5, getHeight());
                    setForeground(TailwindPalette.GREEN_800);
                    setFont(new Font("Segoe UI", Font.BOLD, 14));
                } else {
                    setForeground(new Color(55, 65, 81)); // gray-700
                    setFont(new Font("Segoe UI", Font.PLAIN, 14));
                }
                
                g2.setColor(TailwindPalette.GREEN_200);
                g2.fillRect(0, getHeight() - 1, getWidth(), 1);

                super.paintComponent(g);
            }
        };

        ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ACTIVITY; 
        switch(m) {
            case DASHBOARD: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.HOME; break;
            case APPOINTMENTS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.CALENDAR; break;
            case PATIENTS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.USERS; break;
            case MEDICAL_RECORDS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.CLIPBOARD; break;
            case AGENDA: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.CALENDAR; break;
            case ANTECEDENTS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.FILE_WARNING; break;
            case FINANCIAL_SITUATION: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.BUILDING; break;
        }

        btn.setIcon(ma.TeethCare.mvc.ui.palette.utils.IconUtils.getIcon(iconType, 18, 
            activeModule == m ? TailwindPalette.GREEN_600 : Color.GRAY));
        
        btn.setIconTextGap(12);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(16, 24, 16, 24));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        
        btn.addActionListener(e -> {
            activeModule = m;
            onModuleSelect.accept(m);
            updateIcons(menuPanel);
        });

        return btn;
    }
    
    // Helper to refresh icons on click
    private void updateIcons(JPanel panel) {
        for (Component c : panel.getComponents()) {
            if (c instanceof JButton) {
                JButton b = (JButton)c;
                // Find module by label
                for (Module m : Module.values()) {
                    if (m.getLabel().equals(b.getText())) {
                        ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ACTIVITY; 
                        switch(m) {
                            case DASHBOARD: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.HOME; break;
                            case APPOINTMENTS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.CALENDAR; break;
                            case PATIENTS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.USERS; break;
                            case MEDICAL_RECORDS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.CLIPBOARD; break;
                            case AGENDA: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.CALENDAR; break;
                            case ANTECEDENTS: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.FILE_WARNING; break;
                            case FINANCIAL_SITUATION: iconType = ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.BUILDING; break;
                            default: break;
                        }
                        
                        b.setIcon(ma.TeethCare.mvc.ui.palette.utils.IconUtils.getIcon(iconType, 18, 
                            activeModule == m ? TailwindPalette.GREEN_600 : Color.GRAY));
                        b.repaint();
                    }
                }
            }
        }
    }
}
