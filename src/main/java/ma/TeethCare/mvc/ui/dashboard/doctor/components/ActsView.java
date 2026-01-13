package ma.TeethCare.mvc.ui.dashboard.doctor.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.data.ModernTable;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;
import ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import ma.TeethCare.mvc.dto.actes.ActesDTO;
import ma.TeethCare.service.modules.dossierMedical.api.actesService;
import ma.TeethCare.service.modules.dossierMedical.impl.actesServiceImpl;
import ma.TeethCare.repository.mySQLImpl.ActesRepositoryImpl;

public class ActsView extends JPanel implements TableActionCellRenderer.TableActionEvent {

    private final actesService service; // Note: Interface name in code was actsService (lowercase a?) Checking impl... Interface was not read, but impl uses `actesService` with lowercase 'a' in `implements`.
    // Wait, step 4526 showed `public class actesServiceImpl implements actesService`.
    // So interface is `actesService`.
    
    private ModernTable table;
    private DefaultTableModel model;
    private List<ActesDTO> acts;

    public ActsView() {
        this.service = new actesServiceImpl(new ActesRepositoryImpl());
        
        setLayout(new BorderLayout());
        setOpaque(false); // Transparent
        setBorder(new EmptyBorder(24, 24, 24, 24));

        loadActs();
        initUI();
    }

    private void loadActs() {
        try {
            acts = service.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur chargement actes: " + e.getMessage());
            acts = new ArrayList<>();
        }
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
        // Implement Add
        topBar.add(addBtn, BorderLayout.EAST);

        card.add(topBar, BorderLayout.NORTH);

        // Table
        String[] columns = {"Code", "Libellé", "Catégorie", "Prix Base", "Remise", "Prix Final", "Actions"};
        
        model = new DefaultTableModel(columns, 0) {
             @Override
             public boolean isCellEditable(int row, int column) {
                 return column == 6;
             }
        };

        table = new ModernTable();
        table.setModel(model);
        refreshTable();
        
        table.setRowHeight(60);
        table.setShowGrid(false);
        
        // Add Action Renderer logic
        TableActionCellRenderer actionRenderer = new TableActionCellRenderer(
            this,
            TableActionCellRenderer.ActionType.VIEW_ICON,
            TableActionCellRenderer.ActionType.PERCENTAGE,
            TableActionCellRenderer.ActionType.EDIT,
            TableActionCellRenderer.ActionType.DELETE
        );
        table.getColumnModel().getColumn(6).setCellRenderer(actionRenderer);
        table.getColumnModel().getColumn(6).setCellEditor(actionRenderer);
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        sp.getViewport().setBackground(Color.WHITE);
        
        card.add(sp, BorderLayout.CENTER);
        
        add(card, BorderLayout.CENTER);
    }
    
    private void refreshTable() {
        model.setRowCount(0);
        if (acts == null) return;
        
        for (ActesDTO a : acts) {
            model.addRow(new Object[]{
                a.getCode() != null ? a.getCode() : "",
                a.getNom(),
                a.getCategorie(),
                a.getPrix() != null ? a.getPrix() + " MAD" : "",
                "0%", // Placeholder
                a.getPrix() != null ? a.getPrix() + " MAD" : "",
                ""
            });
        }
    }

    @Override
    public void onAction(int row, int column, TableActionCellRenderer.ActionType type) {
        if (row < 0) return;
        int modelRow = table.convertRowIndexToModel(row);
        ActesDTO a = acts.get(modelRow);
        
        switch (type) {
            case DELETE:
                int confirm = JOptionPane.showConfirmDialog(this, "Supprimer ?", "Confirmer", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        service.delete(a.getId());
                        acts.remove(modelRow);
                        refreshTable();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }
}
