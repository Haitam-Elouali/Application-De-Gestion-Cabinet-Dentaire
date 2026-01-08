package ma.TeethCare.mvc.ui.dashboard.admin.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.data.ModernTable;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RoleManagementView extends JPanel {

    public RoleManagementView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(24, 24, 24, 24));
        
        initUI();
    }

    private void initUI() {
        // Toolbar
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setOpaque(false);
        toolbar.setBorder(new EmptyBorder(0, 0, 16, 0));
        
        // Add Button
        ModernButton addBtn = new ModernButton("Ajouter Rôle", ModernButton.Variant.DESTRUCTIVE); // Red
        
        toolbar.add(addBtn, BorderLayout.EAST);
        
        add(toolbar, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"ID", "Rôle", "Description", "Permissions Clés", "Actions"};
        Object[][] data = {
            {"1", "Médecin", "Accès complet aux modules médicaux", "Dossier Médical (RW), Ordonnances (RW)", ""},
            {"2", "Secrétaire", "Gestion administrative et accueil", "Rendez-vous (RW), Facturation (RW)", ""},
            {"3", "Administrateur", "Contrôle total du système", "TOUTES", ""}
        };

        ModernTable table = new ModernTable();
        table.setModel(new DefaultTableModel(data, columns));
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        
        add(sp, BorderLayout.CENTER);
    }
}
