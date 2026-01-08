package ma.TeethCare.mvc.ui.dashboard.doctor.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.data.ModernTable;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PrescriptionView extends JPanel {

    public PrescriptionView() {
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

        // Add Button
        ModernButton addBtn = new ModernButton("Ajouter une ordonnance", ModernButton.Variant.DEFAULT);
        topBar.add(addBtn, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Patient", "Date", "Nb. Médicaments", "Actions"};
        Object[][] data = {
            {"1", "Dubois Marie", "10/11/2025", "1", ""},
            {"2", "Martin Jean", "12/11/2025", "1", ""}
        };

        ModernTable table = new ModernTable();
        table.setModel(new DefaultTableModel(data, columns));
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        
        add(sp, BorderLayout.CENTER);
    }
}
