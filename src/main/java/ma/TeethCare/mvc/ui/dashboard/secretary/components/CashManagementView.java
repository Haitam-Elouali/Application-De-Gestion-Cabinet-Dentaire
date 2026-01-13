package ma.TeethCare.mvc.ui.dashboard.secretary.components;

import ma.TeethCare.mvc.ui.palette.containers.RoundedPanel;
import ma.TeethCare.mvc.ui.palette.utils.IconUtils;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;
import ma.TeethCare.repository.mySQLImpl.ChargesRepositoryImpl;
import ma.TeethCare.repository.mySQLImpl.RevenuesRepositoryImpl;
import ma.TeethCare.service.modules.caisse.api.FinancialStatisticsService;
import ma.TeethCare.service.modules.caisse.impl.FinancialStatisticsServiceImpl;
import net.miginfocom.swing.MigLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.Map;

import ma.TeethCare.mvc.ui.palette.data.ModernTable;
import ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer;
import ma.TeethCare.mvc.ui.palette.renderers.StatusPillRenderer;

public class CashManagementView extends JPanel {

    private DefaultCategoryDataset chartDataset;
    private FinancialStatisticsService financialService;
    private ma.TeethCare.service.modules.agenda.api.rdvService rdvService; // Inject RDV Service
    private JLabel kpiRecettesValue;
    private JLabel kpiDepensesValue;
    private JLabel kpiBeneficeValue;

    public CashManagementView() {
        // Initialize Service
        this.financialService = new FinancialStatisticsServiceImpl(new RevenuesRepositoryImpl(), new ChargesRepositoryImpl());
        this.rdvService = new ma.TeethCare.service.modules.agenda.impl.rdvServiceImpl(new ma.TeethCare.repository.mySQLImpl.RdvRepositoryImpl());

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(24, 24, 24, 24)); // Component padding

        initUI();
    }

    private void initUI() {
        // Main Container with MigLayout (Top KPIs, Middle Stats, Bottom Chart)
        JPanel mainContainer = new JPanel(new MigLayout("insets 0, fillx, wrap 1", "[grow]", "[]20[]20[grow]"));
        mainContainer.setOpaque(false);
        // Top Actions Bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        JLabel titleLabel = new JLabel("Gestion de Caisse");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(TailwindPalette.GRAY_900);
        topBar.add(titleLabel, BorderLayout.WEST);
        
        ma.TeethCare.mvc.ui.palette.buttons.ModernButton exportBtn = new ma.TeethCare.mvc.ui.palette.buttons.ModernButton("Exporter le Rapport", ma.TeethCare.mvc.ui.palette.buttons.ModernButton.Variant.DEFAULT);
        exportBtn.setIcon(IconUtils.getIcon(IconUtils.IconType.ICON_PRINT, 16, Color.WHITE));
        // Use Emerald 500 (#10B981) matching "Nouveau Patient" (Variant.SUCCESS)
        exportBtn.setCustomColor(new Color(16, 185, 129), new Color(209, 250, 229)); 
        exportBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Export du rapport financier en cours..."));
        topBar.add(exportBtn, BorderLayout.EAST);
        
        mainContainer.add(topBar, "growx");
 
        // --- Fetch Initial Data ---
        FinancialStatisticsService.FinancialSummary summary = financialService.getFinancialSummary(
                LocalDateTime.now().withDayOfYear(1), LocalDateTime.now()); // Year to Date
        
        long totalConsultations = 0;
        double montantMoyen = 0.0;
        
        try {
             totalConsultations = rdvService.count();
             if (totalConsultations > 0) {
                 montantMoyen = summary.totalRecettes() / totalConsultations;
             }
        } catch (Exception e) {}

        // --- 1. Top Cards (KPIs) ---
        JPanel kpiPanel = new JPanel(new MigLayout("insets 0, fill, gap 20", "[grow][grow][grow]")); // 3 Columns
        kpiPanel.setOpaque(false);

        kpiRecettesValue = new JLabel(formatCurrency(summary.totalRecettes()));
        kpiDepensesValue = new JLabel(formatCurrency(summary.totalDepenses()));
        kpiBeneficeValue = new JLabel(formatCurrency(summary.benefice()));

        kpiPanel.add(createKpiCard("Total Recettes", kpiRecettesValue, "", TailwindPalette.GREEN_100, TailwindPalette.GREEN_600, "c:\\Users\\Choukhairi\\Desktop\\Application-De-Gestion-Cabinet-Dentaire-2\\Screenshots\\gain.png"), "grow");
        kpiPanel.add(createKpiCard("Total Dépenses", kpiDepensesValue, "", TailwindPalette.RED_100, TailwindPalette.RED_600, "c:\\Users\\Choukhairi\\Desktop\\Application-De-Gestion-Cabinet-Dentaire-2\\Screenshots\\depense_img.png"), "grow");
        kpiPanel.add(createKpiCard("Bénéfice Net", kpiBeneficeValue, "", TailwindPalette.BLUE_100, TailwindPalette.BLUE_600, "c:\\Users\\Choukhairi\\Desktop\\Application-De-Gestion-Cabinet-Dentaire-2\\Screenshots\\benefice_img.png"), "grow");

        mainContainer.add(kpiPanel, "growx");

        // --- 2. Middle Stats ---
        JPanel statsPanel = new JPanel(new MigLayout("insets 0, fill, gap 20", "[grow][grow][grow]"));
        statsPanel.setOpaque(false);

        statsPanel.add(createStatCard("Montant Moyen", formatCurrency(montantMoyen), IconUtils.IconType.CLIPBOARD, TailwindPalette.PURPLE_500, null), "grow");
        statsPanel.add(createStatCard("Taux Remise", "0%", IconUtils.IconType.ICON_EDIT, TailwindPalette.ORANGE_500, null), "grow");
        statsPanel.add(createStatCard("Total RDV/Cons.", String.valueOf(totalConsultations), IconUtils.IconType.USERS, TailwindPalette.TEAL_500, "c:\\Users\\Choukhairi\\Desktop\\Application-De-Gestion-Cabinet-Dentaire-2\\Screenshots\\consultation.png"), "grow");

        mainContainer.add(statsPanel, "growx");

        // --- 3. Chart Section ---
        JPanel chartCard = new RoundedPanel(20);
        chartCard.setBackground(Color.WHITE);
        chartCard.setBorder(new EmptyBorder(20, 20, 20, 20));
        chartCard.setLayout(new BorderLayout());
        
        // Chart Header with Filters
        JPanel chartHeader = new JPanel(new BorderLayout());
        chartHeader.setOpaque(false);
        chartHeader.setBorder(new EmptyBorder(0, 0, 16, 0));
        
        JLabel chartTitle = new JLabel("Recettes vs Dépenses"); // Text simplified, filters control range
        chartTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        chartTitle.setForeground(TailwindPalette.GRAY_800);
        chartHeader.add(chartTitle, BorderLayout.WEST);
        
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        filterPanel.setOpaque(false);
        
        ma.TeethCare.mvc.ui.palette.buttons.ModernButton weekBtn = new ma.TeethCare.mvc.ui.palette.buttons.ModernButton("Semaine", ma.TeethCare.mvc.ui.palette.buttons.ModernButton.Variant.GHOST);
        weekBtn.setCustomColor(new Color(16, 185, 129), new Color(209, 250, 229));
        
        ma.TeethCare.mvc.ui.palette.buttons.ModernButton monthBtn = new ma.TeethCare.mvc.ui.palette.buttons.ModernButton("Mois", ma.TeethCare.mvc.ui.palette.buttons.ModernButton.Variant.DEFAULT); // Active state simulation
        monthBtn.setCustomColor(new Color(16, 185, 129), new Color(209, 250, 229));
        
        weekBtn.addActionListener(e -> {
             weekBtn.setVariant(ma.TeethCare.mvc.ui.palette.buttons.ModernButton.Variant.DEFAULT);
             monthBtn.setVariant(ma.TeethCare.mvc.ui.palette.buttons.ModernButton.Variant.GHOST);
             weekBtn.repaint(); monthBtn.repaint();
             updateChartData("WEEK");
        });
        
        monthBtn.addActionListener(e -> {
             monthBtn.setVariant(ma.TeethCare.mvc.ui.palette.buttons.ModernButton.Variant.DEFAULT);
             weekBtn.setVariant(ma.TeethCare.mvc.ui.palette.buttons.ModernButton.Variant.GHOST);
             weekBtn.repaint(); monthBtn.repaint();
             updateChartData("MONTH");
        });
        
        filterPanel.add(weekBtn);
        filterPanel.add(monthBtn);
        
        chartHeader.add(filterPanel, BorderLayout.EAST);
        
        chartCard.add(chartHeader, BorderLayout.NORTH);

        // JFreeChart
        ChartPanel chartPanel = createChartPanel();
        chartCard.add(chartPanel, BorderLayout.CENTER);

        mainContainer.add(chartCard, "grow, h 350!"); // Fixed height for chart

        // --- 4. Recent Transactions Table ---
        JPanel tableCard = new RoundedPanel(20);
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(new EmptyBorder(20, 20, 20, 20));
        tableCard.setLayout(new BorderLayout());

        JLabel tableTitle = new JLabel("Transactions Récentes");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tableTitle.setForeground(TailwindPalette.GRAY_800);
        tableTitle.setBorder(new EmptyBorder(0, 0, 16, 0));
        tableCard.add(tableTitle, BorderLayout.NORTH);

        // Table Setup
        String[] columns = {"Type", "Label", "Date", "Montant", "Statut"};
        java.util.List<FinancialStatisticsService.TransactionDTO> recentTx = financialService.getRecentTransactions(10);
        
        Object[][] data = new Object[recentTx.size()][5];
        java.time.format.DateTimeFormatter dtf = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for(int i=0; i<recentTx.size(); i++) {
            FinancialStatisticsService.TransactionDTO tx = recentTx.get(i);
            data[i][0] = tx.type();
            data[i][1] = tx.label();
            data[i][2] = tx.date() != null ? tx.date().format(dtf) : "-";
            data[i][3] = String.format("%,.2f DH", tx.amount());
            data[i][4] = tx.status();
        }

        ModernTable table = new ModernTable();
        table.setModel(new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No editable cells
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

        // WRAP IN SCROLL PANE
        JScrollPane mainScroll = new JScrollPane(mainContainer);
        mainScroll.setBorder(null);
        mainScroll.setOpaque(false);
        mainScroll.getViewport().setOpaque(false);
        mainScroll.getVerticalScrollBar().setUnitIncrement(16); // Faster scrolling

        add(mainScroll, BorderLayout.CENTER);
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

        // Percent Pill
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

        return p;
    }

    private JPanel createStatCard(String title, String value, IconUtils.IconType iconType, Color iconColor, String imagePath) {
        RoundedPanel p = new RoundedPanel(16);
        p.setBackground(TailwindPalette.GRAY_50); // Slightly gray to diff from main cards
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

    private ChartPanel createChartPanel() {
        chartDataset = new DefaultCategoryDataset();
        // Default to Month view
        updateChartData("MONTH");

        JFreeChart chart = ChartFactory.createBarChart(
                null,       // chart title
                null,       // domain axis label
                null,       // range axis label
                chartDataset,    // data
                PlotOrientation.VERTICAL,
                true,       // include legend
                true,       // tooltips
                false       // urls
        );

        // Customize Chart
        chart.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.WHITE);
        plot.setRangeGridlinePaint(TailwindPalette.GRAY_200);
        plot.setOutlineVisible(false);
        plot.setAxisOffset(new RectangleInsets(0, 0, 0, 0));

        // Renderer (Colors)
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(16, 185, 129)); // Emerald 500
        renderer.setSeriesPaint(1, new Color(239, 68, 68));  // Red 500
        renderer.setBarPainter(new org.jfree.chart.renderer.category.StandardBarPainter()); // Flat look
        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(false);
        renderer.setItemMargin(0.1);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setOpaque(false);
        chartPanel.setBorder(null);
        return chartPanel;
    }

    private void updateChartData(String period) {
        chartDataset.clear();
        if ("WEEK".equals(period)) {
            // Placeholder: Week view logic not implemented in service yet
            // Just clearing for now or keeping mock data if requested
        } else {
            // Month View from Service
            Map<String, Object> data = financialService.getChartData(LocalDateTime.now().getYear());
            double[] revenues = (double[]) data.get("revenues");
            double[] expenses = (double[]) data.get("expenses");
            
            String[] months = {"Jan", "Fev", "Mar", "Avr", "Mai", "Juin", "Juil", "Aout", "Sept", "Oct", "Nov", "Dec"};
            
            for (int i = 0; i < 12; i++) {
                // Filtering 0 values if desired, or showing all
                if (revenues[i] > 0 || expenses[i] > 0) {
                     chartDataset.addValue(revenues[i], "Recettes", months[i]);
                     chartDataset.addValue(expenses[i], "Dépenses", months[i]);
                }
            }
        }
    }

}
