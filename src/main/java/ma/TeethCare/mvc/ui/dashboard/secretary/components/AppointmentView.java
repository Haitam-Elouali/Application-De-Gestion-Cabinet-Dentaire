package ma.TeethCare.mvc.ui.dashboard.secretary.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.data.ModernTable;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.function.Consumer;

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
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(24, 24, 24, 24));

        initUI();
    }

    private void initUI() {
        // Top: Tabs
        tabsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        tabsPanel.setOpaque(false);
        tabsPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, TailwindPalette.BORDER));

        updateTabs();

        add(tabsPanel, BorderLayout.NORTH);

        // Center: Content
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(24, 0, 0, 0));
        
        updateContent();
        
        add(contentPanel, BorderLayout.CENTER);
    }

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
        
        // Toolbar (Add Button)
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        toolbar.setOpaque(false);
        toolbar.setBorder(new EmptyBorder(0, 0, 16, 0));
        
        ModernButton addBtn = new ModernButton("Ajouter RDV", ModernButton.Variant.SUCCESS);
        toolbar.add(addBtn);
        p.add(toolbar, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"ID", "Nom", "Prénom", "Date", "Heure", "Type", "Statut", "Actions"};
        Object[][] data = {
            {"1", "Dubois", "Marie", "2025-11-15", "09:00", "Consultation", "Confirmé", ""},
            {"2", "Martin", "Jean", "2025-11-15", "10:30", "Détartrage", "Confirmé", ""},
            {"3", "Bernard", "Sophie", "2025-11-16", "14:00", "Soin", "En attente", ""}
        };
        
        ModernTable table = new ModernTable();
        table.setModel(new DefaultTableModel(data, columns));
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        
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
        table.setModel(new DefaultTableModel(data, columns));
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        
        p.add(sp, BorderLayout.CENTER);
         return p;
    }
}
