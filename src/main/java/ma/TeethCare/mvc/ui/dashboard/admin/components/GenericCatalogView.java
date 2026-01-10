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
        setOpaque(false); // Enable transparent background
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
        
        ModernButton addBtn = new ModernButton("Ajouter " + itemName, ModernButton.Variant.DESTRUCTIVE);
        
        header.add(titleLabel, BorderLayout.WEST);
        header.add(addBtn, BorderLayout.EAST);
        
        // Header moved into card
        // add(header, BorderLayout.NORTH);
        
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
        
        // Wraps content in a white card
        ma.TeethCare.mvc.ui.palette.containers.RoundedPanel card = new ma.TeethCare.mvc.ui.palette.containers.RoundedPanel(12);
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(24, 24, 24, 24));

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        
        card.add(header, BorderLayout.NORTH); // Move header inside card for clean look
        card.add(sp, BorderLayout.CENTER);
        
        add(card, BorderLayout.CENTER);
    }
}
