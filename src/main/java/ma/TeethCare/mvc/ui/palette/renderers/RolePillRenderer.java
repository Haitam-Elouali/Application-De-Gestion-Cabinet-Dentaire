package ma.TeethCare.mvc.ui.palette.renderers;

import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class RolePillRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.setOpaque(true);
        panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        panel.setBorder(new EmptyBorder(10, 0, 10, 0)); 

        if (value != null) {
            String role = value.toString();
            Color bg;
            Color fg;

            if (role.equalsIgnoreCase("Médecin")) {
                bg = TailwindPalette.BLUE_100;
                fg = TailwindPalette.BLUE_800;
            } else if (role.equalsIgnoreCase("Secrétaire")) {
                bg = TailwindPalette.GREEN_100;
                fg = TailwindPalette.GREEN_800;
            } else if (role.equalsIgnoreCase("Administrateur")) {
                bg = TailwindPalette.ROSE_100;
                fg = TailwindPalette.ROSE_600;
            } else {
                bg = TailwindPalette.GRAY_100;
                fg = TailwindPalette.GRAY_800;
            }

            JLabel label = new JLabel(role);
            label.setFont(new Font("Segoe UI", Font.BOLD, 12));
            label.setForeground(fg);
            
            JPanel pill = new JPanel(new BorderLayout());
            pill.setOpaque(false);
            pill.add(label);
            pill.setBorder(new EmptyBorder(4, 12, 4, 12));

            JPanel container = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(bg);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                    super.paintComponent(g);
                }
            };
            container.setOpaque(false);
            container.add(pill);
            panel.add(container);
        }
        return panel;
    }
}
