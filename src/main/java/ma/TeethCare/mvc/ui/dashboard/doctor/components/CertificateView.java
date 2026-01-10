package ma.TeethCare.mvc.ui.dashboard.doctor.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.data.ModernTable;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CertificateView extends JPanel {

    public CertificateView() {
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

        // Add Button
        ModernButton addBtn = new ModernButton("Ajouter un certificat", ModernButton.Variant.DEFAULT);
        topBar.add(addBtn, BorderLayout.EAST);

        card.add(topBar, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Patient", "Type", "Date", "Durée", "Actions"};
        Object[][] data = {
            {"1", "Dubois Marie", "Arrêt de travail", "10/12/2025", "3 jours", ""},
            {"2", "Martin Jean", "Certificat médical", "15/12/2025", "N/A", ""}
        };

        ModernTable table = new ModernTable();
        table.setModel(new DefaultTableModel(data, columns));
        table.setRowHeight(60);
        table.setShowGrid(false);
        
        // Type Renderer (Col 2)
        table.getColumnModel().getColumn(2).setCellRenderer(new ma.TeethCare.mvc.ui.palette.renderers.StatusPillRenderer());

        // Actions (Col 5)
        ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer actionRenderer = new ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer(
            ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.VIEW_ICON,
            ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.PRINT,
            ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.EDIT,
            ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.DELETE
        );
        table.getColumnModel().getColumn(5).setCellRenderer(actionRenderer);
        table.getColumnModel().getColumn(5).setCellEditor(actionRenderer);
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        sp.getViewport().setBackground(Color.WHITE);
        
        card.add(sp, BorderLayout.CENTER);
        
        add(card, BorderLayout.CENTER);
    }
}
