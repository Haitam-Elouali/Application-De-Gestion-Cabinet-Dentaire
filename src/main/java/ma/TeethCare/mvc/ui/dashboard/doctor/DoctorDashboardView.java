package ma.TeethCare.mvc.ui.dashboard.doctor;

import ma.TeethCare.mvc.ui.dashboard.doctor.components.DashboardHeader;
import ma.TeethCare.mvc.ui.dashboard.doctor.components.DashboardHome;
import ma.TeethCare.mvc.ui.dashboard.doctor.components.DashboardSidebar;

import javax.swing.*;
import java.awt.*;

public class DoctorDashboardView extends JFrame {

    private JPanel contentArea;

    public DoctorDashboardView() {
        this("Dr. Dentiste");
    }

    public DoctorDashboardView(String username) {
        setTitle("TeethCare - Doctor Dashboard - Connecté: " + username);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Or DISPOSE based on app logic
        setSize(1366, 768); // HD resolution
        setLocationRelativeTo(null);
        
        initUI(username);
    }

    private void initUI(String username) {
        Container root = getContentPane();
        root.setLayout(new BorderLayout());

        // Header (Top)
        DashboardHeader header = new DashboardHeader(username, () -> {
            new ma.TeethCare.mvc.ui.login.LoginView().setVisible(true);
            dispose();
        });
        root.add(header, BorderLayout.NORTH);

        // Sidebar (Left)
        DashboardSidebar sidebar = new DashboardSidebar(this::onModuleSelected);
        root.add(sidebar, BorderLayout.WEST);

        // Content Area (Center)
        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(Color.WHITE); // Switched to White per user request
        
        // Toggle Button Area
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 12));
        topBar.setOpaque(false);
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10)); // mb-6 approx
        
        JButton toggleBtn = new JButton("Cacher");
        toggleBtn.setIcon(ma.TeethCare.mvc.ui.palette.utils.IconUtils.getIcon(ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_HIDE, 18, ma.TeethCare.mvc.ui.palette.utils.TailwindPalette.BLUE_900));
        toggleBtn.setBackground(ma.TeethCare.mvc.ui.palette.utils.TailwindPalette.BLUE_100);
        toggleBtn.setForeground(ma.TeethCare.mvc.ui.palette.utils.TailwindPalette.BLUE_900);
        toggleBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        toggleBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ma.TeethCare.mvc.ui.palette.utils.TailwindPalette.BLUE_200),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        toggleBtn.setFocusPainted(false);
        toggleBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        toggleBtn.addActionListener(e -> {
            boolean isVisible = sidebar.isVisible();
            sidebar.setVisible(!isVisible);
            if (!isVisible) {
                toggleBtn.setText("Cacher");
                toggleBtn.setIcon(ma.TeethCare.mvc.ui.palette.utils.IconUtils.getIcon(ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_HIDE, 18, ma.TeethCare.mvc.ui.palette.utils.TailwindPalette.BLUE_900));
            } else {
                toggleBtn.setText("Menu");
                toggleBtn.setIcon(ma.TeethCare.mvc.ui.palette.utils.IconUtils.getIcon(ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_VIEW, 18, ma.TeethCare.mvc.ui.palette.utils.TailwindPalette.BLUE_900));
            }
        });
        
        topBar.add(toggleBtn);
        contentArea.add(topBar, BorderLayout.NORTH);
        
        // Main Module Panel
        JPanel modulePanel = new JPanel(new BorderLayout());
        modulePanel.setOpaque(false);
        modulePanel.setBorder(BorderFactory.createEmptyBorder(10, 24, 24, 24)); // p-6
        contentArea.add(modulePanel, BorderLayout.CENTER);
        
        // Default View
        modulePanel.add(new DashboardHome(), BorderLayout.CENTER);
        
        root.add(contentArea, BorderLayout.CENTER);
    }
    
    private void onModuleSelected(DashboardSidebar.Module module) {
         // Access modulePanel (child of contentArea)
         if (contentArea.getComponentCount() < 2) return;
         JPanel modulePanel = (JPanel) contentArea.getComponent(1); // 0 is topBar
         modulePanel.removeAll();
         
        switch (module) {
            case DASHBOARD:
                modulePanel.add(new DashboardHome(), BorderLayout.CENTER);
                break;
            case PATIENTS:
                modulePanel.add(new ma.TeethCare.mvc.ui.dashboard.doctor.components.PatientManagementView(), BorderLayout.CENTER);
                break;
            case MEDICAL_RECORDS:
                modulePanel.add(new ma.TeethCare.mvc.ui.dashboard.doctor.components.MedicalRecordsView(), BorderLayout.CENTER);
                break;
            case CONSULTATION:
                modulePanel.add(new ma.TeethCare.mvc.ui.dashboard.doctor.components.ConsultationView(), BorderLayout.CENTER);
                break;
            case PRESCRIPTIONS:
                modulePanel.add(new ma.TeethCare.mvc.ui.dashboard.doctor.components.PrescriptionView(), BorderLayout.CENTER);
                break;
            case CERTIFICATES:
                modulePanel.add(new ma.TeethCare.mvc.ui.dashboard.doctor.components.CertificateView(), BorderLayout.CENTER);
                break;
            case ACTS:
                modulePanel.add(new ma.TeethCare.mvc.ui.dashboard.doctor.components.ActsView(), BorderLayout.CENTER);
                break;
            case BILLING:
                modulePanel.add(new ma.TeethCare.mvc.ui.dashboard.doctor.components.CashView(), BorderLayout.CENTER);
                break;
            case FINANCIAL:
                modulePanel.add(new ma.TeethCare.mvc.ui.dashboard.doctor.components.FinancialSituationView(), BorderLayout.CENTER);
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
        l.setForeground(Color.GRAY);
        p.add(l);
        return p;
    }

    public static void main(String[] args) {
        try {
            com.formdev.flatlaf.FlatLightLaf.setup();
            UIManager.put("Panel.background", Color.decode("#EFF6FF")); // Blue-50 default
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new DoctorDashboardView().setVisible(true);
        });
    }
}
