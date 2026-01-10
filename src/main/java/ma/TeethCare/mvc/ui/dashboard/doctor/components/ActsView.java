package ma.TeethCare.mvc.ui.dashboard.doctor.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.data.ModernTable;
import ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ActsView extends JPanel {

    public ActsView() {
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
        ModernButton addBtn = new ModernButton("Ajouter un acte", ModernButton.Variant.DEFAULT);
        topBar.add(addBtn, BorderLayout.EAST);

        card.add(topBar, BorderLayout.NORTH);

        // Table
        String[] columns = {"Code", "Libellé", "Catégorie", "Prix Base", "Remise", "Prix Final", "Actions"};
        Object[][] data = {
            {"DET001", "Détartrage complet", "Prévention", "500 MAD", "0%", "500 MAD", ""},
            {"SOI001", "Soin carie simple", "Soins", "800 MAD", "10%", "720 MAD", ""}
        };

        ModernTable table = new ModernTable();
        table.setModel(new DefaultTableModel(data, columns));
        table.setRowHeight(60);
        table.setShowGrid(false);
        
        // Add Action Renderer logic
        ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer actionRenderer = new ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer(
            ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.VIEW_ICON,
            ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.PERCENTAGE,
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
