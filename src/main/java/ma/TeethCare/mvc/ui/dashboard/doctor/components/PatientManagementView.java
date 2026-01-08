package ma.TeethCare.mvc.ui.dashboard.doctor.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.data.ModernTable;
import ma.TeethCare.mvc.ui.palette.fields.ModernTextField;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PatientManagementView extends JPanel {

    public PatientManagementView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE); // or transparent if parent has bg
        setBorder(new EmptyBorder(24, 24, 24, 24));

        initUI();
    }

    private void initUI() {
        // Top Bar: Search + Add Button
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Search Field
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);
        searchPanel.setPreferredSize(new Dimension(300, 36)); // h-9
        
        ModernTextField searchField = new ModernTextField("Rechercher un patient...");
        searchPanel.add(searchField, BorderLayout.CENTER);
        
        topBar.add(searchPanel, BorderLayout.WEST);

        // Add Button
        ModernButton addBtn = new ModernButton("Nouveau Patient", ModernButton.Variant.DEFAULT);
        // Add icon later if possible
        topBar.add(addBtn, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);

        // Table
        String[] columns = {"Nom", "Prénom", "Date Naissance", "Téléphone", "Email", "Antécédents", "Actions"};
        Object[][] data = {
            {"Dupont", "Jean", "1980-05-15", "0601020304", "jean.dupont@email.com", "Diabète", ""},
            {"Martin", "Sophie", "1992-11-20", "0605060708", "sophie.martin@email.com", "Allergie", ""}
        };

        ModernTable table = new ModernTable();
        table.setModel(new DefaultTableModel(data, columns));
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        
        add(sp, BorderLayout.CENTER);
    }
}
