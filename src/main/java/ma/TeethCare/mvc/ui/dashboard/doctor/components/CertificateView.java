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

import ma.TeethCare.mvc.dto.certificat.CertificatDTO;
import ma.TeethCare.service.modules.dossierMedical.api.certificatService;
import ma.TeethCare.service.modules.dossierMedical.impl.certificatServiceImpl;
import ma.TeethCare.repository.mySQLImpl.CertificatRepositoryImpl;

public class CertificateView extends JPanel implements TableActionCellRenderer.TableActionEvent {

    private final certificatService service;
    private ModernTable table;
    private DefaultTableModel model;
    private List<CertificatDTO> certificats;

    public CertificateView() {
        this.service = new certificatServiceImpl(new CertificatRepositoryImpl());
        
        setLayout(new BorderLayout());
        setOpaque(false); // Transparent
        setBorder(new EmptyBorder(24, 24, 24, 24));

        loadCertificats();
        initUI();
    }

    private void loadCertificats() {
        try {
            certificats = service.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur chargement certificats: " + e.getMessage());
            certificats = new ArrayList<>();
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
        ModernButton addBtn = new ModernButton("Ajouter un certificat", ModernButton.Variant.DEFAULT);
        addBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Ajoutez via consultation."));
        topBar.add(addBtn, BorderLayout.EAST);

        card.add(topBar, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Patient", "Type", "Date", "Durée", "Actions"};
        
        model = new DefaultTableModel(columns, 0) {
             @Override
             public boolean isCellEditable(int row, int column) {
                 return column == 5;
             }
        };

        table = new ModernTable();
        table.setModel(model);
        refreshTable();
        
        table.setRowHeight(60);
        table.setShowGrid(false);
        
        // Type Renderer (Col 2)
        table.getColumnModel().getColumn(2).setCellRenderer(new ma.TeethCare.mvc.ui.palette.renderers.StatusPillRenderer());

        // Actions (Col 5)
        TableActionCellRenderer actionRenderer = new TableActionCellRenderer(
            this,
            TableActionCellRenderer.ActionType.VIEW_ICON,
            TableActionCellRenderer.ActionType.PRINT,
            TableActionCellRenderer.ActionType.EDIT,
            TableActionCellRenderer.ActionType.DELETE
        );
        table.getColumnModel().getColumn(5).setCellRenderer(actionRenderer);
        table.getColumnModel().getColumn(5).setCellEditor(actionRenderer);
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        sp.getViewport().setBackground(Color.WHITE);
        
        card.add(sp, BorderLayout.CENTER);
        
        add(card, BorderLayout.CENTER);
    }
    
    private void refreshTable() {
        model.setRowCount(0);
        if (certificats == null) return;
        
        for (CertificatDTO c : certificats) {
            String patientName = (c.getPatientNom() != null ? c.getPatientNom() : "") + " " + 
                                 (c.getPatientPrenom() != null ? c.getPatientPrenom() : "");
            String dureeStr = c.getDuree() != null ? c.getDuree() + " jours" : "N/A";
            
            model.addRow(new Object[]{
                c.getId(),
                patientName,
                c.getType() != null ? c.getType() : "Certificat",
                c.getDateEmission() != null ? c.getDateEmission().toString() : "",
                dureeStr,
                ""
            });
        }
    }

    @Override
    public void onAction(int row, int column, TableActionCellRenderer.ActionType type) {
        if (row < 0) return;
        int modelRow = table.convertRowIndexToModel(row);
        CertificatDTO c = certificats.get(modelRow);

        switch (type) {
             case DELETE:
                 int confirm = JOptionPane.showConfirmDialog(this, "Supprimer ?", "Confirmer", JOptionPane.YES_NO_OPTION);
                 if (confirm == JOptionPane.YES_OPTION) {
                     try {
                         service.delete(c.getId());
                         certificats.remove(modelRow);
                         refreshTable();
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 }
                 break;
             case PRINT:
                 JOptionPane.showMessageDialog(this, "Impression certificat: " + c.getType());
                 break;
             default:
                 break;
        }
    }
}
