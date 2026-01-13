package ma.TeethCare.mvc.ui.dashboard.doctor.components;

import ma.TeethCare.mvc.ui.palette.containers.RoundedPanel;
import ma.TeethCare.mvc.ui.palette.utils.IconUtils;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;
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
import java.awt.*;

public class CashView extends JPanel {

    private DefaultCategoryDataset chartDataset;

    public CashView() {
        setLayout(new BorderLayout());
        setOpaque(false); // Transparent to show Blue background
        setBorder(new EmptyBorder(24, 24, 24, 24)); // Component padding

        initUI();
    }

    private void initUI() {
        // Main Container with MigLayout (Top KPIs, Middle Stats, Bottom Chart)
        // Rows: [TopCards] 20px [MiddleStats] 20px [Chart]
        JPanel mainContainer = new JPanel(new MigLayout("insets 0, fillx, wrap 1", "[grow]", "[]20[]20[grow]"));
        mainContainer.setOpaque(false);

        // --- 1. Top Cards (KPIs) ---
        JPanel kpiCardPanel = new JPanel(new MigLayout("insets 0, fill, gap 20", "[grow][grow][grow]")); // 3 Columns
        kpiCardPanel.setOpaque(false);

        kpiCardPanel.add(createKpiCard("Total Recettes", "124 500 DH", "+12%", TailwindPalette.GREEN_100, TailwindPalette.GREEN_600, "c:\\Users\\Choukhairi\\Desktop\\Application-De-Gestion-Cabinet-Dentaire-2\\Screenshots\\gain.png"), "grow");
        kpiCardPanel.add(createKpiCard("Total Dépenses", "45 200 DH", "-5%", TailwindPalette.RED_100, TailwindPalette.RED_600, "c:\\Users\\Choukhairi\\Desktop\\Application-De-Gestion-Cabinet-Dentaire-2\\Screenshots\\depense_img.png"), "grow");
        kpiCardPanel.add(createKpiCard("Bénéfice Net", "79 300 DH", "+18%", TailwindPalette.BLUE_100, TailwindPalette.BLUE_600, "c:\\Users\\Choukhairi\\Desktop\\Application-De-Gestion-Cabinet-Dentaire-2\\Screenshots\\benefice_img.png"), "grow");

        mainContainer.add(kpiCardPanel, "growx");

        // --- 2. Middle Stats ---
        JPanel statsPanel = new JPanel(new MigLayout("insets 0, fill, gap 20", "[grow][grow][grow]"));
        statsPanel.setOpaque(false);

        statsPanel.add(createStatCard("Montant Moyen", "450 DH", IconUtils.IconType.CLIPBOARD, TailwindPalette.PURPLE_500, null), "grow");
        statsPanel.add(createStatCard("Taux Remise", "5%", IconUtils.IconType.ICON_EDIT, TailwindPalette.ORANGE_500, null), "grow");
        statsPanel.add(createStatCard("Total Consultations", "850", IconUtils.IconType.USERS, TailwindPalette.TEAL_500, "c:\\Users\\Choukhairi\\Desktop\\Application-De-Gestion-Cabinet-Dentaire-2\\Screenshots\\consultation.png"), "grow");

        mainContainer.add(statsPanel, "growx");

        // --- 3. Chart Section ---
        RoundedPanel chartCard = new RoundedPanel(20);
        chartCard.setBackground(Color.WHITE);
        chartCard.setBorder(new EmptyBorder(20, 20, 20, 20));
        chartCard.setLayout(new BorderLayout());
        
        // Chart Header with Filters
        JPanel chartHeader = new JPanel(new BorderLayout());
        chartHeader.setOpaque(false);
        chartHeader.setBorder(new EmptyBorder(0, 0, 16, 0));
        
        JLabel chartTitle = new JLabel("Recettes vs Dépenses");
        chartTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        chartTitle.setForeground(TailwindPalette.GRAY_800);
        chartHeader.add(chartTitle, BorderLayout.WEST);
        
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        filterPanel.setOpaque(false);
        
        ma.TeethCare.mvc.ui.palette.buttons.ModernButton weekBtn = new ma.TeethCare.mvc.ui.palette.buttons.ModernButton("Semaine", ma.TeethCare.mvc.ui.palette.buttons.ModernButton.Variant.GHOST);
        weekBtn.setCustomColor(TailwindPalette.BLUE_600, TailwindPalette.BLUE_100);
        
        ma.TeethCare.mvc.ui.palette.buttons.ModernButton monthBtn = new ma.TeethCare.mvc.ui.palette.buttons.ModernButton("Mois", ma.TeethCare.mvc.ui.palette.buttons.ModernButton.Variant.DEFAULT);
        monthBtn.setCustomColor(TailwindPalette.BLUE_600, TailwindPalette.BLUE_100);
        
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

        mainContainer.add(chartCard, "grow, h 400!"); // Fix height for chart

        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(mainContainer);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
    }
    
    private JPanel createKpiCard(String title, String value, String percent, Color bg, Color fg, String imagePath) {
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
                     // Fallback if file not found
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
        JLabel v = new JLabel(value);
        v.setFont(new Font("Segoe UI", Font.BOLD, 24));
        v.setForeground(TailwindPalette.GRAY_900);
        p.add(v);

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
            chartDataset.addValue(4000, "Recettes", "Lun");
            chartDataset.addValue(2000, "Dépenses", "Lun");
            chartDataset.addValue(5500, "Recettes", "Mar");
            chartDataset.addValue(1500, "Dépenses", "Mar");
            chartDataset.addValue(3000, "Recettes", "Mer");
            chartDataset.addValue(2500, "Dépenses", "Mer");
            chartDataset.addValue(6000, "Recettes", "Jeu");
            chartDataset.addValue(3000, "Dépenses", "Jeu");
            chartDataset.addValue(4500, "Recettes", "Ven");
            chartDataset.addValue(2000, "Dépenses", "Ven");
            chartDataset.addValue(7000, "Recettes", "Sam");
            chartDataset.addValue(4000, "Dépenses", "Sam");
        } else {
            // Month View (Last 6 months)
            chartDataset.addValue(25000, "Recettes", "Juin");
            chartDataset.addValue(15000, "Dépenses", "Juin");
            chartDataset.addValue(32000, "Recettes", "Juil");
            chartDataset.addValue(18000, "Dépenses", "Juil");
            chartDataset.addValue(28000, "Recettes", "Aout");
            chartDataset.addValue(12000, "Dépenses", "Aout");
            chartDataset.addValue(45000, "Recettes", "Sept");
            chartDataset.addValue(20000, "Dépenses", "Sept");
            chartDataset.addValue(38000, "Recettes", "Oct");
            chartDataset.addValue(22000, "Dépenses", "Oct");
            chartDataset.addValue(42000, "Recettes", "Nov");
            chartDataset.addValue(19000, "Dépenses", "Nov");
        }
    }
}
