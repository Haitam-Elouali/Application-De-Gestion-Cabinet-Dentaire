package ma.TeethCare.mvc.ui;

import ma.TeethCare.mvc.ui.palette.buttons.MyButton;
import ma.TeethCare.mvc.ui.palette.cards.StatCard;
import ma.TeethCare.mvc.ui.palette.fields.CustomPasswordField;
import ma.TeethCare.mvc.ui.palette.fields.CustomTextField;
import ma.TeethCare.mvc.ui.palette.utils.UIConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UITestBench extends JFrame {

    public UITestBench() {
        setTitle("TeethCare — UI Component Test Bench");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(UIConstants.SURFACE_MAIN);
        setContentPane(root);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(UIConstants.SURFACE_MAIN);
        content.setBorder(new EmptyBorder(40, 60, 40, 60));

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        root.add(scroll, BorderLayout.CENTER);

        // Section: Buttons
        addSectionTitle(content, "Buttons (MyButton & Nav)");
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        btnPanel.setOpaque(false);
        btnPanel.add(new MyButton("Primary Button", null, UIConstants.FONT_BOLD));

        MyButton secondary = new MyButton("Hover Me", null, UIConstants.FONT_BOLD);
        btnPanel.add(secondary);

        content.add(btnPanel);

        // Section: Text Fields
        addSectionTitle(content, "Text Fields (CustomTextField & Password)");
        JPanel fieldPanel = new JPanel(new GridLayout(0, 1, 0, 15));
        fieldPanel.setOpaque(false);
        fieldPanel.add(new CustomTextField("Enter Username"));
        fieldPanel.add(new CustomPasswordField("Enter Password"));

        JPanel fieldContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fieldContainer.setOpaque(false);
        fieldContainer.add(fieldPanel);
        content.add(fieldContainer);

        // Section: Stat Cards
        addSectionTitle(content, "Stat Cards (StatCard)");
        JPanel cardPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        cardPanel.setOpaque(false);
        cardPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));

        cardPanel.add(new StatCard("Total Patients", "1,240", "+12% vs last month",
                null, UIConstants.SURFACE_GREEN, UIConstants.ACCENT_GREEN));

        cardPanel.add(new StatCard("Appointments Today", "42", "6 pending",
                null, new Color(239, 246, 255), new Color(29, 78, 216)));

        cardPanel.add(new StatCard("Revenue (MAD)", "15,800", "Updated 5m ago",
                null, new Color(255, 251, 235), new Color(180, 83, 9)));

        content.add(cardPanel);

        // Section: Colors & Typography
        addSectionTitle(content, "Design Tokens (UIConstants)");
        JPanel tokenPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        tokenPanel.setOpaque(false);
        tokenPanel.add(createColorSwatch(UIConstants.ACCENT_GREEN, "Accent Green"));
        tokenPanel.add(createColorSwatch(UIConstants.TEXT_DARK, "Text Dark"));
        tokenPanel.add(createColorSwatch(UIConstants.BORDER_GRAY, "Border Gray"));
        content.add(tokenPanel);

        setVisible(true);
    }

    private void addSectionTitle(JPanel container, String title) {
        container.add(Box.createVerticalStrut(30));
        JLabel lbl = new JLabel(title.toUpperCase());
        lbl.setFont(UIConstants.FONT_BOLD.deriveFont(12f));
        lbl.setForeground(UIConstants.TEXT_GRAY);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        container.add(lbl);
        container.add(Box.createVerticalStrut(10));
    }

    private JPanel createColorSwatch(Color color, String name) {
        JPanel p = new JPanel(new BorderLayout(0, 5));
        p.setOpaque(false);

        JPanel swatch = new JPanel();
        swatch.setPreferredSize(new Dimension(80, 40));
        swatch.setBackground(color);
        swatch.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_GRAY));

        JLabel lbl = new JLabel(name);
        lbl.setFont(UIConstants.FONT_REGULAR.deriveFont(11f));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);

        p.add(swatch, BorderLayout.CENTER);
        p.add(lbl, BorderLayout.SOUTH);
        return p;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UITestBench::new);
    }
}
