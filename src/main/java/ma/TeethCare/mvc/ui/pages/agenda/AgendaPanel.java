package ma.TeethCare.mvc.ui.pages.agenda;

import ma.TeethCare.mvc.controllers.modules.agenda.api.AgendaController;
import ma.TeethCare.service.modules.auth.dto.UserPrincipal;
import ma.TeethCare.service.modules.agenda.api.rdvService;
import ma.TeethCare.service.modules.patient.api.PatientService;
import ma.TeethCare.mvc.dto.rdv.RdvDTO;
import ma.TeethCare.mvc.dto.patient.PatientDTO;
import ma.TeethCare.mvc.ui.palette.fields.CustomTextField;
import ma.TeethCare.mvc.ui.palette.utils.UIConstants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AgendaPanel extends JPanel {

    private final AgendaController controller;
    private final rdvService service;
    private final PatientService patientService;
    private final UserPrincipal principal;

    private JTable table;
    private DefaultTableModel tableModel;
    private CustomTextField searchField;
    private List<RdvDTO> rdvs;

    public AgendaPanel(AgendaController controller, rdvService service, PatientService patientService, UserPrincipal principal) {
        this.controller = controller;
        this.service = service;
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

        JLabel title = new JLabel("Agenda - Rendez-vous");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(UIConstants.TEXT_DARK);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.setBackground(Color.WHITE);

        searchField = new CustomTextField("Rechercher...");
        searchField.setPreferredSize(new Dimension(200, 35));
        searchField.addActionListener(e -> filterData(searchField.getText()));

        JButton addButton = new JButton("Nouveau RDV");
        styleButton(addButton, UIConstants.ACCENT_GREEN);
        
        // TODO: Implement Add RDV Dialog
        addButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Fonctionnalité Nouveau RDV à implémenter (Dialog)"));

        actions.add(searchField);
        actions.add(addButton);

        header.add(title, BorderLayout.WEST);
        header.add(actions, BorderLayout.EAST);

        return header;
    }

    private JComponent createTableArea() {
        String[] columns = {"ID", "Date", "Heure", "Patient", "Motif", "Statut"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(40);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(245, 245, 245));
        table.setShowGrid(false);

        // Format header
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.LEFT);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        return scrollPane;
    }

    private void refreshData() {
        try {
            rdvs = service.findAll();
            filterData("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur chargement RDVs: " + e.getMessage());
        }
    }

    private void filterData(String query) {
        tableModel.setRowCount(0);
        if (rdvs == null) return;

        List<RdvDTO> filtered = rdvs.stream()
            // Expand filter logic as needed
            .collect(Collectors.toList());
            
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");

        for (RdvDTO r : rdvs) { // Using all for now, filter logic can be refined
            String patientName = "ID: " + r.getPatientId();
            try {
                if (r.getPatientId() != null) {
                    Optional<PatientDTO> p = patientService.findById(r.getPatientId());
                    if (p.isPresent()) {
                        patientName = p.get().getNom() + " " + p.get().getPrenom();
                    }
                }
            } catch (Exception ignored) {}

            tableModel.addRow(new Object[]{
                r.getIdRDV(),
                r.getDate() != null ? r.getDate().format(dateFmt) : "",
                r.getHeure() != null ? r.getHeure().format(timeFmt) : "",
                patientName,
                r.getMotif(),
                r.getStatut()
            });
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
