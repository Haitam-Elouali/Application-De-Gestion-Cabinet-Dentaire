package ma.TeethCare.mvc.ui.palette;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.containers.ModernCard;
import ma.TeethCare.mvc.ui.palette.fields.ModernTextField;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import java.awt.*;

public class PaletteDemoFrame extends JFrame {

    public PaletteDemoFrame() {
        setTitle("TeethCare UI Palette Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(TailwindPalette.BACKGROUND);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(TailwindPalette.BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Section: Buttons
        addSection(mainPanel, "Buttons", createButtonsPanel());

        // Section: Inputs
        addSection(mainPanel, "Inputs", createInputsPanel());

        // Section: Cards
        addSection(mainPanel, "Cards", createCardsPanel());
        
        // Section: Badges
        addSection(mainPanel, "Badges", createBadgesPanel());

        // Section: Table
        addSection(mainPanel, "Table", createTablePanel());

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addSection(JPanel parent, String title, JComponent content) {
        JLabel label = new JLabel(title);
        label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        label.setForeground(TailwindPalette.FOREGROUND);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.add(label);
        parent.add(Box.createVerticalStrut(10));
        
        content.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.add(content);
        parent.add(Box.createVerticalStrut(30));
    }

    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setOpaque(false);

        panel.add(new ModernButton("Default", ModernButton.Variant.DEFAULT));
        panel.add(new ModernButton("Secondary", ModernButton.Variant.SECONDARY));
        panel.add(new ModernButton("Outline", ModernButton.Variant.OUTLINE));
        panel.add(new ModernButton("Ghost", ModernButton.Variant.GHOST));
        panel.add(new ModernButton("Destructive", ModernButton.Variant.DESTRUCTIVE));
        panel.add(new ModernButton("Link", ModernButton.Variant.LINK));

        return panel;
    }

    private JPanel createInputsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setOpaque(false);

        ModernTextField tf1 = new ModernTextField();
        tf1.setPreferredSize(new Dimension(250, 40));
        tf1.setText("Enter text here...");
        
        ModernTextField tf2 = new ModernTextField();
        tf2.setPreferredSize(new Dimension(250, 40));
        tf2.setText("Another input");

        panel.add(tf1);
        panel.add(tf2);

        return panel;
    }

    private JPanel createCardsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setOpaque(false);

        ModernCard card1 = new ModernCard();
        card1.setPreferredSize(new Dimension(300, 180));
        card1.setLayout(new BorderLayout());
        
        JLabel title = new JLabel("Card Title");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        card1.add(title, BorderLayout.NORTH);
        
        JTextArea content = new JTextArea("This is the content of the card. It mimics the Shadcn card component.");
        content.setWrapStyleWord(true);
        content.setLineWrap(true);
        content.setOpaque(false);
        content.setEditable(false);
        content.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        card1.add(content, BorderLayout.CENTER);
        
        ModernButton btn = new ModernButton("Action", ModernButton.Variant.DEFAULT);
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setOpaque(false);
        btnPanel.add(btn);
        card1.add(btnPanel, BorderLayout.SOUTH);

        panel.add(card1);

        return panel;
    }

    private JPanel createBadgesPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setOpaque(false);

        panel.add(new ma.TeethCare.mvc.ui.palette.data.ModernBadge("Default", ma.TeethCare.mvc.ui.palette.data.ModernBadge.Variant.DEFAULT));
        panel.add(new ma.TeethCare.mvc.ui.palette.data.ModernBadge("Secondary", ma.TeethCare.mvc.ui.palette.data.ModernBadge.Variant.SECONDARY));
        panel.add(new ma.TeethCare.mvc.ui.palette.data.ModernBadge("Outline", ma.TeethCare.mvc.ui.palette.data.ModernBadge.Variant.OUTLINE));
        panel.add(new ma.TeethCare.mvc.ui.palette.data.ModernBadge("Destructive", ma.TeethCare.mvc.ui.palette.data.ModernBadge.Variant.DESTRUCTIVE));
        panel.add(new ma.TeethCare.mvc.ui.palette.data.ModernBadge("Success", ma.TeethCare.mvc.ui.palette.data.ModernBadge.Variant.SUCCESS));
        panel.add(new ma.TeethCare.mvc.ui.palette.data.ModernBadge("Warning", ma.TeethCare.mvc.ui.palette.data.ModernBadge.Variant.WARNING));
        panel.add(new ma.TeethCare.mvc.ui.palette.data.ModernBadge("Info", ma.TeethCare.mvc.ui.palette.data.ModernBadge.Variant.INFO));

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(700, 200));

        String[] columns = {"ID", "Name", "Role", "Status"};
        Object[][] data = {
            {"001", "Dr. Smith", "Doctor", "Active"},
            {"002", "Jane Doe", "Nurse", "On Leave"},
            {"003", "John Coltrane", "Admin", "Active"},
            {"004", "Miles Davis", "Patient", "Inactive"}
        };

        ma.TeethCare.mvc.ui.palette.data.ModernTable table = new ma.TeethCare.mvc.ui.palette.data.ModernTable();
        table.setModel(new javax.swing.table.DefaultTableModel(data, columns));
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(ma.TeethCare.mvc.ui.palette.utils.TailwindPalette.BORDER));
        panel.add(sp, BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PaletteDemoFrame().setVisible(true);
        });
    }
}
