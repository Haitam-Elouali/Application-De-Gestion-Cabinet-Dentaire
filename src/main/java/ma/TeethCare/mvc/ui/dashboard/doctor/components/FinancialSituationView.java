package ma.TeethCare.mvc.ui.dashboard.doctor.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.containers.RoundedPanel;
import ma.TeethCare.mvc.ui.palette.data.ModernTable;
import ma.TeethCare.mvc.ui.palette.renderers.StatusPillRenderer;
import ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer;
import ma.TeethCare.mvc.ui.palette.utils.IconUtils;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FinancialSituationView extends JPanel {

    public FinancialSituationView() {
        this(ModernButton.Variant.DEFAULT);
    }

    private final ModernButton.Variant actionVariant;

    public FinancialSituationView(ModernButton.Variant actionVariant) {
        this.actionVariant = actionVariant;
        setLayout(new BorderLayout());
        setOpaque(false); // Transparent to show Mint BG
        setBorder(new EmptyBorder(24, 24, 24, 24));
        
        // Use a container for vertical stacking
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        
        // 1. Top Cards (Recettes vs Reste à Payer)
        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 24, 0));
        statsPanel.setOpaque(false);
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));
        
        // Card 1: Recettes
        statsPanel.add(createModernStatCard(
            "Recettes Totales (Payé)", 
            "85.00 €", 
            TailwindPalette.GREEN_600, 
            TailwindPalette.GREEN_100, 
            IconUtils.IconType.EURO
        ));
        
        // Card 2: Reste à Payer
        statsPanel.add(createModernStatCard(
            "Reste à Payer", 
            "1250.00 €", 
            TailwindPalette.RED_600, 
            TailwindPalette.RED_100, 
            IconUtils.IconType.FILE_WARNING
        ));
        
        content.add(statsPanel);
        content.add(Box.createVerticalStrut(32));
        
        // Main Card for Table
        RoundedPanel tableCard = new RoundedPanel(12);
        tableCard.setBackground(Color.WHITE);
        tableCard.setLayout(new BorderLayout());
        tableCard.setBorder(new EmptyBorder(24, 24, 24, 24));
        
        // 2. Header and Add Button
        JPanel tableHeader = new JPanel(new BorderLayout());
        tableHeader.setOpaque(false);
        
        JLabel title = new JLabel("Liste des Factures");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(TailwindPalette.GRAY_800);
        tableHeader.add(title, BorderLayout.WEST);
        
        ModernButton addBtn = new ModernButton("Nouvelle Facture", this.actionVariant); // Updated variant
        tableHeader.add(addBtn, BorderLayout.EAST);
        
        tableCard.add(tableHeader, BorderLayout.NORTH);
        
        // 3. Table
        String[] columns = {"N°", "Patient", "Date", "Type", "Montant", "Statut", "Actions"};
        Object[][] data = {
            {"101", "Dubois Marie", "2025-11-10", "Soins Dentaires", "85.0 €", "Payé", ""},
            {"102", "Martin Jean", "2025-11-12", "Prothèse Complète", "1200.0 €", "Non Payé", ""},
            {"103", "Bernard Sophie", "2025-10-25", "Consultation Urgence", "50.0 €", "En Retard", ""}
        };

        ModernTable table = new ModernTable();
        table.setModel(new DefaultTableModel(data, columns) {
             @Override
             public boolean isCellEditable(int row, int column) {
                 return column == 6;
             }
        });
        
        table.setRowHeight(60);
        table.setShowGrid(false);
        
        // Renderers
        table.getColumnModel().getColumn(5).setCellRenderer(new StatusPillRenderer());
        // Switch to TableActionCellRenderer for consistency
        // Switch to TableActionCellRenderer with specific actions: PRINT, BILL, EDIT, DELETE
        ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer actionRenderer = new ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer(
            ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.PRINT,
            ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.BILL,
            ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.EDIT,
            ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.DELETE
        );
        table.getColumnModel().getColumn(6).setCellRenderer(actionRenderer);
        table.getColumnModel().getColumn(6).setCellEditor(actionRenderer);
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        sp.getViewport().setBackground(Color.WHITE);
        
        tableCard.add(sp, BorderLayout.CENTER);
        
        content.add(tableCard);
        
        add(content, BorderLayout.CENTER);
    }
    
    private JPanel createModernStatCard(String title, String value, Color fgColor, Color iconBg, IconUtils.IconType iconType) {
        RoundedPanel p = new RoundedPanel(20);
        p.setBackground(Color.WHITE);
        p.setLayout(new BorderLayout());
        p.setBorder(new EmptyBorder(20, 24, 20, 24));
        
        // Left: Text
        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 4));
        textPanel.setOpaque(false);
        
        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 14));
        t.setForeground(TailwindPalette.GRAY_500);
        
        JLabel v = new JLabel(value);
        v.setFont(new Font("Segoe UI", Font.BOLD, 28)); // Huge font
        v.setForeground(fgColor);
        
        textPanel.add(t);
        textPanel.add(v);
        
        p.add(textPanel, BorderLayout.CENTER);
        
        // Right: Icon
        JPanel iconContainer = new JPanel(new GridBagLayout());
        iconContainer.setOpaque(false);
        
        JLabel icon = new JLabel() {
             @Override
             protected void paintComponent(Graphics g) {
                 Graphics2D g2 = (Graphics2D)g;
                 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                 g2.setColor(iconBg);
                 g2.fillOval(0,0,getWidth(), getHeight());
                 super.paintComponent(g);
             }
        };
        icon.setPreferredSize(new Dimension(56, 56));
        icon.setIcon(IconUtils.getIcon(iconType, 24, fgColor));
        icon.setHorizontalAlignment(SwingConstants.CENTER);
        
        iconContainer.add(icon);
        p.add(iconContainer, BorderLayout.EAST);
        
        return p;
    }
}
