package ma.TeethCare.mvc.ui.dashboard.admin.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.data.ModernTable;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RoleManagementView extends JPanel {

    private ma.TeethCare.repository.api.RoleRepository roleRepository;

    public RoleManagementView() {
        this.roleRepository = new ma.TeethCare.repository.mySQLImpl.RoleRepositoryImpl();
        
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(24, 24, 24, 24));
        
        initUI();
    }

    private void initUI() {
        // Toolbar
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setOpaque(false);
        toolbar.setBorder(new EmptyBorder(0, 0, 16, 0));
        
        // Title
        JLabel title = new JLabel("Gestion des Rôles");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.decode("#1f2937"));
        
        toolbar.add(title, BorderLayout.WEST);
        
        // Add Button
        ModernButton addBtn = new ModernButton("Ajouter Rôle", ModernButton.Variant.DESTRUCTIVE); // Red
        
        toolbar.add(addBtn, BorderLayout.EAST);
        
        // Fetch Roles
        java.util.List<ma.TeethCare.entities.role.role> roles = new java.util.ArrayList<>();
        try {
            roles = roleRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Table
        String[] columns = {"ID", "Rôle", "Description", "Permissions Clés", "Actions"};
        Object[][] data = new Object[roles.size()][5];
        
        for(int i=0; i<roles.size(); i++) {
            ma.TeethCare.entities.role.role r = roles.get(i);
            data[i][0] = r.getId();
            data[i][1] = r.getLibelle();
            data[i][2] = "Description du rôle " + r.getLibelle(); // Placeholder
            data[i][3] = "Voir Détails"; // Placeholder
            data[i][4] = "";
        }

        ModernTable table = new ModernTable();
        table.setModel(new DefaultTableModel(data, columns));
        table.setRowHeight(60);
        
        // Renderers
        table.getColumnModel().getColumn(3).setCellRenderer(new ma.TeethCare.mvc.ui.palette.renderers.PermissionTagRenderer());
        
        // Actions
        ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer actionRenderer = new ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer(
            null,
            ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.EDIT,
            ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.DELETE
        );
        table.getColumnModel().getColumn(4).setCellRenderer(actionRenderer);
        table.getColumnModel().getColumn(4).setCellEditor(actionRenderer);
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        sp.getViewport().setBackground(Color.WHITE);
        
        // Card
        ma.TeethCare.mvc.ui.palette.containers.RoundedPanel card = new ma.TeethCare.mvc.ui.palette.containers.RoundedPanel(12);
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(24, 24, 24, 24));
        
        card.add(toolbar, BorderLayout.NORTH);
        card.add(sp, BorderLayout.CENTER);
        
        add(card, BorderLayout.CENTER);
    }
}
