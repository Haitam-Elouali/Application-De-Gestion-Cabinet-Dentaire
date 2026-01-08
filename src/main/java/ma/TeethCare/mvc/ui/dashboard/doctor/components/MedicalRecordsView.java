package ma.TeethCare.mvc.ui.dashboard.doctor.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.data.ModernTable;
import ma.TeethCare.mvc.ui.palette.fields.ModernTextField;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MedicalRecordsView extends JPanel {

    public MedicalRecordsView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE); 
        setBorder(new EmptyBorder(24, 24, 24, 24));

        initUI();
    }

    private void initUI() {
        // Top Bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Search Field
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);
        searchPanel.setPreferredSize(new Dimension(300, 36)); 
        
        ModernTextField searchField = new ModernTextField("Rechercher un dossier...");
        searchPanel.add(searchField, BorderLayout.CENTER);
        
        topBar.add(searchPanel, BorderLayout.WEST);

        // Add Button
        ModernButton addBtn = new ModernButton("Nouveau Dossier", ModernButton.Variant.DEFAULT);
        topBar.add(addBtn, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);

        // Table
        String[] columns = {"Patient", "Date Création", "Diagnostic Principal", "Traitement en cours", "Actions"};
        Object[][] data = {
            {"Dubois Marie", "2025-01-05", "Gingivite légère", "Détartrage + bain de bouche", ""},
            {"Martin Jean", "2025-02-10", "Carie dentaire molaire 16", "Soin + composite", ""}
        };

        ModernTable table = new ModernTable();
        table.setModel(new DefaultTableModel(data, columns));
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        
        add(sp, BorderLayout.CENTER);
    }
}
