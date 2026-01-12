package ma.TeethCare.mvc.ui.palette.data;

import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class ModernTable extends JTable {

    public ModernTable() {
        setShowVerticalLines(false);
        setShowHorizontalLines(true);
        setGridColor(TailwindPalette.BORDER);
        setRowHeight(48); // Taller rows for modern feel
        setIntercellSpacing(new Dimension(0, 0));
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Header Style
        JTableHeader header = getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                l.setBackground(Color.WHITE);
                l.setForeground(TailwindPalette.MUTED_FOREGROUND); // Lighter text for headers
                l.setFont(new Font("Segoe UI", Font.BOLD, 13)); // Slightly smaller, uppercase feel often used
                l.setBorder(BorderFactory.createCompoundBorder(
                        new MatteBorder(0, 0, 1, 0, TailwindPalette.BORDER),
                        BorderFactory.createEmptyBorder(12, 16, 12, 16)
                ));
                return l;
            }
        });
        header.setPreferredSize(new Dimension(header.getWidth(), 48));

        // Cell Style
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                // Check if value implies a badge (String starting with [BADGE:COLOR])
                // Or simplified: custom logic. for now standard generic renderer.
                
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (c instanceof JComponent) {
                    ((JComponent) c).setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));
                }

                c.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                
                if (isSelected) {
                    c.setBackground(TailwindPalette.MUTED); 
                    c.setForeground(TailwindPalette.FOREGROUND);
                } else {
                    c.setBackground(Color.WHITE);
                    c.setForeground(TailwindPalette.FOREGROUND);
                }
                
                // Handle Badge Logic (Convention: Strings like "PAYÉ", "EN ATTENTE", "HIGH")
                if (value instanceof String) {
                    String s = (String) value;
                    if (isBadgeValue(s)) {
                        return createBadge(s, isSelected);
                    }
                }
                
                return c;
            }
        });
    }

    private boolean isBadgeValue(String s) {
        // Quick heuristics for demo purposes
        return s.equals("Confirmé") || s.equals("En attente") || s.equals("Annulé") || 
               s.equals("Payé") || s.equals("Non Payé") || 
               s.equals("Élevé") || s.equals("Moyen") || s.equals("Faible") ||
               s.equals("Actif") || s.equals("Succès") || s.equals("INFO") || s.equals("ERROR") || s.equals("WARN");
    }

    private Component createBadge(String text, boolean isSelected) {
        JLabel label = new JLabel(text);
        label.setOpaque(true);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        
        Color bg = TailwindPalette.GRAY_100;
        Color fg = TailwindPalette.GRAY_800;
        
        if (text.equalsIgnoreCase("Confirmé") || text.equalsIgnoreCase("Payé") || text.equalsIgnoreCase("Actif") || text.equalsIgnoreCase("Succès")) {
            bg = TailwindPalette.GREEN_100; fg = TailwindPalette.GREEN_800;
        } else if (text.equalsIgnoreCase("Annulé") || text.equalsIgnoreCase("Non Payé") || text.equalsIgnoreCase("Élevé") || text.equalsIgnoreCase("ERROR")) {
            bg = TailwindPalette.RED_100; fg = TailwindPalette.RED_800;
        } else if (text.equalsIgnoreCase("En attente") || text.equalsIgnoreCase("Moyen") || text.equalsIgnoreCase("WARN")) {
            bg = TailwindPalette.ORANGE_100; fg = TailwindPalette.ORANGE_800;
        } else if (text.equalsIgnoreCase("INFO") || text.equalsIgnoreCase("Automatique")) {
            bg = TailwindPalette.BLUE_100; fg = TailwindPalette.BLUE_800;
        }

        label.setBackground(isSelected ? bg.darker() : bg);
        label.setForeground(fg);
        
        // Padding wrapper
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 12)); // match cell padding
        p.setOpaque(false); // Transparent
        p.add(label);
        
        // Rounded Badge Border (using EmptyBorder for padding inside label is better)
        label.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12)); 
        // Note: JLabel setOpaque(true) draws a rectangle. Rounded corners require custom painting.
        // For standard JTable, this is "good enough" badge look or we override paintComponent of label.
        
        return p; // Return panel to control alignment/sizing
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return getModel().isCellEditable(row, column);
    }
}
