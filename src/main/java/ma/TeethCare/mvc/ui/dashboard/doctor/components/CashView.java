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
        JPanel kpiPanel = new JPanel(new MigLayout("insets 0, fill, gap 20", "[grow][grow][grow]")); // 3 Columns
        kpiPanel.setOpaque(false);

        kpiPanel.add(createKpiCard("Total Recettes", "124 500 DH", "+12%", TailwindPalette.GREEN_100, TailwindPalette.GREEN_600), "grow");
        kpiPanel.add(createKpiCard("Total Dépenses", "45 200 DH", "-5%", TailwindPalette.RED_100, TailwindPalette.RED_600), "grow");
        kpiPanel.add(createKpiCard("Bénéfice Net", "79 300 DH", "+18%", TailwindPalette.BLUE_100, TailwindPalette.BLUE_600), "grow");

        mainContainer.add(kpiPanel, "growx");

        // --- 2. Middle Stats ---
        JPanel statsPanel = new JPanel(new MigLayout("insets 0, fill, gap 20", "[grow][grow][grow]"));
        statsPanel.setOpaque(false);

        statsPanel.add(createStatCard("Montant Moyen", "450 DH", IconUtils.IconType.CLIPBOARD, TailwindPalette.PURPLE_500), "grow");
        statsPanel.add(createStatCard("Taux Remise", "5%", IconUtils.IconType.ICON_EDIT, TailwindPalette.ORANGE_500), "grow");
        statsPanel.add(createStatCard("Total Consultations", "850", IconUtils.IconType.USERS, TailwindPalette.TEAL_500), "grow");

        mainContainer.add(statsPanel, "growx");

        // --- 3. Chart Section ---
        RoundedPanel chartCard = new RoundedPanel(20);
        chartCard.setBackground(Color.WHITE);
        chartCard.setBorder(new EmptyBorder(20, 20, 20, 20));
        chartCard.setLayout(new BorderLayout());
        
        // Chart Title
        JLabel chartTitle = new JLabel("Recettes vs Dépenses (Derniers 6 mois)");
        chartTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        chartTitle.setForeground(TailwindPalette.GRAY_800);
        chartTitle.setBorder(new EmptyBorder(0, 0, 16, 0));
        chartCard.add(chartTitle, BorderLayout.NORTH);

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
    
    private JPanel createKpiCard(String title, String value, String percent, Color bg, Color fg) {
        RoundedPanel p = new RoundedPanel(20);
        p.setBackground(Color.WHITE);
        p.setLayout(new MigLayout("insets 24, fill", "[]push[]", "[]10[]")); // Title...Icon / Value...Percent

        // Title
        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 14));
        t.setForeground(TailwindPalette.GRAY_500);
        p.add(t);

        // Icon (Circle)
        JPanel iconPanel = new JPanel(new BorderLayout());
        iconPanel.setOpaque(false);
        JLabel icon = new JLabel(IconUtils.getIcon(IconUtils.IconType.BUILDING, 20, fg)) { // Default icon
             @Override
             protected void paintComponent(Graphics g) {
                 Graphics2D g2 = (Graphics2D)g;
                 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                 g2.setColor(bg);
                 g2.fillOval(0,0,getWidth(), getHeight());
                 super.paintComponent(g);
             }
        };
        icon.setBorder(new EmptyBorder(8,8,8,8));
        p.add(icon, "wrap");

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

    private JPanel createStatCard(String title, String value, IconUtils.IconType iconType, Color iconColor) {
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
        
        JLabel icon = new JLabel(IconUtils.getIcon(iconType, 24, iconColor));
        p.add(icon);

        return p;
    }

    private ChartPanel createChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(25000, "Recettes", "Juin");
        dataset.addValue(15000, "Dépenses", "Juin");
        dataset.addValue(32000, "Recettes", "Juil");
        dataset.addValue(18000, "Dépenses", "Juil");
        dataset.addValue(28000, "Recettes", "Aout");
        dataset.addValue(12000, "Dépenses", "Aout");
        dataset.addValue(45000, "Recettes", "Sept");
        dataset.addValue(20000, "Dépenses", "Sept");
        dataset.addValue(38000, "Recettes", "Oct");
        dataset.addValue(22000, "Dépenses", "Oct");
        dataset.addValue(42000, "Recettes", "Nov");
        dataset.addValue(19000, "Dépenses", "Nov");

        JFreeChart chart = ChartFactory.createBarChart(
                null,       // chart title
                null,       // domain axis label
                null,       // range axis label
                dataset,    // data
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
}
