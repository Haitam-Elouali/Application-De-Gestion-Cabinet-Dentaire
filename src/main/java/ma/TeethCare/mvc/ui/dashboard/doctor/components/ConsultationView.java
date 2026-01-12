package ma.TeethCare.mvc.ui.dashboard.doctor.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.data.ModernTable;
import ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ConsultationView extends JPanel {

    public ConsultationView() {
        setLayout(new BorderLayout());
        setOpaque(false); // Transparent
        setBorder(new EmptyBorder(24, 24, 24, 24));

        initUI();
    }

    private void initUI() {
        // Content Wrapper
        ma.TeethCare.mvc.ui.palette.containers.RoundedPanel card = new ma.TeethCare.mvc.ui.palette.containers.RoundedPanel(12);
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(24, 24, 24, 24));

        // Top Bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Add Button (Right aligned)
        ModernButton addBtn = new ModernButton("Ajouter une consultation", ModernButton.Variant.DEFAULT);
        topBar.add(addBtn, BorderLayout.EAST);

        card.add(topBar, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Patient", "Date", "Heure", "Statut", "Motif", "Actions"};
        Object[][] data = {
            {"1", "Dubois Marie", "10/11/2025", "09:00", "Terminée", "Douleur dentaire", ""},
            {"2", "Martin Jean", "12/11/2025", "10:30", "Planifiée", "Contrôle de routine", ""}
        };

        ModernTable table = new ModernTable();
        table.setModel(new DefaultTableModel(data, columns));
        table.setRowHeight(60);
        table.setShowGrid(false);
        
        // Actions
        // Status Renderer (Col 4)
        table.getColumnModel().getColumn(4).setCellRenderer(new ma.TeethCare.mvc.ui.palette.renderers.StatusPillRenderer());

        // Actions (Col 6)
        ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer actionRenderer = new ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer(
            null,
            ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.VIEW_ICON,
            TableActionCellRenderer.ActionType.BILL,
            TableActionCellRenderer.ActionType.LINK,
            ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.EDIT,
            ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.DELETE
        );
        table.getColumnModel().getColumn(6).setCellRenderer(actionRenderer);
        table.getColumnModel().getColumn(6).setCellEditor(actionRenderer);
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        sp.getViewport().setBackground(Color.WHITE);
        
        card.add(sp, BorderLayout.CENTER);
        
        add(card, BorderLayout.CENTER);
    }
}
