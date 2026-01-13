package ma.TeethCare.mvc.ui.dashboard.secretary.components;

import ma.TeethCare.mvc.ui.palette.containers.RoundedPanel;
import ma.TeethCare.mvc.ui.palette.utils.IconUtils;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import ma.TeethCare.repository.mySQLImpl.ChargesRepositoryImpl;
import ma.TeethCare.repository.mySQLImpl.RevenuesRepositoryImpl;
import ma.TeethCare.repository.mySQLImpl.FactureRepositoryImpl; // Added
import ma.TeethCare.service.modules.caisse.api.FinancialStatisticsService;
import ma.TeethCare.service.modules.caisse.impl.FinancialStatisticsServiceImpl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import ma.TeethCare.mvc.ui.palette.data.ModernTable;
import ma.TeethCare.mvc.ui.palette.renderers.StatusPillRenderer;
import net.miginfocom.swing.MigLayout;

public class CashManagementView extends JPanel {

    private FinancialStatisticsService financialService;
    private ma.TeethCare.service.modules.agenda.api.rdvService rdvService; // Re-added

    public CashManagementView() {
        // Initialize Service with FactureRepository
        this.financialService = new FinancialStatisticsServiceImpl(
            new RevenuesRepositoryImpl(), 
            new ChargesRepositoryImpl(),
            new FactureRepositoryImpl()
        );
        this.rdvService = new ma.TeethCare.service.modules.agenda.impl.rdvServiceImpl(new ma.TeethCare.repository.mySQLImpl.RdvRepositoryImpl());

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(24, 24, 24, 24)); 

        initUI();
    }

    private void initUI() {
        JPanel mainContainer = new JPanel(new MigLayout("insets 0, fillx, wrap 1", "[grow]", "[]20[]20[]20[grow]"));
        mainContainer.setOpaque(false);
        
        // Top Actions Bar (Export) - Keeping it
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        JLabel titleLabel = new JLabel("Gestion de Caisse");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(TailwindPalette.GRAY_900);
        topBar.add(titleLabel, BorderLayout.WEST);
        
        ma.TeethCare.mvc.ui.palette.buttons.ModernButton exportBtn = new ma.TeethCare.mvc.ui.palette.buttons.ModernButton("Exporter le Rapport", ma.TeethCare.mvc.ui.palette.buttons.ModernButton.Variant.DEFAULT);
        exportBtn.setIcon(IconUtils.getIcon(IconUtils.IconType.ICON_PRINT, 16, Color.WHITE));
        exportBtn.setCustomColor(new Color(16, 185, 129), new Color(209, 250, 229)); 
        exportBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Export du rapport financier en cours..."));
        topBar.add(exportBtn, BorderLayout.EAST);
        
        mainContainer.add(topBar, "growx");
 
        // --- Fetch Initial Data ---
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        FinancialStatisticsService.FinancialSummary summary = financialService.getFinancialSummary(
                now.withDayOfYear(1), now); // Year to Date
        
        long totalConsultations = 0;
        double montantMoyen = 0.0;
        
        try {
             totalConsultations = rdvService.count();
             if (totalConsultations > 0 && summary.totalRecettes() > 0) {
                 montantMoyen = summary.totalRecettes() / totalConsultations;
             }
        } catch (Exception e) {}

        // --- 1. Top Cards (KPIs) - Only Total Recettes ---
        JPanel kpiPanel = new JPanel(new MigLayout("insets 0, fill, gap 20", "[grow]"));
        kpiPanel.setOpaque(false);

        JLabel kpiRecettesValue = new JLabel(formatCurrency(summary.totalRecettes()));
        kpiPanel.add(createKpiCard("Total Recettes (YTD)", kpiRecettesValue, "", TailwindPalette.GREEN_100, TailwindPalette.GREEN_600, "c:\\Users\\Choukhairi\\Desktop\\Application-De-Gestion-Cabinet-Dentaire-2\\Screenshots\\gain.png"), "grow");
        
        mainContainer.add(kpiPanel, "growx");

        // --- 2. Middle Stats (Restored) ---
        JPanel statsPanel = new JPanel(new MigLayout("insets 0, fill, gap 20", "[grow][grow][grow]"));
        statsPanel.setOpaque(false);

        statsPanel.add(createStatCard("Montant Moyen", formatCurrency(montantMoyen), IconUtils.IconType.CLIPBOARD, TailwindPalette.PURPLE_500, null), "grow");
        statsPanel.add(createStatCard("Taux Remise", "0%", IconUtils.IconType.ICON_EDIT, TailwindPalette.ORANGE_500, null), "grow");
        statsPanel.add(createStatCard("Total RDV", String.valueOf(totalConsultations), IconUtils.IconType.USERS, TailwindPalette.TEAL_500, "c:\\Users\\Choukhairi\\Desktop\\Application-De-Gestion-Cabinet-Dentaire-2\\Screenshots\\consultation.png"), "grow");

        mainContainer.add(statsPanel, "growx");

        // --- Recent Transactions Table ---
        JPanel tableCard = new RoundedPanel(20);
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(new EmptyBorder(20, 20, 20, 20));
        tableCard.setLayout(new BorderLayout());

        JLabel tableTitle = new JLabel("Transactions Récentes (Paiements Patients)");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tableTitle.setForeground(TailwindPalette.GRAY_800);
        tableTitle.setBorder(new EmptyBorder(0, 0, 16, 0));
        tableCard.add(tableTitle, BorderLayout.NORTH);

        // Table Setup
        String[] columns = {"Type", "Patient", "Date", "Montant Payé", "Statut"};
        // Real Data
        List<FinancialStatisticsService.TransactionDTO> recentTx = financialService.getRecentTransactions(20);
        
        Object[][] data = new Object[recentTx.size()][5];
        java.time.format.DateTimeFormatter dtf = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for(int i=0; i<recentTx.size(); i++) {
            FinancialStatisticsService.TransactionDTO tx = recentTx.get(i);
            data[i][0] = tx.type();
            data[i][1] = tx.label(); // Assuming label now holds patient name
            data[i][2] = tx.date() != null ? tx.date().format(dtf) : "-";
            data[i][3] = String.format("%,.2f DH", tx.amount());
            data[i][4] = tx.status();
        }

        ModernTable table = new ModernTable();
        table.setModel(new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        });
        table.setRowHeight(50);
        
        // Renderers
        table.getColumnModel().getColumn(4).setCellRenderer(new StatusPillRenderer());
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        sp.getViewport().setBackground(Color.WHITE);
        
        tableCard.add(sp, BorderLayout.CENTER);

        mainContainer.add(tableCard, "grow");

        add(mainContainer, BorderLayout.CENTER);
    }
    private String formatCurrency(Double amount) {
        if (amount == null) return "0 DH";
        return String.format("%,.0f DH", amount);
    }

    private JPanel createKpiCard(String title, JLabel valueLabel, String percent, Color bg, Color fg, String imagePath) {
        RoundedPanel p = new RoundedPanel(20);
        p.setBackground(Color.WHITE);
        p.setLayout(new MigLayout("insets 24, fill", "[]push[]", "[]10[]")); // Title...Icon / Value...Percent

        // Title
        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 14));
        t.setForeground(TailwindPalette.GRAY_500);
        p.add(t);

        // Icon
        JLabel iconLabel = new JLabel();
        if (imagePath != null && !imagePath.isEmpty()) {
             try {
                 java.io.File imgFile = new java.io.File(imagePath);
                 if (imgFile.exists()) {
                     ImageIcon rawIcon = new ImageIcon(imagePath);
                     Image scaled = rawIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                     iconLabel.setIcon(new ImageIcon(scaled));
                 } else {
                     iconLabel.setIcon(IconUtils.getIcon(IconUtils.IconType.BUILDING, 20, fg)); 
                 }
             } catch (Exception e) {
                 e.printStackTrace();
             }
        } else {
             iconLabel.setIcon(IconUtils.getIcon(IconUtils.IconType.BUILDING, 20, fg));
        }
        
        p.add(iconLabel, "wrap");

        // Value
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(TailwindPalette.GRAY_900);
        p.add(valueLabel);

        // Percent Pill (Empty if no percent)
        if (percent != null && !percent.isEmpty()) {
            JPanel pill = new JPanel(new BorderLayout());
            pill.setOpaque(false);
            JLabel pl = new JLabel(percent);
            pl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            pl.setForeground(fg);
            pill.add(pl);
            
            JPanel pillContainer = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D)g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(bg);
                    g2.fillRoundRect(0,0,getWidth(), getHeight(), 12, 12);
                    super.paintComponent(g);
                }
            };
            pillContainer.setOpaque(false);
            pillContainer.setBorder(new EmptyBorder(4, 8, 4, 8));
            pillContainer.add(pill);
            p.add(pillContainer, "align right");
        }

        return p;
    }

    private JPanel createStatCard(String title, String value, IconUtils.IconType iconType, Color iconColor, String imagePath) {
        RoundedPanel p = new RoundedPanel(16);
        p.setBackground(TailwindPalette.GRAY_50); 
        p.setLayout(new MigLayout("insets 16 20 16 20, fill", "[]push[]", "[]"));
        
        JPanel textInfo = new JPanel(new MigLayout("insets 0, gap 0", "[]", "[]4[]"));
        textInfo.setOpaque(false);
        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 12));
        t.setForeground(TailwindPalette.GRAY_500);
        
        JLabel v = new JLabel(value);
        v.setFont(new Font("Segoe UI", Font.BOLD, 18));
        v.setForeground(TailwindPalette.GRAY_800);
        
        textInfo.add(t, "wrap");
        textInfo.add(v);
        
        p.add(textInfo);
        
        JLabel icon = new JLabel();
        if (imagePath != null && !imagePath.isEmpty()) {
             try {
                 java.io.File imgFile = new java.io.File(imagePath);
                 if (imgFile.exists()) {
                     ImageIcon rawIcon = new ImageIcon(imagePath);
                     Image scaled = rawIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
                     icon.setIcon(new ImageIcon(scaled));
                 } else {
                     icon.setIcon(IconUtils.getIcon(iconType, 24, iconColor));
                 }
             } catch (Exception e) {
                 icon.setIcon(IconUtils.getIcon(iconType, 24, iconColor));
             }
        } else {
             icon.setIcon(IconUtils.getIcon(iconType, 24, iconColor));
        }
        p.add(icon);

        return p;
    }
}
