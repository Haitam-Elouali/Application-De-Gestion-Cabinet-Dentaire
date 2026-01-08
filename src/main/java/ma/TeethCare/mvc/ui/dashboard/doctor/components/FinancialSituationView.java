package ma.TeethCare.mvc.ui.dashboard.doctor.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.data.ModernTable;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FinancialSituationView extends JPanel {

    public FinancialSituationView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(24, 24, 24, 24));
        
        // Use a container for vertical stacking
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        
        // 1. Stats Row
        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 24, 0));
        statsPanel.setOpaque(false);
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        
        statsPanel.add(new StatsCard("Recettes Totales (Payé)", "85.00 €", StatsCard.Type.GREEN)); // Sample data
        statsPanel.add(new StatsCard("Reste à Payer", "1250.00 €", StatsCard.Type.ORANGE)); // Using Orange as warning color placeholder
        
        content.add(statsPanel);
        content.add(Box.createVerticalStrut(24));
        
        // 2. Header and Add Button
        JPanel tableHeader = new JPanel(new BorderLayout());
        tableHeader.setOpaque(false);
        tableHeader.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        JLabel title = new JLabel("Liste des Factures");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        tableHeader.add(title, BorderLayout.WEST);
        
        ModernButton addBtn = new ModernButton("Nouvelle Facture", ModernButton.Variant.DEFAULT);
        tableHeader.add(addBtn, BorderLayout.EAST);
        
        content.add(tableHeader);
        content.add(Box.createVerticalStrut(12));

        // 3. Table
        String[] columns = {"N°", "Patient", "Date", "Type", "Montant", "Statut", "Actions"};
        Object[][] data = {
            {"101", "Dubois Marie", "2025-11-10", "Soins Dentaires", "85.0 €", "Payé", ""},
            {"102", "Martin Jean", "2025-11-12", "Prothèse Complète", "1200.0 €", "Non Payé", ""},
            {"103", "Bernard Sophie", "2025-10-25", "Consultation Urgence", "50.0 €", "En Retard", ""}
        };

        ModernTable table = new ModernTable();
        table.setModel(new DefaultTableModel(data, columns));
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        sp.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));
        
        content.add(sp);
        
        add(new JScrollPane(content), BorderLayout.CENTER);
    }
}
