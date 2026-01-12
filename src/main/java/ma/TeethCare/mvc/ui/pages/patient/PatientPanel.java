package ma.TeethCare.mvc.ui.pages.patient;

import ma.TeethCare.mvc.controllers.modules.patient.api.PatientController;
import ma.TeethCare.service.modules.auth.dto.UserPrincipal;
import ma.TeethCare.service.modules.patient.api.PatientService;
import ma.TeethCare.mvc.dto.patient.PatientDTO;
import ma.TeethCare.mvc.ui.palette.fields.CustomTextField;
import ma.TeethCare.mvc.ui.palette.utils.UIConstants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

public class PatientPanel extends JPanel {

    private final PatientController controller;
    private final PatientService patientService;
    private final UserPrincipal principal;

    private JTable table;
    private DefaultTableModel tableModel;
    private CustomTextField searchField;
    private List<PatientDTO> patients;

    public PatientPanel(PatientController controller, PatientService patientService, UserPrincipal principal) {
        this.controller = controller;
        this.patientService = patientService;
        this.principal = principal;

        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(createHeader(), BorderLayout.NORTH);
        add(createTableArea(), BorderLayout.CENTER);

        refreshData();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);

        JLabel title = new JLabel("Gestion des Patients");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(UIConstants.TEXT_DARK);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.setBackground(Color.WHITE);

        searchField = new CustomTextField("Rechercher...");
        searchField.setPreferredSize(new Dimension(200, 35));
        searchField.addActionListener(e -> filterData(searchField.getText()));

        JButton addButton = new JButton("Nouveau Patient");
        styleButton(addButton, UIConstants.ACCENT_GREEN);
        addButton.addActionListener(e -> openPatientDialog(null));

        JButton deleteButton = new JButton("Supprimer");
        styleButton(deleteButton, new Color(220, 53, 69));
        deleteButton.addActionListener(e -> deleteSelectedPatient());

        actions.add(searchField);
        actions.add(addButton);
        actions.add(deleteButton);

        header.add(title, BorderLayout.WEST);
        header.add(actions, BorderLayout.EAST);

        return header;
    }

    private JComponent createTableArea() {
        String[] columns = {"ID", "Nom", "Prénom", "CIN", "Téléphone", "Email", "Actions"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only Actions column endsitable
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(40);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(245, 245, 245));
        table.setSelectionBackground(new Color(230, 240, 255));
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        // Format header
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        
        // Setup Actions Column
        // Use TableActionCellRenderer as both Renderer and Editor
        ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.TableActionEvent event = (row, col, type) -> {
            Long id = (Long) table.getModel().getValueAt(row, 0); // Use Model index if sorted? No, row passed is usually view row. Check impl.
            // Actually AbstractCellEditor doesn't know view vs model index easily unless passed. 
            // We'll trust getEditingRow() returns view row, so we need to map to model if sorted.
            // For now, assuming unsorted or using simple table.
            
            if (row >= 0 && row < table.getRowCount()) { 
                id = (Long) table.getValueAt(row, 0);
            }
            
            if (type == ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.EDIT) {
                editPatient(id);
            } else if (type == ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.DELETE) {
                // Delete logic
                if (id != null) {
                   // We need to call a method that confirms and deletes. 
                   // But "deleteSelectedPatient" used table.getSelectedRow(). Here we target specific row.
                   deletePatientById(id, (String) table.getValueAt(row, 1) + " " + table.getValueAt(row, 2));
                }
            } else if (type == ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.VIEW_FULL) {
                 // Open dossier?
                 JOptionPane.showMessageDialog(this, "Voir Dossier (Not Implemented)");
            }
        };
        
        ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer actionRenderer = 
            new ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer(event, 
                ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.VIEW_FULL,
                ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.EDIT,
                ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.DELETE);

        table.getColumnModel().getColumn(6).setCellRenderer(actionRenderer);
        table.getColumnModel().getColumn(6).setCellEditor(actionRenderer);
        table.getColumnModel().getColumn(6).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        return scrollPane;
    }

    private void refreshData() {
        try {
            patients = patientService.findAll();
            filterData("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des patients: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filterData(String query) {
        tableModel.setRowCount(0);
        if (patients == null) return;

        List<PatientDTO> filtered = patients.stream()
            .filter(p -> query.isEmpty() || 
                (p.getNom() != null && p.getNom().toLowerCase().contains(query.toLowerCase())) ||
                (p.getPrenom() != null && p.getPrenom().toLowerCase().contains(query.toLowerCase())) ||
                (p.getCin() != null && p.getCin().toLowerCase().contains(query.toLowerCase())))
            .collect(Collectors.toList());

        for (PatientDTO p : filtered) {
            tableModel.addRow(new Object[]{
                p.getId(),
                p.getNom(),
                p.getPrenom(),
                p.getCin(),
                p.getTelephone(),
                p.getEmail(),
                "" // Actions placeholder
            });
        }
    }

    private void editPatient(Long id) {
        try {
            PatientDTO dto = patients.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
            if (dto != null) {
                openPatientDialog(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteSelectedPatient() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un patient à supprimer.");
            return;
        }
        Long id = (Long) table.getValueAt(row, 0);
        String nom = (String) table.getValueAt(row, 1) + " " + table.getValueAt(row, 2);
        deletePatientById(id, nom);
    }

    private void deletePatientById(Long id, String nom) {
        DeleteConfirmationDialog dialog = new DeleteConfirmationDialog(SwingUtilities.getWindowAncestor(this), nom);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            try {
                patientService.delete(id);
                refreshData();
                JOptionPane.showMessageDialog(this, "Patient supprimé avec succès.");
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(this, "Erreur lors de la suppression: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openPatientDialog(PatientDTO patient) {
        PatientDialog dialog = new PatientDialog(SwingUtilities.getWindowAncestor(this), patient);
        dialog.setVisible(true);
        
        if (dialog.isSaved()) {
            try {
                PatientDTO dto = dialog.getPatient();
                if (dto.getId() == null) {
                    patientService.create(dto);
                } else {
                    patientService.update(dto);
                }
                refreshData();
                JOptionPane.showMessageDialog(this, "Patient enregistré avec succès.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void styleButton(JButton btn, Color bgColor) {
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
