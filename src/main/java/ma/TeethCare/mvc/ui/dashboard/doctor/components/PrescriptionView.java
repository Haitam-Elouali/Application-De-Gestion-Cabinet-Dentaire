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

import ma.TeethCare.mvc.dto.ordonnance.OrdonnanceDTO;
import ma.TeethCare.service.modules.dossierMedical.api.ordonnanceService;
import ma.TeethCare.service.modules.dossierMedical.impl.ordonnanceServiceImpl;
import ma.TeethCare.repository.mySQLImpl.OrdonnanceRepositoryImpl;

public class PrescriptionView extends JPanel implements TableActionCellRenderer.TableActionEvent {

    private final ordonnanceService service;
    private ModernTable table;
    private DefaultTableModel model;
    private List<OrdonnanceDTO> prescriptions;

    public PrescriptionView() {
        this.service = new ordonnanceServiceImpl(new OrdonnanceRepositoryImpl());
        
        setLayout(new BorderLayout());
        setOpaque(false); // Transparent
        setBorder(new EmptyBorder(24, 24, 24, 24));

        loadPrescriptions();
        initUI();
    }

    private void loadPrescriptions() {
        try {
            prescriptions = service.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur chargement ordonnances: " + e.getMessage());
            prescriptions = new ArrayList<>();
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
        ModernButton addBtn = new ModernButton("Ajouter une ordonnance", ModernButton.Variant.DEFAULT);
        addBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Ajoutez via le dossier ou la consultation."));
        topBar.add(addBtn, BorderLayout.EAST);

        card.add(topBar, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Patient", "Date", "Nb. Médicaments", "Actions"};
        
        model = new DefaultTableModel(columns, 0) {
             @Override
             public boolean isCellEditable(int row, int column) {
                 return column == 4;
             }
        };

        table = new ModernTable();
        table.setModel(model);
        refreshTable();
        
        table.setRowHeight(60);
        table.setShowGrid(false);
        
        // Actions
        ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer actionRenderer = new ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer(
            this,
            ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.VIEW_ICON,
            ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.PRINT,
            ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.EDIT,
            ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.DELETE
        );
        table.getColumnModel().getColumn(4).setCellRenderer(actionRenderer);
        table.getColumnModel().getColumn(4).setCellEditor(actionRenderer);
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        sp.getViewport().setBackground(Color.WHITE);
        
        card.add(sp, BorderLayout.CENTER);
        
        add(card, BorderLayout.CENTER);
    }
    
    private void refreshTable() {
        model.setRowCount(0);
        if (prescriptions == null) return;
        
        for (OrdonnanceDTO p : prescriptions) {
            String patientName = (p.getPatientNom() != null ? p.getPatientNom() : "") + " " + 
                                 (p.getPatientPrenom() != null ? p.getPatientPrenom() : "");
            
            model.addRow(new Object[]{
                p.getIdOrd(),
                patientName,
                p.getDate() != null ? p.getDate().toString() : "",
                "-", // Count not implemented
                ""
            });
        }
    }

    @Override
    public void onAction(int row, int column, TableActionCellRenderer.ActionType type) {
        if (row < 0) return;
        int modelRow = table.convertRowIndexToModel(row);
        OrdonnanceDTO p = prescriptions.get(modelRow);

        switch (type) {
             case DELETE:
                 int confirm = JOptionPane.showConfirmDialog(this, "Supprimer ?", "Confirmer", JOptionPane.YES_NO_OPTION);
                 if (confirm == JOptionPane.YES_OPTION) {
                     try {
                         service.delete(p.getIdOrd());
                         prescriptions.remove(modelRow);
                         refreshTable();
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 }
                 break;
             case PRINT:
                 JOptionPane.showMessageDialog(this, "Impression ordonnance id: " + p.getIdOrd());
                 break;
             default:
                 break;
        }
    }
}
