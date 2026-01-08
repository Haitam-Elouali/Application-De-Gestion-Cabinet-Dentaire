package ma.TeethCare.mvc.ui.dashboard.admin.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.data.ModernTable;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// A Reusable view for Catalogs (Acts, Meds, Insurance, Antecedents)
public class GenericCatalogView extends JPanel {

    private final String title;
    private final String itemName;
    private final boolean hasPrice;
    
    public GenericCatalogView(String title, String itemName, boolean hasPrice) {
        this.title = title;
        this.itemName = itemName;
        this.hasPrice = hasPrice;
        
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(24, 24, 24, 24));
        
        initUI();
    }

    private void initUI() {
        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(0, 0, 16, 0));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(TailwindPalette.GRAY_50); // Using Gray-800 equivalent or just Dark Gray
        titleLabel.setForeground(Color.decode("#1f2937"));
        
        ModernButton addBtn = new ModernButton("Ajouter " + itemName, ModernButton.Variant.DEFAULT); // Default = Blue, maybe Red preferred? React uses Blue/Primary often for actions inside. Checked React: No specific color for "Ajouter" found but usually primary.
        
        header.add(titleLabel, BorderLayout.WEST);
        header.add(addBtn, BorderLayout.EAST);
        
        add(header, BorderLayout.NORTH);
        
        // Table
        String[] columns = hasPrice 
             ? new String[]{"ID", "Code", "Nom", "Description", "Prix", "Actions"}
             : new String[]{"ID", "Code", "Nom", "Description", "Actions"};

        // Mock Data
        Object[][] data;
        if (hasPrice) {
            data = new Object[][]{
                {"1", "C001", "Consultation Généraliste", "Consultation standard", "250.0", ""},
                {"2", "D005", "Détartrage", "Nettoyage complet", "400.0", ""}
            };
        } else {
             data = new Object[][]{
                {"1", "DOL100", "Doliprane 1000mg", "Antalgique", ""},
                {"2", "AMO500", "Amoxicilline 500mg", "Antibiotique", ""}
            };
        }

        ModernTable table = new ModernTable();
        table.setModel(new DefaultTableModel(data, columns));
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        
        add(sp, BorderLayout.CENTER);
    }
}
