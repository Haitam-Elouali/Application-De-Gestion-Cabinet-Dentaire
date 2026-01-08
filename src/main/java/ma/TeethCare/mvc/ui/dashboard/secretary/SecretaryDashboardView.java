package ma.TeethCare.mvc.ui.dashboard.secretary;

import ma.TeethCare.mvc.ui.dashboard.secretary.components.SecretaryHeader;
import ma.TeethCare.mvc.ui.dashboard.secretary.components.SecretaryHome;
import ma.TeethCare.mvc.ui.dashboard.secretary.components.SecretarySidebar;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;
import ma.TeethCare.mvc.ui.palette.utils.IconUtils;

import javax.swing.*;
import java.awt.*;

public class SecretaryDashboardView extends JFrame {

    private JPanel contentArea;
    private SecretarySidebar sidebar; // Declare as field for access in listeners

    public SecretaryDashboardView() {
        setTitle("TeethCare - Secretary Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1366, 768);
        setLocationRelativeTo(null);
        
        initUI();
    }

    private void initUI() {
        Container root = getContentPane();
        root.setLayout(new BorderLayout());

        // Header (Top)
        SecretaryHeader header = new SecretaryHeader("Sophie Martin", () -> System.out.println("Logout clicked"));
        root.add(header, BorderLayout.NORTH);

        // Sidebar (Left)
        sidebar = new SecretarySidebar(this::onModuleSelected);
        root.add(sidebar, BorderLayout.WEST);

        // Content Area (Center)
        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(TailwindPalette.GREEN_50);
        
        // Toggle Area
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 12));
        topBar.setOpaque(false);
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        
        JButton toggleBtn = new JButton("Cacher Menu");
        toggleBtn.setIcon(IconUtils.getIcon(IconUtils.IconType.MENU, 18, TailwindPalette.GREEN_900));
        toggleBtn.setBackground(TailwindPalette.GREEN_100);
        toggleBtn.setForeground(TailwindPalette.GREEN_900);
        toggleBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        toggleBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(TailwindPalette.GREEN_300),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        toggleBtn.setFocusPainted(false);
        toggleBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        toggleBtn.addActionListener(e -> {
            boolean isVisible = sidebar.isVisible();
            sidebar.setVisible(!isVisible);
            toggleBtn.setText(!isVisible ? "Cacher Menu" : "Menu");
        });
        
        topBar.add(toggleBtn);
        contentArea.add(topBar, BorderLayout.NORTH);
        
        // Modules Panel
        JPanel modulePanel = new JPanel(new BorderLayout());
        modulePanel.setOpaque(false);
        modulePanel.setBorder(BorderFactory.createEmptyBorder(10, 24, 24, 24));
        contentArea.add(modulePanel, BorderLayout.CENTER);
        
        // Default
        modulePanel.add(new SecretaryHome(), BorderLayout.CENTER);
        
        root.add(contentArea, BorderLayout.CENTER);
    }
    
    // Explicitly import or qualify internal Enum
    private void onModuleSelected(SecretarySidebar.Module module) {
         if (contentArea.getComponentCount() < 2) return;
         JPanel modulePanel = (JPanel) contentArea.getComponent(1);
         modulePanel.removeAll();
         
        switch (module) {
            case DASHBOARD:
                modulePanel.add(new SecretaryHome(), BorderLayout.CENTER);
                break;
            case PATIENTS:
                modulePanel.add(new ma.TeethCare.mvc.ui.dashboard.doctor.components.PatientManagementView(), BorderLayout.CENTER);
                break;
            case APPOINTMENTS:
                modulePanel.add(new ma.TeethCare.mvc.ui.dashboard.secretary.components.AppointmentView(), BorderLayout.CENTER);
                break;
            case MEDICAL_RECORDS:
                modulePanel.add(new ma.TeethCare.mvc.ui.dashboard.doctor.components.MedicalRecordsView(), BorderLayout.CENTER);
                break;
            case FINANCIAL_SITUATION:
                modulePanel.add(new ma.TeethCare.mvc.ui.dashboard.doctor.components.FinancialSituationView(), BorderLayout.CENTER);
                break;
            case AGENDA:
                modulePanel.add(new ma.TeethCare.mvc.ui.dashboard.secretary.components.AgendaView(), BorderLayout.CENTER);
                break;
            case ANTECEDENTS:
                modulePanel.add(new ma.TeethCare.mvc.ui.dashboard.secretary.components.AntecedentView(), BorderLayout.CENTER);
                break;
            default:
                break;
        }
        
        modulePanel.revalidate();
        modulePanel.repaint();
    }
    
    private JPanel createPlaceholder(String title) {
        JPanel p = new JPanel(new GridBagLayout());
        p.setOpaque(false);
        JLabel l = new JLabel(title);
        l.setFont(new Font("Segoe UI", Font.BOLD, 24));
        l.setForeground(new Color(21, 128, 61)); // Green-700
        p.add(l);
        return p;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        
        SwingUtilities.invokeLater(() -> {
            new SecretaryDashboardView().setVisible(true);
        });
    }
}
