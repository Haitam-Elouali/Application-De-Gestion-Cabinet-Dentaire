package ma.TeethCare.mvc.ui.dashboard.admin.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.data.ModernTable;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
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

    private ma.TeethCare.repository.api.LogRepository logRepository;

    public SecurityBackupManagementView() {
        this.logRepository = new ma.TeethCare.repository.mySQLImpl.LogRepositoryImpl();
        
        setLayout(new BorderLayout());
        setOpaque(false); // Transparent for background
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
        
        // Content Card
        ma.TeethCare.mvc.ui.palette.containers.RoundedPanel card = new ma.TeethCare.mvc.ui.palette.containers.RoundedPanel(12);
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(24, 24, 24, 24));

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        // contentPanel.setBorder(new EmptyBorder(24, 0, 0, 0)); // No extra spacing needed inside card
        
        updateContent();
        card.add(contentPanel, BorderLayout.CENTER);
        
        add(card, BorderLayout.CENTER);
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
        
        // --- Sessions Section ---
        String[] columns = {"Utilisateur", "IP", "Début", "Durée", "Action"};
        // Removed fake sessions data
        Object[][] data = new Object[0][5];
        
        ModernTable table = new ModernTable();
        table.setModel(new DefaultTableModel(data, columns));
        table.setRowHeight(50);
        
        // Red Text Renderer for Action Column
        table.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                l.setForeground(TailwindPalette.RED_600);
                l.setFont(new Font("Segoe UI", Font.BOLD, 12));
                l.setHorizontalAlignment(SwingConstants.CENTER);
                l.setText((value != null) ? value.toString() : "");
                l.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                return l;
            }
        });

        JPanel sessionContainer = new JPanel(new BorderLayout());
        sessionContainer.setOpaque(false);
        JLabel title = new JLabel("Sessions Actives (Mock - En cours d'implémentation)");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setBorder(new EmptyBorder(0, 0, 12, 0));
        sessionContainer.add(title, BorderLayout.NORTH);
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        sp.setPreferredSize(new Dimension(0, 200)); // Limit height
        sessionContainer.add(sp, BorderLayout.CENTER);
        
        p.add(sessionContainer, BorderLayout.NORTH);
        
        // --- Terminal Logs Section ---
        JPanel terminalContainer = new JPanel(new BorderLayout());
        terminalContainer.setOpaque(false);
        
        JLabel termTitle = new JLabel("Security Logs (Live Database Feed)");
        termTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        termTitle.setBorder(new EmptyBorder(16, 0, 12, 0));
        terminalContainer.add(termTitle, BorderLayout.NORTH);
        
        JPanel terminal = new JPanel();
        terminal.setLayout(new BoxLayout(terminal, BoxLayout.Y_AXIS));
        terminal.setBackground(new Color(15, 23, 42)); // #0F172A (Slate-900)
        terminal.setBorder(new EmptyBorder(16, 16, 16, 16));
        
        // Fetch Real Logs
        try {
            java.util.List<ma.TeethCare.entities.log.log> logs = logRepository.findAll();
            // Show latest 20 logs
            logs.stream()
                .sorted((l1, l2) -> l2.getDateAction().compareTo(l1.getDateAction())) // Descending
                .limit(20)
                .forEach(l -> {
                    String type = l.getTypeSupp() != null ? l.getTypeSupp() : "INFO";
                    Color c = TailwindPalette.GREEN_400;
                    if ("ERROR".equalsIgnoreCase(type)) c = TailwindPalette.RED_400;
                    if ("WARN".equalsIgnoreCase(type)) c = TailwindPalette.ORANGE_400;
                    
                    terminal.add(createLogLine(type, l.getMessage() != null ? l.getMessage() : "No message", c));
                });
                
            if (logs.isEmpty()) {
                terminal.add(createLogLine("INFO", "No logs found in database.", TailwindPalette.GRAY_400));
            }
        } catch (Exception e) {
             terminal.add(createLogLine("ERROR", "Failed to fetch logs: " + e.getMessage(), TailwindPalette.RED_400));
        }

        // Fill remaining space
        JPanel filler = new JPanel(); 
        filler.setOpaque(false);
        terminal.add(filler);

        JScrollPane termScroll = new JScrollPane(terminal);
        termScroll.setBorder(null);
        termScroll.getVerticalScrollBar().setUnitIncrement(16);
        terminalContainer.add(termScroll, BorderLayout.CENTER);
        
        p.add(terminalContainer, BorderLayout.CENTER);
        
        return p;
    }

    private JPanel createLogLine(String level, String msg, Color color) {
        JPanel line = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        line.setOpaque(false);
        line.setMaximumSize(new Dimension(Short.MAX_VALUE, 24));
        
        JLabel lblLevel = new JLabel("[" + level + "]");
        lblLevel.setFont(new Font("Consolas", Font.BOLD, 12)); // Monospaced
        lblLevel.setForeground(color);
        
        JLabel lblMsg = new JLabel(msg + "  -- " + java.time.LocalTime.now().toString());
        lblMsg.setFont(new Font("Consolas", Font.PLAIN, 12));
        lblMsg.setForeground(new Color(226, 232, 240)); // Slate-200
        
        line.add(lblLevel);
        line.add(lblMsg);
        return line;
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
        // Removed fake backup data
        Object[][] data = new Object[0][5];
        
        ModernTable table = new ModernTable();
        table.setModel(new DefaultTableModel(data, columns));
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        
        p.add(sp, BorderLayout.CENTER);
        
        return p;
    }
}
