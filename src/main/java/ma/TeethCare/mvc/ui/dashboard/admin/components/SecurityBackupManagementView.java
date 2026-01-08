package ma.TeethCare.mvc.ui.dashboard.admin.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.data.ModernTable;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SecurityBackupManagementView extends JPanel {

    private enum Tab {
        SECURITY("Sécurité & Sessions"),
        BACKUPS("Sauvegardes");
        
        final String label;
        Tab(String label) { this.label = label; }
    }
    
    private Tab activeTab = Tab.SECURITY;
    private JPanel tabsPanel;
    private JPanel contentPanel;

    public SecurityBackupManagementView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(24, 24, 24, 24));
        
        initUI();
    }

    private void initUI() {
        // Tabs
        tabsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        tabsPanel.setOpaque(false);
        tabsPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, TailwindPalette.BORDER));
        
        updateTabs();
        add(tabsPanel, BorderLayout.NORTH);
        
        // Content
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(24, 0, 0, 0));
        
        updateContent();
        add(contentPanel, BorderLayout.CENTER);
    }

    private void updateTabs() {
        tabsPanel.removeAll();
        tabsPanel.add(createTabButton(Tab.SECURITY));
        tabsPanel.add(createTabButton(Tab.BACKUPS));
        tabsPanel.revalidate();
        tabsPanel.repaint();
    }

    private JButton createTabButton(Tab tab) {
        JButton btn = new JButton(tab.label) {
            @Override
            protected void paintComponent(Graphics g) {
                if (activeTab == tab) {
                    g.setColor(TailwindPalette.RED_50);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
                super.paintComponent(g);
                if (activeTab == tab) {
                     g.setColor(TailwindPalette.RED_600);
                     g.fillRect(0, getHeight() - 2, getWidth(), 2);
                }
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(activeTab == tab ? TailwindPalette.RED_700 : Color.GRAY);
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
        if (activeTab == Tab.SECURITY) {
            contentPanel.add(createSecurityPanel(), BorderLayout.CENTER);
        } else {
            contentPanel.add(createBackupPanel(), BorderLayout.CENTER);
        }
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private JPanel createSecurityPanel() {
        JPanel p = new JPanel(new BorderLayout(16, 16));
        p.setOpaque(false);
        
        // Sessions Table
        String[] columns = {"Utilisateur", "IP", "Début", "Durée", "Action"};
        Object[][] data = {
            {"Dr. Dupont", "192.168.1.105", "10:00", "2h 15m", ""},
            {"Secrétaire Sophie", "192.168.1.102", "08:30", "4h 45m", ""},
             {"Admin", "10.0.0.1", "14:00", "15m", ""}
        };
        
        ModernTable table = new ModernTable();
        table.setModel(new DefaultTableModel(data, columns));
        
        // Wrapper for title
        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        JLabel title = new JLabel("Sessions Actives");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setBorder(new EmptyBorder(0, 0, 12, 0));
        container.add(title, BorderLayout.NORTH);
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        container.add(sp, BorderLayout.CENTER);
        
        p.add(container, BorderLayout.CENTER);
        
        return p;
    }
    
    private JPanel createBackupPanel() {
        JPanel p = new JPanel(new BorderLayout(16, 16));
        p.setOpaque(false);
        
        // Top Actions
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actions.setOpaque(false);
        actions.add(new ModernButton("Lancer Sauvegarde Maintenant", ModernButton.Variant.DESTRUCTIVE));
        p.add(actions, BorderLayout.NORTH);
        
        // History Table
        String[] columns = {"Date", "Type", "Taille", "Statut", "Action"};
        Object[][] data = {
            {"28/10/2025 02:00", "Automatique", "245 MB", "Succès", ""},
            {"27/10/2025 02:00", "Automatique", "243 MB", "Succès", ""},
            {"25/10/2025 15:30", "Manuelle", "240 MB", "Succès", ""}
        };
        
        ModernTable table = new ModernTable();
        table.setModel(new DefaultTableModel(data, columns));
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        
        p.add(sp, BorderLayout.CENTER);
        
        return p;
    }
}
