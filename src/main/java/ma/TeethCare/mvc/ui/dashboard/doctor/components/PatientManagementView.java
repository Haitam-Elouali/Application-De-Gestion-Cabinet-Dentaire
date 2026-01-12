package ma.TeethCare.mvc.ui.dashboard.doctor.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.containers.RoundedPanel;
import ma.TeethCare.mvc.ui.palette.data.ModernTable;
import ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer;
import ma.TeethCare.mvc.ui.palette.utils.IconUtils;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import ma.TeethCare.mvc.dto.antecedent.AntecedentDTO;

import ma.TeethCare.mvc.ui.pages.patient.PatientDialog;
import ma.TeethCare.mvc.ui.pages.patient.MedicalRecordDetailDialog;
import ma.TeethCare.mvc.ui.pages.patient.DeleteConfirmationDialog;
import ma.TeethCare.mvc.dto.patient.PatientDTO;
import java.util.ArrayList;

public class PatientManagementView extends JPanel implements TableActionCellRenderer.TableActionEvent {

    private final ModernButton.Variant actionVariant;
    private ModernTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;
    private List<PatientDTO> patients;

    public PatientManagementView(ModernButton.Variant actionVariant) {
        this.actionVariant = actionVariant;
        setLayout(new BorderLayout());
        setOpaque(false); // Transparent
        setBorder(new EmptyBorder(24, 24, 24, 24)); // Outer padding

        loadMockData(); // Initialize data
        initUI();
    }
    
    // Default constructor for compatibility (defaults to Blue/DEFAULT)
    public PatientManagementView() {
        this(ModernButton.Variant.DEFAULT);
    }

    private void initUI() {
        // Content Card
        RoundedPanel card = new RoundedPanel(12);
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(24, 24, 24, 24));

        // Top Bar: Search + Add Button
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Search Field Container
        JPanel searchContainer = new JPanel(new BorderLayout());
        searchContainer.setOpaque(false);
        
        // Custom search layout
        JPanel searchFieldPanel = new JPanel(new BorderLayout()) {
             @Override
             protected void paintComponent(Graphics g) {
                  Graphics2D g2 = (Graphics2D)g;
                  g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                  g2.setColor(Color.WHITE);
                  g2.fillRoundRect(0,0,getWidth(), getHeight(), 8, 8);
                  g2.setColor(TailwindPalette.BORDER);
                  g2.drawRoundRect(0,0,getWidth()-1, getHeight()-1, 8, 8);
             }
        };
        searchFieldPanel.setOpaque(false);
        searchFieldPanel.setPreferredSize(new Dimension(300, 40));
        searchFieldPanel.setBorder(new EmptyBorder(0, 12, 0, 12));
        
        JLabel searchIcon = new JLabel(IconUtils.getIcon(IconUtils.IconType.SEARCH, 18, Color.GRAY));
        JTextField searchField = new JTextField("Rechercher un patient...");
        searchField.setBorder(null);
        searchField.setOpaque(false);
        searchField.setForeground(Color.GRAY);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        searchFieldPanel.add(searchIcon, BorderLayout.WEST);
        searchFieldPanel.add(Box.createHorizontalStrut(8), BorderLayout.CENTER);
        searchFieldPanel.add(searchField, BorderLayout.CENTER);
        
        // Placeholder Logic
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Rechercher un patient...")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(Color.GRAY);
                    searchField.setText("Rechercher un patient...");
                }
            }
        });
        
        searchContainer.add(searchFieldPanel, BorderLayout.WEST);
        
        // Search Logic
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { filter(); }
            @Override public void removeUpdate(DocumentEvent e) { filter(); }
            @Override public void changedUpdate(DocumentEvent e) { filter(); }
            
            private void filter() {
                String text = searchField.getText();
                if (text.trim().length() == 0 || text.equals("Rechercher un patient...")) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        topBar.add(searchContainer, BorderLayout.WEST);

        // Add Button
        ModernButton addBtn = new ModernButton("+ Nouveau Patient", this.actionVariant);
        addBtn.addActionListener(e -> onAdd());
        topBar.add(addBtn, BorderLayout.EAST);

        card.add(topBar, BorderLayout.NORTH);

        // Table
        String[] columns = {"Nom", "Prénom", "Date Naissance", "Téléphone", "Email", "Antécédents", "Actions"};
        
        model = new DefaultTableModel(columns, 0) {
             @Override
             public boolean isCellEditable(int row, int column) {
                 return column == 6;
             }
        };

        table = new ModernTable();
        table.setModel(model);
        
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // Don't sort the Actions column
        sorter.setSortable(6, false);

        refreshTable(); // Populate table from patients list
        
        table.setRowHeight(60);
        table.setShowGrid(false);
        
        // Renderers 
        // Antecedents (Col 5) - Use StatusPillRenderer (or similar badge logic)
        table.getColumnModel().getColumn(5).setCellRenderer(new ma.TeethCare.mvc.ui.palette.renderers.StatusPillRenderer());
        
        // Actions (Col 6) - Use VIEW_ICON, EDIT, DELETE
        TableActionCellRenderer actionRenderer = new TableActionCellRenderer(
             this,
             TableActionCellRenderer.ActionType.VIEW_ICON,
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

    private void loadMockData() {
        patients = new ArrayList<>();

        PatientDTO p1 = new PatientDTO();
        p1.setId(1L);
        p1.setNom("Dupont");
        p1.setPrenom("Jean");
        p1.setDateNaissance(LocalDate.of(1980, 5, 15));
        p1.setTelephone("0601020304");
        p1.setEmail("jean.dupont@email.com");
        p1.setAdresse("123 Rue Principale, Casablanca");
        p1.setCin("BH12345");

        List<AntecedentDTO> a1 = new ArrayList<>();
        a1.add(AntecedentDTO.builder().categorie("Maladie Chronique").nom("Diabète").niveauDeRisque("Moyen").build());
        p1.setAntecedents(a1);

        PatientDTO p2 = new PatientDTO();
        p2.setId(2L);
        p2.setNom("Martin");
        p2.setPrenom("Sophie");
        p2.setDateNaissance(LocalDate.of(1992, 11, 20));
        p2.setTelephone("0605060708");
        p2.setEmail("sophie.martin@email.com");
        p2.setAdresse("45 Avenue Hassan II, Rabat");
        p2.setCin("RB98765");

        List<AntecedentDTO> a2 = new ArrayList<>();
        a2.add(AntecedentDTO.builder().categorie("Allergie").nom("Pénicilline").niveauDeRisque("Elevé").build());
        p2.setAntecedents(a2);

        patients.add(p1);
        patients.add(p2);
    }

    private void refreshTable() {
        model.setRowCount(0);
        for (PatientDTO p : patients) {
            String antecedentsStr = "";
            if (p.getAntecedents() != null && !p.getAntecedents().isEmpty()) {
                antecedentsStr = p.getAntecedents().get(0).getNom(); // Show first one
                if (p.getAntecedents().size() > 1) antecedentsStr += " + " + (p.getAntecedents().size() - 1);
            }

            model.addRow(new Object[]{
                p.getNom(),
                p.getPrenom(),
                p.getDateNaissance() != null ? p.getDateNaissance().toString() : "",
                p.getTelephone(),
                p.getEmail(),
                antecedentsStr,
                "" // Action placeholder
            });
        }
    }

    private void onAdd() {
        PatientDialog dialog = new PatientDialog(SwingUtilities.getWindowAncestor(this), null);
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            PatientDTO newPatient = dialog.getPatient();
            // Simulate ID generation
            newPatient.setId((long) (patients.size() + 1));
            patients.add(newPatient);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Patient ajouté avec succès !");
        }
    }

    @Override
    public void onAction(int row, int column, TableActionCellRenderer.ActionType type) {
        switch (type) {
            case EDIT:
                onEdit(row);
                break;
            case DELETE:
                onDelete(row);
                break;
            case VIEW_ICON:
                onView(row);
                break;
            default:
                break;
        }
    }

    private void onEdit(int row) {
        if (row < 0) return;
        int modelRow = table.convertRowIndexToModel(row);
        PatientDTO selectedPatient = patients.get(modelRow);

        PatientDialog dialog = new PatientDialog(SwingUtilities.getWindowAncestor(this), selectedPatient);
        dialog.setVisible(true);
         if (dialog.isSaved()) {
            // Since selectedPatient is a reference to the object in the list,
            // changes in the dialog (which updates the DTO) are already applied.
            // We just need to refresh the table display.
            refreshTable();
            JOptionPane.showMessageDialog(this, "Patient modifié avec succès !");
        }
    }

    private void onDelete(int row) {
        if (row < 0) return;
        int modelRow = table.convertRowIndexToModel(row);
        PatientDTO p = patients.get(modelRow);

        DeleteConfirmationDialog dialog = new DeleteConfirmationDialog(SwingUtilities.getWindowAncestor(this), p.getNom() + " " + p.getPrenom());
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            patients.remove(modelRow);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Patient supprimé.");
        }
    }

    private void onView(int row) {
        if (row < 0) return;
        int modelRow = table.convertRowIndexToModel(row);
        PatientDTO p = patients.get(modelRow);

        MedicalRecordDetailDialog dialog = new MedicalRecordDetailDialog(SwingUtilities.getWindowAncestor(this), p);
        dialog.setVisible(true);
    }
}


