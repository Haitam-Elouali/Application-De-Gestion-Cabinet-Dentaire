package ma.TeethCare.mvc.ui.dashboard.secretary.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.containers.RoundedPanel;
import ma.TeethCare.mvc.ui.palette.data.ModernTable;
import ma.TeethCare.mvc.ui.palette.renderers.RiskCellRenderer;
import ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer;
import ma.TeethCare.mvc.ui.palette.utils.IconUtils;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AntecedentView extends JPanel {

    public AntecedentView() {
        setLayout(new BorderLayout());
        setOpaque(false); // Transparent to show Mint BG
        setBorder(new EmptyBorder(24, 24, 24, 24));

        initUI();
    }

    private void initUI() {
        // Content Card
        RoundedPanel card = new RoundedPanel(12);
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(24, 24, 24, 24));
        
        // Toolbar (Search + Add)
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setOpaque(false);
        toolbar.setBorder(new EmptyBorder(0, 0, 16, 0));
        
        // Search Container
        JPanel searchContainer = new JPanel(new BorderLayout());
        searchContainer.setOpaque(false);
        // Custom search field panel
        JPanel searchFieldPanel = new JPanel(new BorderLayout()) {
             @Override
             protected void paintComponent(Graphics g) {
                  Graphics2D g2 = (Graphics2D)g;
                  g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                  g2.setColor(Color.WHITE);
                  g2.fillRoundRect(0,0,getWidth(), getHeight(), 8, 8);
                  g2.setColor(TailwindPalette.BORDER);
                  g2.drawRoundRect(0,0,getWidth()-1, getHeight()-1, 8, 8);
             }
        };
        searchFieldPanel.setOpaque(false);
        searchFieldPanel.setPreferredSize(new Dimension(300, 40));
        searchFieldPanel.setBorder(new EmptyBorder(0, 12, 0, 12));
        
        JLabel searchIcon = new JLabel(IconUtils.getIcon(IconUtils.IconType.SEARCH, 18, Color.GRAY));
        JTextField searchField = new JTextField("Rechercher un antécédent...");
        searchField.setBorder(null);
        searchField.setOpaque(false);
        searchField.setForeground(Color.GRAY);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        searchFieldPanel.add(searchIcon, BorderLayout.WEST);
        searchFieldPanel.add(Box.createHorizontalStrut(8), BorderLayout.CENTER);
        searchFieldPanel.add(searchField, BorderLayout.CENTER);
        
        searchContainer.add(searchFieldPanel, BorderLayout.WEST);
        
        ModernButton addBtn = new ModernButton("Ajouter Antécédent", ModernButton.Variant.SUCCESS);
        
        toolbar.add(searchContainer, BorderLayout.WEST);
        toolbar.add(addBtn, BorderLayout.EAST);
        
        card.add(toolbar, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Risque", "Type", "Description", "Actions"};
        Object[][] data = {
            {"1", "Élevé", "Allergie", "Pénicilline - Choc anaphylactique", ""},
            {"2", "Moyen", "Diabète", "Type 2, Insulino-dépendant", ""},
            {"3", "Faible", "Chirurgie", "Appendicectomie (2010)", ""},
            {"4", "Élevé", "Cardiaque", "Hypertension sévère", ""}
        };

        ModernTable table = new ModernTable();
        table.setModel(new DefaultTableModel(data, columns) {
             @Override
             public boolean isCellEditable(int row, int column) {
                 return column == 4;
             }
        });
        
        table.setRowHeight(60);
        table.setShowGrid(false);
        
        // Renderers
        table.getColumnModel().getColumn(1).setCellRenderer(new RiskCellRenderer());
        // Use NEW TableActionCellRenderer
        table.getColumnModel().getColumn(4).setCellRenderer(new TableActionCellRenderer());
        table.getColumnModel().getColumn(4).setCellEditor(new TableActionCellRenderer());
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        sp.getViewport().setBackground(Color.WHITE);
        
        card.add(sp, BorderLayout.CENTER);
        
        add(card, BorderLayout.CENTER);
    }
}
