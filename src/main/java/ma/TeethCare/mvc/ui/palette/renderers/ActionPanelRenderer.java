package ma.TeethCare.mvc.ui.palette.renderers;

import ma.TeethCare.mvc.ui.palette.utils.IconUtils;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionPanelRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

    private final JPanel panel;
    private final JButton btnView;
    private final JButton btnEdit;
    private final JButton btnDelete;

    public ActionPanelRenderer() {
        panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panel.setOpaque(false);

        btnView = createIconButton(IconUtils.IconType.ICON_VIEW, TailwindPalette.BLUE_600);
        btnEdit = createIconButton(IconUtils.IconType.ICON_EDIT, TailwindPalette.ORANGE_600);
        btnDelete = createIconButton(IconUtils.IconType.ICON_DELETE, TailwindPalette.RED_600);

        panel.add(btnView);
        panel.add(btnEdit);
        panel.add(btnDelete);
    }

    private JButton createIconButton(IconUtils.IconType icon, Color hoverColor) {
        JButton btn = new JButton(IconUtils.getIcon(icon, 16, Color.GRAY));
        btn.setPreferredSize(new Dimension(28, 28));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Simple hover effect (optional, might need mouse listener but for renderer it's static)
        // For Editor it will work.
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setIcon(IconUtils.getIcon(icon, 16, hoverColor));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setIcon(IconUtils.getIcon(icon, 16, Color.GRAY));
            }
        });
        return btn;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        return panel;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        panel.setBackground(table.getSelectionBackground());
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }
}
