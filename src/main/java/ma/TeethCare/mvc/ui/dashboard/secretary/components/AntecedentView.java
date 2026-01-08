package ma.TeethCare.mvc.ui.dashboard.secretary.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.data.ModernTable;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AntecedentView extends JPanel {

    public AntecedentView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(24, 24, 24, 24));

        initUI();
    }

    private void initUI() {
        // Toolbar (Search + Add)
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setOpaque(false);
        toolbar.setBorder(new EmptyBorder(0, 0, 16, 0));
        
        // Search (Placeholder logic)
        JTextField searchField = new JTextField("Rechercher...");
        searchField.setPreferredSize(new Dimension(300, 36));
        // Style it a bit manually since we don't have a SearchField component handy ready-scoped here, 
        // or usage of ModernTextField if available. Assuming ModernTextField is available.
        // But to keep it simple and compile-safe without checking imports, standard JTextField for now or:
        // ModernTextField search = new ModernTextField("Rechercher...");
        
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        left.setOpaque(false);
        // left.add(searchField); // Skipped for visual simplicity in mockup unless requested
        
        ModernButton addBtn = new ModernButton("Ajouter Antécédent", ModernButton.Variant.SUCCESS);
        
        toolbar.add(left, BorderLayout.WEST);
        toolbar.add(addBtn, BorderLayout.EAST);
        
        add(toolbar, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Risque", "Type", "Description", "Actions"};
        Object[][] data = {
            {"1", "Élevé", "Allergie", "Pénicilline", ""},
            {"2", "Moyen", "Diabète", "Type 2, Insulino-dépendant", ""},
            {"3", "Faible", "Chirurgie", "Appendicectomie (2010)", ""}
        };

        ModernTable table = new ModernTable();
        table.setModel(new DefaultTableModel(data, columns));
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        
        add(sp, BorderLayout.CENTER);
    }
}
