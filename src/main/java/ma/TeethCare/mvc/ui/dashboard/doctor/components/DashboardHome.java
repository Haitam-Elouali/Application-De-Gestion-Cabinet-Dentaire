package ma.TeethCare.mvc.ui.dashboard.doctor.components;

import ma.TeethCare.mvc.ui.palette.containers.ModernCard;
import ma.TeethCare.mvc.ui.palette.data.ModernBadge;
import ma.TeethCare.mvc.ui.palette.data.ModernTable;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DashboardHome extends JPanel {

    public DashboardHome() {
        setLayout(new BorderLayout());
        setOpaque(false); // Transparent to show Blue background
        setBorder(new EmptyBorder(0, 0, 0, 0)); // Padding handled by parent modulePanel

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        
        // 1. Stats Row
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 16, 0)); // 4 cols, gap 16
        statsPanel.setOpaque(false);
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        
        statsPanel.add(new StatsCard("Patients du jour", "8", StatsCard.Type.BLUE, ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_PATIENTS));
        statsPanel.add(new StatsCard("Consultations", "5", StatsCard.Type.GREEN, ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_CONSULTATION));
        statsPanel.add(new StatsCard("Actes réalisés", "12", StatsCard.Type.PURPLE, ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_ACTS));
        statsPanel.add(new StatsCard("Revenus du jour", "3 450 MAD", StatsCard.Type.ORANGE, ma.TeethCare.mvc.ui.palette.utils.IconUtils.IconType.ICON_CASH));
        
        content.add(statsPanel);
        content.add(Box.createVerticalStrut(24)); // Gap

        // 2. Planning + Notifications Grid
        // Swing doesn't have CSS Grid, so we use a Panel with BorderLayout or GridBagLayout
        JPanel mainGrid = new JPanel(new GridBagLayout());
        mainGrid.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Left: Planning (Take more width)
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0.7; gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 16); // right gap
        
        ModernCard planningCard = new ModernCard();
        planningCard.setLayout(new BorderLayout());
        
        // Header of Card
        JPanel pHeader = new JPanel(new BorderLayout());
        pHeader.setOpaque(false);
        pHeader.setBorder(new EmptyBorder(0, 0, 16, 0));
        JLabel pTitle = new JLabel("Planning du jour");
        pTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        pHeader.add(pTitle, BorderLayout.WEST);
        planningCard.add(pHeader, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"Heure", "Patient", "Motif", "Statut"};
        Object[][] data = {
            {"09:00", "Dupont Jean", "Consultation", "Terminé"},
            {"10:30", "Martin Sophie", "Détartrage", "En cours"},
            {"11:15", "Bernard Luc", "Urgence", "En attente"},
            {"14:00", "Petit Pierre", "Contrôle", "Prévu"},
            {"15:30", "Dubois Marie", "Soins carie", "Prévu"}
        };
        
        ModernTable table = new ModernTable();
        table.setModel(new DefaultTableModel(data, columns));
        // Add status renderer
        table.getColumnModel().getColumn(3).setCellRenderer(new ma.TeethCare.mvc.ui.palette.renderers.StatusPillRenderer());
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createEmptyBorder());
        planningCard.add(sp, BorderLayout.CENTER);
        
        // Right: Notifications REMOVED
        
        // Adjust Planning to take full width
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 1.0; gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        
        // Remove previous add to re-add with new constraints
        mainGrid.remove(planningCard);
        mainGrid.add(planningCard, gbc);
        
        content.add(mainGrid);

        add(content, BorderLayout.CENTER);
    }
}
