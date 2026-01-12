package ma.TeethCare.mvc.ui.palette.renderers;

import ma.TeethCare.mvc.ui.palette.utils.IconUtils;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

public class TableActionCellRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

    public interface TableActionEvent {
        void onAction(int row, int column, ActionType type);
    }

    public enum ActionType {
        VIEW_FULL, VIEW_ICON, EDIT, DELETE, PRINT, BILL, LINK, PERCENTAGE, RDV_MANAGE, SUSPEND
    }

    private final JPanel panel;
    private final List<JButton> buttons = new ArrayList<>();
    private TableActionEvent event;
    private JTable table;

    public TableActionCellRenderer(TableActionEvent event, ActionType... actions) {
        this.event = event;
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 4));
        panel.setOpaque(false); // Transparent
        // Removed border to let table grid show naturally if transparent? 
        // Or if we need padding:
        panel.setBorder(null); // FlowLayout handles spacing, or add EmptyBorder if needed

        if (actions.length == 0) {
            actions = new ActionType[]{ActionType.VIEW_FULL, ActionType.EDIT, ActionType.DELETE};
        }

        for (ActionType type : actions) {
            JButton btn = createButton(type);
            buttons.add(btn);
            panel.add(btn);
            
            btn.addActionListener(e -> {
                if (event != null && table != null) {
                    int row = table.getEditingRow();
                    int col = table.getEditingColumn();
                    fireEditingStopped(); // Stop editing BEFORE action (especially if action is modal)
                    event.onAction(row, col, type);
                }
            });
        }
    }

    public TableActionCellRenderer() {
        this(null, ActionType.VIEW_FULL, ActionType.EDIT, ActionType.DELETE);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return panel;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.table = table;
        panel.setOpaque(false); // Transparent
        // panel.setBackground(Color.WHITE); // Removed
        
        // Forward the click if it started editing
        // This is a simplified approach. Ideally use a MouseListener on the table
        // that delegates to the buttons, but this works if the user clicked exactly on a button.
        // However, standard JTable absorbs the first click.
        
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
        btn.setIcon(IconUtils.getIcon(icon, 16, fg)); 
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
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8); 
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
}
