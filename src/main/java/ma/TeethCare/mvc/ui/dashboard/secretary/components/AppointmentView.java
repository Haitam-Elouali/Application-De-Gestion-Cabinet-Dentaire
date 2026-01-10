package ma.TeethCare.mvc.ui.dashboard.secretary.components;

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

public class AppointmentView extends JPanel {

    private enum Tab {
        APPOINTMENTS("Rendez-vous"),
        WAITING_LIST("File d'attente");
        
        final String label;
        Tab(String label) { this.label = label; }
    }

    private Tab activeTab = Tab.APPOINTMENTS;
    private JPanel contentPanel;
    private JPanel tabsPanel;


    public AppointmentView() {
        setLayout(new BorderLayout());
        setOpaque(false); // Transparent to show Mint BG
        setBorder(new EmptyBorder(24, 24, 24, 24)); // Outer padding

        initUI();
    }

    private void initUI() {
        // Top: Tabs
        tabsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        tabsPanel.setOpaque(false);
        tabsPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, TailwindPalette.BORDER));

        updateTabs();

        add(tabsPanel, BorderLayout.NORTH);

        // Center: Content wrapped in RoundedPanel
        RoundedPanel card = new RoundedPanel(12);
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(24, 24, 24, 24));

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        
        updateContent();
        
        card.add(contentPanel, BorderLayout.CENTER);
        add(card, BorderLayout.CENTER);
    }
    
    // ... Tabs logic remains similar ...

    private void updateTabs() {
        tabsPanel.removeAll();
        tabsPanel.add(createTabButton(Tab.APPOINTMENTS));
        tabsPanel.add(createTabButton(Tab.WAITING_LIST));
        tabsPanel.revalidate();
        tabsPanel.repaint();
    }

    private JButton createTabButton(Tab tab) {
        JButton btn = new JButton(tab.label) {
            @Override
            protected void paintComponent(Graphics g) {
                if (activeTab == tab) {
                    g.setColor(TailwindPalette.GREEN_50);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
                super.paintComponent(g);
                
                if (activeTab == tab) {
                     g.setColor(TailwindPalette.GREEN_600);
                     g.fillRect(0, getHeight() - 2, getWidth(), 2);
                }
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(activeTab == tab ? TailwindPalette.GREEN_700 : Color.GRAY);
        
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(12, 24, 12, 24));
        
        btn.addActionListener(e -> {
            activeTab = tab;
            updateTabs();
            updateContent();
        });
        
        return btn;
    }

    private void updateContent() {
        contentPanel.removeAll();
        
        if (activeTab == Tab.APPOINTMENTS) {
            contentPanel.add(createAppointmentsPanel(), BorderLayout.CENTER);
        } else {
            contentPanel.add(createWaitingListPanel(), BorderLayout.CENTER);
        }
        
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel createAppointmentsPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        
        // Toolbar (Search + Add Button)
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setOpaque(false);
        toolbar.setBorder(new EmptyBorder(0, 0, 16, 0));
        
        // Search Bar
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
        JTextField searchField = new JTextField("Rechercher un rendez-vous...");
        searchField.setBorder(null);
        searchField.setOpaque(false);
        searchField.setForeground(Color.GRAY);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        searchFieldPanel.add(searchIcon, BorderLayout.WEST);
        searchFieldPanel.add(Box.createHorizontalStrut(8), BorderLayout.CENTER);
        searchFieldPanel.add(searchField, BorderLayout.CENTER);
        
        searchContainer.add(searchFieldPanel, BorderLayout.WEST);

        // Add Button
        ModernButton addBtn = new ModernButton("+ Ajouter RDV", ModernButton.Variant.SUCCESS);
        
        toolbar.add(searchContainer, BorderLayout.WEST);
        toolbar.add(addBtn, BorderLayout.EAST);
        
        p.add(toolbar, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"ID", "Nom", "Prénom", "Date", "Heure", "Type", "Statut", "Actions"};
        Object[][] data = {
            {"1", "Dubois", "Marie", "2025-11-15", "09:00", "Consultation", "Confirmé", ""},
            {"2", "Martin", "Jean", "2025-11-15", "10:30", "Détartrage", "Confirmé", ""},
            {"3", "Bernard", "Sophie", "2025-11-16", "14:00", "Soin", "En attente", ""},
            {"4", "Petit", "Emma", "2025-11-16", "16:00", "Urgence", "Annulé", ""}
        };
        
        ModernTable table = new ModernTable();
        table.setModel(new DefaultTableModel(data, columns) {
             @Override
             public boolean isCellEditable(int row, int column) {
                 return column == 7;
             }
        });
        
        table.setRowHeight(60);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        
        // Apply Renderers
        table.getColumnModel().getColumn(6).setCellRenderer(new StatusPillRenderer());
        table.getColumnModel().getColumn(7).setCellRenderer(new TableActionCellRenderer());
        table.getColumnModel().getColumn(7).setCellEditor(new TableActionCellRenderer());
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        sp.getViewport().setBackground(Color.WHITE);
        
        p.add(sp, BorderLayout.CENTER);
        return p;
    }
    
    private JPanel createWaitingListPanel() {
         JPanel p = new JPanel(new BorderLayout());
         p.setOpaque(false);
         // Build waiting list table similar to above but different cols
         
        String[] columns = {"ID", "Patient", "Arrivée", "Priorité", "Statut", "Actions"};
        Object[][] data = {
            {"1", "Dubois Marie", "08:45", "Normal", "En attente", ""},
            {"2", "Martin Jean", "10:15", "Haute", "En soin", ""}
        };
        
        ModernTable table = new ModernTable();
        table.setModel(new DefaultTableModel(data, columns) {
             @Override
             public boolean isCellEditable(int row, int column) {
                 return column == 5;
             }
        });
        
        table.setRowHeight(60);
        table.setShowGrid(false);
        
        table.getColumnModel().getColumn(4).setCellRenderer(new StatusPillRenderer());
        table.getColumnModel().getColumn(5).setCellRenderer(new TableActionCellRenderer());
        table.getColumnModel().getColumn(5).setCellEditor(new TableActionCellRenderer());
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        sp.getViewport().setBackground(Color.WHITE);
        
        p.add(sp, BorderLayout.CENTER);
         return p;
    }
}
