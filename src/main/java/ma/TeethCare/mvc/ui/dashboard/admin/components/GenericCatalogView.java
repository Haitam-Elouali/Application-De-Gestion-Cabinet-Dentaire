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
    private final java.util.function.Supplier<Object[][]> dataProvider;
    
    public GenericCatalogView(String title, String itemName, boolean hasPrice, java.util.function.Supplier<Object[][]> dataProvider) {
        this.title = title;
        this.itemName = itemName;
        this.hasPrice = hasPrice;
        this.dataProvider = dataProvider;
        
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
        titleLabel.setForeground(Color.decode("#1f2937"));
        
        ModernButton addBtn = new ModernButton("Ajouter " + itemName, ModernButton.Variant.DESTRUCTIVE);
        
        header.add(titleLabel, BorderLayout.WEST);
        header.add(addBtn, BorderLayout.EAST);
        
        // Table
        String[] columns = hasPrice 
             ? new String[]{"ID", "Code", "Nom", "Description", "Prix", "Actions"}
             : new String[]{"ID", "Code", "Nom", "Description", "Actions"};

        // Fetch Data using Provider
        Object[][] data = new Object[0][0];
        try {
            data = dataProvider.get();
        } catch (Exception e) {
            e.printStackTrace();
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
