package ma.TeethCare.mvc.ui.palette.renderers;

import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Arrays;

public class PermissionTagRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        panel.setOpaque(true);
        panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        panel.setBorder(new EmptyBorder(4, 8, 4, 8));

        if (value != null) {
            String[] permissions = value.toString().split(","); // Assume comma separated or parse from object
            // If the value is "Perm1, Perm2", generic split
            
            for (String perm : permissions) {
                 JLabel tag = new JLabel(perm.trim());
                 tag.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                 tag.setForeground(TailwindPalette.GRAY_700);
                 
                 JPanel tagContainer = new JPanel(new BorderLayout()) {
                      @Override
                      protected void paintComponent(Graphics g) {
                          Graphics2D g2 = (Graphics2D)g;
                          g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                          g2.setColor(TailwindPalette.GRAY_200);
                          g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                          super.paintComponent(g);
                      }
                 };
                 tagContainer.setOpaque(false);
                 tagContainer.setBorder(new EmptyBorder(2, 6, 2, 6)); // Tag padding
                 tagContainer.add(tag);
                 
                 panel.add(tagContainer);
            }
        }
        return panel;
    }
}
