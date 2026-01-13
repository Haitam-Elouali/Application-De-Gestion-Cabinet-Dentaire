package ma.TeethCare.mvc.ui.dashboard.doctor.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.data.ModernTable;
import ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import ma.TeethCare.mvc.dto.consultation.ConsultationDTO;
import ma.TeethCare.service.modules.consultation.api.ConsultationService;
import ma.TeethCare.service.modules.consultation.impl.ConsultationServiceImpl;
import ma.TeethCare.repository.mySQLImpl.ConsultationRepositoryImpl;

public class ConsultationView extends JPanel implements TableActionCellRenderer.TableActionEvent {

    private final ConsultationService consultationService;
    private ModernTable table;
    private DefaultTableModel model;
    private List<ConsultationDTO> consultations;

    public ConsultationView() {
        this.consultationService = new ConsultationServiceImpl(new ConsultationRepositoryImpl());
        
        setLayout(new BorderLayout());
        setOpaque(false); // Transparent
        setBorder(new EmptyBorder(24, 24, 24, 24));

        loadConsultations();
        initUI();
    }

    private void loadConsultations() {
        try {
            consultations = consultationService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur chargement consultations: " + e.getMessage());
            consultations = new ArrayList<>();
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

        // Add Button (Right aligned)
        ModernButton addBtn = new ModernButton("Ajouter une consultation", ModernButton.Variant.DEFAULT);
        addBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Utilisez le dossier médical pour ajouter une consultation."));
        topBar.add(addBtn, BorderLayout.EAST);

        card.add(topBar, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Patient", "Date", "Heure", "Statut", "Motif", "Actions"};
        
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
        
        // Actions
        // Status Renderer (Col 4)
        table.getColumnModel().getColumn(4).setCellRenderer(new ma.TeethCare.mvc.ui.palette.renderers.StatusPillRenderer());

        // Actions (Col 6)
        TableActionCellRenderer actionRenderer = new TableActionCellRenderer(
            this,
            TableActionCellRenderer.ActionType.VIEW_ICON,
            TableActionCellRenderer.ActionType.BILL,
            TableActionCellRenderer.ActionType.LINK,
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
        if (consultations == null) return;
        
        for (ConsultationDTO c : consultations) {
            String patientName = (c.getPatientNom() != null ? c.getPatientNom() : "") + " " + 
                                 (c.getPatientPrenom() != null ? c.getPatientPrenom() : "");
            
            model.addRow(new Object[]{
                c.getId(),
                patientName,
                c.getDate() != null ? c.getDate().toString() : "",
                "-", // Heure not strictly modeled yet
                c.getStatut() != null ? c.getStatut() : "PLANIFIE",
                c.getNotes() != null ? c.getNotes() : (c.getDiagnostique() != null ? c.getDiagnostique() : ""),
                ""
            });
        }
    }

    @Override
    public void onAction(int row, int column, TableActionCellRenderer.ActionType type) {
        if (row < 0) return;
        int modelRow = table.convertRowIndexToModel(row);
        ConsultationDTO c = consultations.get(modelRow);

        switch(type) {
            case DELETE:
                 int confirm = JOptionPane.showConfirmDialog(this, "Supprimer ?", "Confirmer", JOptionPane.YES_NO_OPTION);
                 if (confirm == JOptionPane.YES_OPTION) {
                     try {
                         consultationService.delete(c.getId());
                         consultations.remove(modelRow);
                         refreshTable();
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 }
                 break;
            default:
                 JOptionPane.showMessageDialog(this, "Action non implémentée pour: " + type);
                 break;
        }
    }
}
