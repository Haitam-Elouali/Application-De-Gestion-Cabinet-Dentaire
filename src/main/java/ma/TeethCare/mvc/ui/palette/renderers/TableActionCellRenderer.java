package ma.TeethCare.mvc.ui.palette.renderers;

import ma.TeethCare.mvc.ui.palette.utils.IconUtils;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import java.util.ArrayList;
import java.util.List;

public class TableActionCellRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

    public enum ActionType {
        VIEW_FULL, // "Dossier" + Icon
        VIEW_ICON, // Icon only
        EDIT,
        DELETE,
        PRINT,
        BILL,
        LINK,
        PERCENTAGE,
        RDV_MANAGE,
        SUSPEND
    }

    private final JPanel panel;
    private final List<JButton> buttons = new ArrayList<>();

    public TableActionCellRenderer(ActionType... actions) {
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 4));
        panel.setOpaque(false);

        // Default if empty
        if (actions.length == 0) {
            actions = new ActionType[]{ActionType.VIEW_FULL, ActionType.EDIT, ActionType.DELETE};
        }

        for (ActionType type : actions) {
            JButton btn = createButton(type);
            buttons.add(btn);
            panel.add(btn);
            // Placeholder listener
            btn.addActionListener(e -> fireEditingStopped());
        }
    }
    
    // Default constructor for backward compatibility (std view)
    public TableActionCellRenderer() {
        this(ActionType.VIEW_FULL, ActionType.EDIT, ActionType.DELETE);
    }

    private JButton createButton(ActionType type) {
        switch (type) {
            case VIEW_FULL:
                return createPillButton("Dossier", IconUtils.IconType.ICON_VIEW, TailwindPalette.GREEN_100, TailwindPalette.GREEN_800);
            case VIEW_ICON:
                return createSquareButton(IconUtils.IconType.ICON_VIEW, TailwindPalette.GREEN_100, TailwindPalette.GREEN_600);
            case EDIT:
                return createSquareButton(IconUtils.IconType.ICON_EDIT, TailwindPalette.BLUE_100, TailwindPalette.BLUE_600);
            case DELETE:
                return createSquareButton(IconUtils.IconType.ICON_DELETE, TailwindPalette.RED_100, TailwindPalette.RED_600);
            case SUSPEND:
                return createSquareButton(IconUtils.IconType.ICON_DISABLE, TailwindPalette.ORANGE_100, TailwindPalette.ORANGE_600);
            case PRINT:
                return createSquareButton(IconUtils.IconType.ICON_PRINT, TailwindPalette.GRAY_200, TailwindPalette.GRAY_700);
            case BILL:
                return createSquareButton(IconUtils.IconType.ICON_BILL, TailwindPalette.ORANGE_100, TailwindPalette.ORANGE_600);
            case LINK:
                return createSquareButton(IconUtils.IconType.ICON_LINK, TailwindPalette.PURPLE_100, TailwindPalette.PURPLE_600);
            case PERCENTAGE:
                return createSquareButton(IconUtils.IconType.ICON_PERCENT, TailwindPalette.TEAL_100, TailwindPalette.TEAL_600);
            case RDV_MANAGE:
                return createSquareButton(IconUtils.IconType.ICON_RDV_MANAGE, TailwindPalette.BLUE_100, TailwindPalette.BLUE_600);
            default:
                return new JButton("?");
        }
    }

    private JButton createPillButton(String text, IconUtils.IconType icon, Color bg, Color fg) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setIcon(IconUtils.getIcon(icon, 16, fg)); // Icon Left
        btn.setIconTextGap(8);
        btn.setOpaque(false);
        btn.setBorder(new EmptyBorder(6, 16, 6, 16));
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton createSquareButton(IconUtils.IconType iconType, Color bg, Color fg) {
        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8); // 8px radius
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        btn.setPreferredSize(new Dimension(32, 32));
        btn.setIcon(IconUtils.getIcon(iconType, 16, fg)); 
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return panel;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // panel.setBackground(table.getSelectionBackground());
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }
    
    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }
}
