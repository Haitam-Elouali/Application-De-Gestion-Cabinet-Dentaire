package ma.TeethCare.mvc.ui.dashboard.doctor.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.containers.RoundedPanel;
import ma.TeethCare.mvc.ui.palette.data.ModernTable;
import ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer;
import ma.TeethCare.mvc.ui.palette.utils.IconUtils;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PatientManagementView extends JPanel {

    private final ModernButton.Variant actionVariant;

    public PatientManagementView(ModernButton.Variant actionVariant) {
        this.actionVariant = actionVariant;
        setLayout(new BorderLayout());
        setOpaque(false); // Transparent
        setBorder(new EmptyBorder(24, 24, 24, 24)); // Outer padding

        initUI();
    }
    
    // Default constructor for compatibility (defaults to Blue/DEFAULT)
    public PatientManagementView() {
        this(ModernButton.Variant.DEFAULT);
    }

    private void initUI() {
        // Content Card
        RoundedPanel card = new RoundedPanel(12);
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(24, 24, 24, 24));

        // Top Bar: Search + Add Button
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Search Field Container
        JPanel searchContainer = new JPanel(new BorderLayout());
        searchContainer.setOpaque(false);
        
        // Custom search layout
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
        JTextField searchField = new JTextField("Rechercher un patient...");
        searchField.setBorder(null);
        searchField.setOpaque(false);
        searchField.setForeground(Color.GRAY);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        searchFieldPanel.add(searchIcon, BorderLayout.WEST);
        searchFieldPanel.add(Box.createHorizontalStrut(8), BorderLayout.CENTER);
        searchFieldPanel.add(searchField, BorderLayout.CENTER);
        
        searchContainer.add(searchFieldPanel, BorderLayout.WEST);
        
        topBar.add(searchContainer, BorderLayout.WEST);

        // Add Button
        ModernButton addBtn = new ModernButton("Nouveau Patient", this.actionVariant);
        topBar.add(addBtn, BorderLayout.EAST);

        card.add(topBar, BorderLayout.NORTH);

        // Table
        String[] columns = {"Nom", "Prénom", "Date Naissance", "Téléphone", "Email", "Antécédents", "Actions"};
        Object[][] data = {
            {"Dupont", "Jean", "1980-05-15", "0601020304", "jean.dupont@email.com", "Diabète", ""},
            {"Martin", "Sophie", "1992-11-20", "0605060708", "sophie.martin@email.com", "Allergie", ""}
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
        // Antecedents (Col 5) - Use StatusPillRenderer (or similar badge logic)
        table.getColumnModel().getColumn(5).setCellRenderer(new ma.TeethCare.mvc.ui.palette.renderers.StatusPillRenderer());
        
        // Actions (Col 6) - Use VIEW_ICON, EDIT, DELETE
        TableActionCellRenderer actionRenderer = new TableActionCellRenderer(
             TableActionCellRenderer.ActionType.VIEW_ICON,
             TableActionCellRenderer.ActionType.EDIT, 
             TableActionCellRenderer.ActionType.DELETE
        );
        table.getColumnModel().getColumn(6).setCellRenderer(actionRenderer);
        table.getColumnModel().getColumn(6).setCellEditor(actionRenderer);
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        sp.getViewport().setBackground(Color.WHITE);
        
        card.add(sp, BorderLayout.CENTER);
        
        add(card, BorderLayout.CENTER);
    }
}
