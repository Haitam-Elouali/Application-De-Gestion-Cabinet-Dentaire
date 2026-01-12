package ma.TeethCare.mvc.ui.dashboard.admin.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.data.ModernTable;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UserManagementView extends JPanel {

    public UserManagementView() {
        setLayout(new BorderLayout());
        setOpaque(false); // Transparent
        setBorder(new EmptyBorder(24, 24, 24, 24));
        
        initUI();
    }

    private void initUI() {
        // Toolbar
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setOpaque(false);
        toolbar.setBorder(new EmptyBorder(0, 0, 16, 0));
        
        JLabel title = new JLabel("Gestion des Utilisateurs");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.decode("#1f2937"));

        ModernButton addBtn = new ModernButton("Nouvel Utilisateur", ModernButton.Variant.DESTRUCTIVE);
        
        toolbar.add(title, BorderLayout.WEST);
        toolbar.add(addBtn, BorderLayout.EAST);
        
        // Card
        ma.TeethCare.mvc.ui.palette.containers.RoundedPanel card = new ma.TeethCare.mvc.ui.palette.containers.RoundedPanel(12);
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(24, 24, 24, 24));

        card.add(toolbar, BorderLayout.NORTH);
        
        // Table (Rest added later, but toolbar needs to go into card)
        // Adjust hierarchy: add(card, BorderLayout.CENTER) instead of adding toolbar directly to this.
        
        add(card, BorderLayout.CENTER);
        
        // Table
        String[] columns = {"ID", "Utilisateur", "Rôle", "Email", "Statut", "Dernière Connexion", "Actions"};
        Object[][] data = {
            {"1", "Dr. Dupont Jean", "Médecin", "dr.dupont@teethcare.ma", "Actif", "08/01/2026 09:00", ""},
            {"2", "Mme. Martin Sophie", "Secrétaire", "s.martin@teethcare.ma", "Actif", "08/01/2026 08:30", ""},
             {"3", "Admin System", "Administrateur", "admin@teethcare.ma", "Actif", "08/01/2026 10:00", ""}
        };

        ModernTable table = new ModernTable();
        table.setModel(new DefaultTableModel(data, columns));
        table.setRowHeight(60);
        
        // Renderers
        table.getColumnModel().getColumn(2).setCellRenderer(new ma.TeethCare.mvc.ui.palette.renderers.RolePillRenderer());
        table.getColumnModel().getColumn(4).setCellRenderer(new ma.TeethCare.mvc.ui.palette.renderers.StatusPillRenderer());
        
        // Actions
        ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer actionRenderer = new ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer(
            null,
            ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.VIEW_FULL,
            ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.EDIT,
            ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.SUSPEND,
            ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.DELETE
        );
        table.getColumnModel().getColumn(6).setCellRenderer(actionRenderer);
        table.getColumnModel().getColumn(6).setCellEditor(actionRenderer);
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        sp.getViewport().setBackground(Color.WHITE);
        
        card.add(sp, BorderLayout.CENTER);
    }
}
