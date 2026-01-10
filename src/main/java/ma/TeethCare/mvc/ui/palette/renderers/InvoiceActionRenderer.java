package ma.TeethCare.mvc.ui.palette.renderers;

import ma.TeethCare.mvc.ui.palette.utils.IconUtils;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class InvoiceActionRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

    private final JPanel panel;
    private final JButton btnPrint;
    private final JButton btnEdit;

    public InvoiceActionRenderer() {
        panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panel.setOpaque(false);

        btnPrint = createIconButton(IconUtils.IconType.PRINTER, TailwindPalette.BLUE_600); // Printer icon
        btnEdit = createIconButton(IconUtils.IconType.ICON_EDIT, TailwindPalette.ORANGE_600);   // Edit icon

        panel.add(btnPrint);
        panel.add(btnEdit);
    }

    private JButton createIconButton(IconUtils.IconType icon, Color hoverColor) {
        JButton btn = new JButton(IconUtils.getIcon(icon, 16, Color.GRAY));
        btn.setPreferredSize(new Dimension(28, 28));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Hover effect could be added via MouseListener if needed
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
