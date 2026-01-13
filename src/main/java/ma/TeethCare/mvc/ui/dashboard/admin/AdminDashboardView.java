package ma.TeethCare.mvc.ui.dashboard.admin;

import ma.TeethCare.mvc.ui.dashboard.admin.components.AdminHeader;
import ma.TeethCare.mvc.ui.dashboard.admin.components.AdminSidebar;

import javax.swing.*;
import java.awt.*;

public class AdminDashboardView extends JFrame {

    private JPanel contentArea;

    public AdminDashboardView() {
        setTitle("TeethCare - Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Or DISPOSE based on app logic
        setSize(1366, 768); // HD resolution
        setLocationRelativeTo(null);
        
        initUI();
    }

    private void initUI() {
        Container root = getContentPane();
        root.setLayout(new BorderLayout());

        // Header (Top)
        AdminHeader header = new AdminHeader("Admin User", () -> {
            new ma.TeethCare.mvc.ui.login.LoginView().setVisible(true);
            dispose();
        }); 
        root.add(header, BorderLayout.NORTH);

        // Sidebar (Left)
        AdminSidebar sidebar = new AdminSidebar(this::onModuleSelected);
        root.add(sidebar, BorderLayout.WEST);

        // Content Area (Center)
        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(Color.WHITE); // Switched to White per user request
        
        // Toggle Area
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 12));
        topBar.setOpaque(false);
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10)); // mb-6 approx
        
        JButton toggleBtn = new JButton("Cacher Menu");
        toggleBtn.setIcon(ma.TeethCare.mvc.ui.palette.utils.IconUtils.getIcon(ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_HIDE, 18, ma.TeethCare.mvc.ui.palette.utils.TailwindPalette.RED_900));
        toggleBtn.setBackground(ma.TeethCare.mvc.ui.palette.utils.TailwindPalette.RED_100);
        toggleBtn.setForeground(ma.TeethCare.mvc.ui.palette.utils.TailwindPalette.RED_900);
        toggleBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        toggleBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ma.TeethCare.mvc.ui.palette.utils.TailwindPalette.RED_300),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        toggleBtn.setFocusPainted(false);
        toggleBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        toggleBtn.addActionListener(e -> {
            boolean isVisible = sidebar.isVisible();
            sidebar.setVisible(!isVisible);
            if (!isVisible) {
                toggleBtn.setText("Cacher Menu");
                toggleBtn.setIcon(ma.TeethCare.mvc.ui.palette.utils.IconUtils.getIcon(ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_HIDE, 18, ma.TeethCare.mvc.ui.palette.utils.TailwindPalette.RED_900));
            } else {
                toggleBtn.setText("Menu");
                toggleBtn.setIcon(ma.TeethCare.mvc.ui.palette.utils.IconUtils.getIcon(ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_VIEW, 18, ma.TeethCare.mvc.ui.palette.utils.TailwindPalette.RED_900));
            }
        });
        
        topBar.add(toggleBtn);
        contentArea.add(topBar, BorderLayout.NORTH);
        
        // Modules Panel
        JPanel modulePanel = new JPanel(new BorderLayout());
        modulePanel.setOpaque(false);
        modulePanel.setBorder(BorderFactory.createEmptyBorder(10, 24, 24, 24));
        contentArea.add(modulePanel, BorderLayout.CENTER);
        
        // Default View: Users
        modulePanel.add(new ma.TeethCare.mvc.ui.dashboard.admin.components.UserManagementView(), BorderLayout.CENTER);
        
        root.add(contentArea, BorderLayout.CENTER);
    }
    
    private ma.TeethCare.repository.api.ActesRepository actesRepo = new ma.TeethCare.repository.mySQLImpl.ActesRepositoryImpl();
    private ma.TeethCare.repository.api.MedicamentRepository medRepo = new ma.TeethCare.repository.mySQLImpl.MedicamentRepositoryImpl();
    private ma.TeethCare.repository.api.AntecedentRepository antRepo = new ma.TeethCare.repository.mySQLImpl.AntecedentRepositoryImpl();

    private void onModuleSelected(AdminSidebar.Module module) {
         if (contentArea.getComponentCount() < 2) return;
         JPanel modulePanel = (JPanel) contentArea.getComponent(1);
         modulePanel.removeAll();
        
        switch (module) {
            case USERS:
                modulePanel.add(new ma.TeethCare.mvc.ui.dashboard.admin.components.UserManagementView(), BorderLayout.CENTER);
                break;
            case ROLES:
                modulePanel.add(new ma.TeethCare.mvc.ui.dashboard.admin.components.RoleManagementView(), BorderLayout.CENTER);
                break;
            case CATALOG_ACTS:
                modulePanel.add(new ma.TeethCare.mvc.ui.dashboard.admin.components.GenericCatalogView(
                    "Catalogue des Actes Médicaux", "un Acte", true, () -> {
                        try {
                            java.util.List<ma.TeethCare.entities.actes.actes> list = actesRepo.findAll();
                            Object[][] data = new Object[list.size()][6];
                            for(int i=0; i<list.size(); i++) {
                                ma.TeethCare.entities.actes.actes a = list.get(i);
                                data[i][0] = a.getId();
                                data[i][1] = a.getCode() != null ? a.getCode() : "-";
                                data[i][2] = a.getNom();
                                data[i][3] = a.getDescription() != null ? a.getDescription() : "";
                                data[i][4] = a.getPrix() + " DH";
                                data[i][5] = "";
                            }
                            return data;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return new Object[0][0]; // Return empty on error
                        }
                    }), BorderLayout.CENTER);
                break;
            case CATALOG_MEDS:
                modulePanel.add(new ma.TeethCare.mvc.ui.dashboard.admin.components.GenericCatalogView(
                    "Catalogue des Médicaments", "un Médicament", false, () -> {
                        try {
                            java.util.List<ma.TeethCare.entities.medicaments.medicaments> list = medRepo.findAll();
                            Object[][] data = new Object[list.size()][5];
                            for(int i=0; i<list.size(); i++) {
                                ma.TeethCare.entities.medicaments.medicaments m = list.get(i);
                                data[i][0] = m.getId();
                                data[i][1] = "MED-" + m.getId(); // Code fake derived from ID if missing
                                data[i][2] = m.getNomCommercial();
                                data[i][3] = m.getDescription() != null ? m.getDescription() : "";
                                data[i][4] = "";
                            }
                            return data;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return new Object[0][0];
                        }
                    }), BorderLayout.CENTER);
                break;
            case CATALOG_ANTECEDENTS:
                modulePanel.add(new ma.TeethCare.mvc.ui.dashboard.admin.components.GenericCatalogView(
                    "Catalogue des Antécédents", "un Antécédent", false, () -> {
                        try {
                            java.util.List<ma.TeethCare.entities.antecedent.antecedent> list = antRepo.findAll();
                            Object[][] data = new Object[list.size()][5];
                            for(int i=0; i<list.size(); i++) {
                                ma.TeethCare.entities.antecedent.antecedent a = list.get(i);
                                data[i][0] = a.getId();
                                data[i][1] = a.getCategorie(); // Use category as code
                                data[i][2] = a.getNom();
                                data[i][3] = a.getNiveauDeRisque() != null ? a.getNiveauDeRisque().toString() : "-";
                                data[i][4] = "";
                            }
                            return data;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return new Object[0][0];
                        }
                    }), BorderLayout.CENTER);
                break;
            case INSURANCE:
                modulePanel.add(new ma.TeethCare.mvc.ui.dashboard.admin.components.GenericCatalogView(
                    "Gestion des Assurances", "une Assurance", false, () -> {
                        // Display Enum values
                        ma.TeethCare.common.enums.Assurance[] values = ma.TeethCare.common.enums.Assurance.values();
                        Object[][] data = new Object[values.length][5];
                        for(int i=0; i<values.length; i++) {
                            data[i][0] = i+1;
                            data[i][1] = "ASS-" + (i+1);
                            data[i][2] = values[i].toString();
                            data[i][3] = "Assurance prédéfinie";
                            data[i][4] = "";
                        }
                        return data;
                    }), BorderLayout.CENTER);
                break;
            case SECURITY:
                modulePanel.add(new ma.TeethCare.mvc.ui.dashboard.admin.components.SecurityBackupManagementView(), BorderLayout.CENTER);
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
        l.setForeground(new Color(185, 28, 28)); // Red-700
        p.add(l);
        return p;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        
        SwingUtilities.invokeLater(() -> {
            new AdminDashboardView().setVisible(true);
        });
    }
}
