package ma.TeethCare.mvc.ui.dashboard.doctor.components;

import ma.TeethCare.mvc.ui.palette.containers.ModernCard;
import ma.TeethCare.mvc.ui.palette.data.ModernTable;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CashView extends JPanel {

    public CashView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(24, 24, 24, 24));
        
        // Scrollable content due to multiple sections
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);
        
        // 1. Stats Row
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 16, 0));
        statsPanel.setOpaque(false);
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        
        statsPanel.add(new StatsCard("Total Recettes", "125 000 MAD", StatsCard.Type.GREEN));
        statsPanel.add(new StatsCard("Total Dépenses", "45 000 MAD", StatsCard.Type.BLUE)); // Red variant not avail in StatsCard yet, using Blue
        statsPanel.add(new StatsCard("Bénéfice Net", "80 000 MAD", StatsCard.Type.PURPLE));
        
        content.add(statsPanel);
        content.add(statsPanel);
        content.add(Box.createVerticalStrut(24));

        // Toolbar
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        toolbar.setOpaque(false);
        toolbar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        JButton btnWeek = new JButton("Semaine");
        btnWeek.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnWeek.setBackground(Color.WHITE);
        btnWeek.setForeground(Color.DARK_GRAY);
        btnWeek.setFocusPainted(false); 
        btnWeek.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        JButton btnMonth = new JButton("Mois");
        btnMonth.setFont(new Font("Segoe UI", Font.BOLD, 13)); // Active style
        btnMonth.setBackground(TailwindPalette.BLUE_50);
        btnMonth.setForeground(TailwindPalette.BLUE_700);
        btnMonth.setFocusPainted(false);
        btnMonth.setBorder(BorderFactory.createLineBorder(TailwindPalette.BLUE_200));

        JButton btnExport = new JButton("Exporter");
        btnExport.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnExport.setBackground(Color.WHITE);
        btnExport.setForeground(Color.DARK_GRAY);
        btnExport.setFocusPainted(false);
        btnExport.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        // Icon for export
        // btnExport.setIcon(...)
        
        toolbar.add(new JLabel("Période: "));
        toolbar.add(btnWeek);
        toolbar.add(btnMonth);
        toolbar.add(Box.createHorizontalStrut(16));
        toolbar.add(btnExport);
        
        content.add(toolbar);
        content.add(Box.createVerticalStrut(12));
        
        // 2. Chart Placeholder (Swing has no native charts, using a colored panel placeholder)
        ModernCard chartCard = new ModernCard();
        chartCard.setLayout(new BorderLayout());
        chartCard.setPreferredSize(new Dimension(0, 300));
        chartCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        
        JLabel chartTitle = new JLabel("Évolution Recettes vs Dépenses");
        chartTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        chartTitle.setBorder(new EmptyBorder(0, 0, 16, 0));
        chartCard.add(chartTitle, BorderLayout.NORTH);
        
        JPanel chartPlaceholder = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(243, 244, 246));
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.GRAY);
                g.drawString("Graphique placeholder (Requires JFreeChart or custom painting)", getWidth()/2 - 150, getHeight()/2);
            }
        };
        chartCard.add(chartPlaceholder, BorderLayout.CENTER);
        
        content.add(chartCard);
        content.add(Box.createVerticalStrut(24));
        
        // 3. Transactions Table
        JLabel tableTitle = new JLabel("Transactions Récentes");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        tableTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(tableTitle);
        content.add(Box.createVerticalStrut(12));

        String[] columns = {"Date", "Patient/Entité", "Type", "Montant", "Statut"};
        Object[][] data = {
            {"01/01/2026", "Marie Dubois", "Consultation", "+800 MAD", "Payé"},
            {"31/12/2025", "Jean Martin", "Prothèse", "+4000 MAD", "Payé"},
            {"30/12/2025", "Fournitures", "Dépense", "-1200 MAD", "Payé"}
        };

        ModernTable table = new ModernTable();
        table.setModel(new DefaultTableModel(data, columns));
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        sp.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        
        content.add(sp);
        
        add(new JScrollPane(content), BorderLayout.CENTER);
    }
}
