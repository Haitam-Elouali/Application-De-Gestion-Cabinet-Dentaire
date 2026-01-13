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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import ma.TeethCare.mvc.dto.dossierMedicale.DossierMedicaleDTO;
import ma.TeethCare.service.modules.dossierMedical.api.dossierMedicaleService;
import ma.TeethCare.service.modules.dossierMedical.impl.dossierMedicaleServiceImpl;
import ma.TeethCare.repository.mySQLImpl.DossierMedicaleRepositoryImpl;

public class MedicalRecordsView extends JPanel implements TableActionCellRenderer.TableActionEvent {

    private final ModernButton.Variant actionVariant;
    private final dossierMedicaleService dmService;
    private ModernTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;
    private List<DossierMedicaleDTO> dossiers;

    public MedicalRecordsView(ModernButton.Variant actionVariant) {
        this.actionVariant = actionVariant;
        this.dmService = new dossierMedicaleServiceImpl(new DossierMedicaleRepositoryImpl());
        
        setLayout(new BorderLayout());
        setOpaque(false); // Transparent to show Mint BG
        setBorder(new EmptyBorder(24, 24, 24, 24)); // Outer padding

        loadDossiers();
        initUI();
    }
    
    public MedicalRecordsView() {
        this(ModernButton.Variant.DEFAULT);
    }

    private void loadDossiers() {
        try {
            dossiers = dmService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur chargement dossiers: " + e.getMessage());
            dossiers = new ArrayList<>();
        }
    }

    private void initUI() {
        // Content Card
        RoundedPanel card = new RoundedPanel(12);
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(24, 24, 24, 24));

        // Top Bar (inside card)
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Search Field
        JPanel searchContainer = new JPanel(new BorderLayout());
        searchContainer.setOpaque(false);
        
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
        JTextField searchField = new JTextField("Rechercher un dossier...");
        searchField.setBorder(null);
        searchField.setOpaque(false);
        searchField.setForeground(Color.GRAY);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        searchFieldPanel.add(searchIcon, BorderLayout.WEST);
        searchFieldPanel.add(Box.createHorizontalStrut(8), BorderLayout.CENTER);
        searchFieldPanel.add(searchField, BorderLayout.CENTER);
        
        // Search Logic
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { filter(); }
            @Override public void removeUpdate(DocumentEvent e) { filter(); }
            @Override public void changedUpdate(DocumentEvent e) { filter(); }
            
            private void filter() {
                String text = searchField.getText();
                if (text.trim().length() == 0 || text.equals("Rechercher un dossier...")) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
        
        searchContainer.add(searchFieldPanel, BorderLayout.WEST);
        topBar.add(searchContainer, BorderLayout.WEST);

        // Add Button
        ModernButton addBtn = new ModernButton("Nouveau Dossier", this.actionVariant);
        // Add action if creating dossier standalone is supported. Usually created with Patient.
        addBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Créez un dossier via la gestion des patients."));
        
        topBar.add(addBtn, BorderLayout.EAST);

        card.add(topBar, BorderLayout.NORTH);

        // Table
        String[] columns = {"Patient", "Date Création", "Diagnostic Principal", "Traitement en cours", "Actions"};
        
        model = new DefaultTableModel(columns, 0) {
             @Override
             public boolean isCellEditable(int row, int column) {
                 return column == 4;
             }
        };

        table = new ModernTable();
        table.setModel(model);
        
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        sorter.setSortable(4, false);

        refreshTable();
        
        table.setRowHeight(60);
        table.setShowGrid(false);
        
        // Renderers - Using NEW TableActionCellRenderer with specific actions
        TableActionCellRenderer actionRenderer = new TableActionCellRenderer(
            this,
            TableActionCellRenderer.ActionType.VIEW_ICON,
            TableActionCellRenderer.ActionType.RDV_MANAGE,
            TableActionCellRenderer.ActionType.EDIT,
            TableActionCellRenderer.ActionType.DELETE
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
        if (dossiers == null) return;
        
        for (DossierMedicaleDTO d : dossiers) {
            String patientName = (d.getPatientNom() != null ? d.getPatientNom() : "") + " " + 
                                 (d.getPatientPrenom() != null ? d.getPatientPrenom() : "");
                                 
            model.addRow(new Object[]{
                patientName,
                d.getDateCreation() != null ? d.getDateCreation().toLocalDate() : "",
                d.getDiagnostic() != null ? d.getDiagnostic() : "-",
                d.getHistorique() != null ? d.getHistorique() : "-",
                ""
            });
        }
    }

    @Override
    public void onAction(int row, int column, TableActionCellRenderer.ActionType type) {
        // Implement actions if needed, e.g. View Details
        if (row < 0) return;
        int modelRow = table.convertRowIndexToModel(row);
        DossierMedicaleDTO d = dossiers.get(modelRow);
        
        switch (type) {
            case VIEW_ICON:
                // Open Medical Record Details
                JOptionPane.showMessageDialog(this, "Détails pour dossier: " + d.getId() + " (Patient: " + d.getPatientNom() + ")");
                break;
            case DELETE:
                // Confirm delete
                 int confirm = JOptionPane.showConfirmDialog(this, "Supprimer le dossier ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                 if (confirm == JOptionPane.YES_OPTION) {
                     try {
                         dmService.delete(d.getId());
                         dossiers.remove(modelRow);
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
