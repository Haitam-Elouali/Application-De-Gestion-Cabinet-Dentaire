package ma.TeethCare.mvc.ui.pages.patient;

import ma.TeethCare.mvc.dto.patient.PatientDTO;
import ma.TeethCare.mvc.dto.antecedent.AntecedentDTO;
import ma.TeethCare.mvc.ui.palette.fields.CustomTextField;
import ma.TeethCare.mvc.ui.palette.utils.UIConstants;
import ma.TeethCare.common.enums.niveauDeRisque;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PatientDialog extends JDialog {

    private final PatientDTO patient;
    private boolean saved = false;
    
    private CustomTextField tfNom;
    private CustomTextField tfPrenom;
    private CustomTextField tfDateNaissance;
    private CustomTextField tfTel;
    private CustomTextField tfEmail;
    private CustomTextField tfAdresse;
    private CustomTextField tfCin;
    
    // Antecedents
    private CustomTextField tfAntType;
    private CustomTextField tfAntDesc;
    private JComboBox<String> cbAntRisque;
    private DefaultTableModel antTableModel;
    private List<AntecedentDTO> currentAntecedents;

    public PatientDialog(Window owner, PatientDTO patient) {
        super(owner, patient == null ? "Ajouter un patient" : "Modifier Patient", ModalityType.APPLICATION_MODAL);
        this.patient = patient != null ? patient : PatientDTO.builder().antecedents(new ArrayList<>()).build();
        this.currentAntecedents = this.patient.getAntecedents() != null ? new ArrayList<>(this.patient.getAntecedents()) : new ArrayList<>();
        
        initComponents();
        setSize(500, 700);
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Form Fields
        JPanel formGrid = new JPanel(new GridLayout(0, 2, 15, 15));
        formGrid.setBackground(Color.WHITE);

        tfNom = new CustomTextField("Nom");
        tfPrenom = new CustomTextField("Prénom");
        tfDateNaissance = new CustomTextField("yyyy-MM-dd");
        tfTel = new CustomTextField("Téléphone");
        tfEmail = new CustomTextField("Email");
        tfCin = new CustomTextField("CIN");
        tfAdresse = new CustomTextField("Adresse");
        
        // Populate if edit
        if (patient.getId() != null) {
            tfNom.setText(patient.getNom());
            tfPrenom.setText(patient.getPrenom());
            if (patient.getDateNaissance() != null) 
                 tfDateNaissance.setText(patient.getDateNaissance().toString()); 
            tfTel.setText(patient.getTelephone());
            tfEmail.setText(patient.getEmail());
            tfAdresse.setText(patient.getAdresse());
            tfCin.setText(patient.getCin());
        }

        addLabeledField(formGrid, "Nom", tfNom);
        addLabeledField(formGrid, "Prénom", tfPrenom);
        addLabeledField(formGrid, "Date de Naissance", tfDateNaissance);
        addLabeledField(formGrid, "Téléphone", tfTel);
        addLabeledField(formGrid, "Email", tfEmail);
        addLabeledField(formGrid, "CIN", tfCin);
        
        // Address full width? 
        JPanel addressPanel = new JPanel(new BorderLayout());
        addressPanel.setBackground(Color.WHITE);
        addressPanel.setBorder(new EmptyBorder(5,0,10,0));
        addressPanel.add(new JLabel("Adresse"), BorderLayout.NORTH);
        addressPanel.add(tfAdresse, BorderLayout.CENTER);

        // Antecedents Section
        JPanel antPanel = new JPanel(new BorderLayout(10, 10));
        antPanel.setBackground(new Color(250, 252, 255));
        antPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), "Antécédents Médicaux"));
        
        JPanel antForm = new JPanel(new GridLayout(1, 4, 5, 5));
        antForm.setOpaque(false);
        tfAntType = new CustomTextField("Type (ex: Allergie)");
        tfAntDesc = new CustomTextField("Description");
        cbAntRisque = new JComboBox<>(new String[]{"Faible", "Moyen", "Elevé"});
        JButton btnAddAnt = new JButton("+");
        
        btnAddAnt.addActionListener(e -> addAntecedent());

        antForm.add(tfAntType);
        antForm.add(tfAntDesc);
        antForm.add(cbAntRisque);
        antForm.add(btnAddAnt);

        String[] antCols = {"Type", "Description", "Risque"};
        antTableModel = new DefaultTableModel(antCols, 0);
        JTable antTable = new JTable(antTableModel);
        JScrollPane antScroll = new JScrollPane(antTable);
        antScroll.setPreferredSize(new Dimension(400, 100));

        refreshAntecedentsTable();

        antPanel.add(antForm, BorderLayout.NORTH);
        antPanel.add(antScroll, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        JButton btnSave = new JButton("Enregistrer");
        JButton btnCancel = new JButton("Annuler");
        
        btnSave.setBackground(UIConstants.ACCENT_GREEN);
        btnSave.setForeground(Color.WHITE);
        btnSave.addActionListener(e -> save());
        
        btnCancel.addActionListener(e -> dispose());
        
        buttonPanel.add(btnCancel);
        buttonPanel.add(btnSave);

        mainPanel.add(formGrid);
        mainPanel.add(addressPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(antPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(buttonPanel);
        
        add(mainPanel);
    }

    private void addLabeledField(JPanel p, String label, JComponent field) {
        JPanel wrapper = new JPanel(new BorderLayout(0, 5));
        wrapper.setBackground(Color.WHITE);
        wrapper.add(new JLabel(label), BorderLayout.NORTH);
        wrapper.add(field, BorderLayout.CENTER);
        p.add(wrapper);
    }

    private void refreshAntecedentsTable() {
        antTableModel.setRowCount(0);
        for (AntecedentDTO a : currentAntecedents) {
            antTableModel.addRow(new Object[]{a.getCategorie(), a.getNom(), a.getNiveauDeRisque()});
        }
    }

    private void addAntecedent() {
        if (tfAntType.getText().isEmpty() || tfAntDesc.getText().isEmpty()) return;
        
        AntecedentDTO a = AntecedentDTO.builder()
                .categorie(tfAntType.getText())
                .nom(tfAntDesc.getText())
                .niveauDeRisque((String) cbAntRisque.getSelectedItem())
                .build();
        currentAntecedents.add(a);
        refreshAntecedentsTable();
        
        tfAntType.setText("");
        tfAntDesc.setText("");
    }

    private void save() {
        // Validation...
        patient.setNom(tfNom.getText());
        patient.setPrenom(tfPrenom.getText());
        patient.setCin(tfCin.getText());
        patient.setTelephone(tfTel.getText());
        patient.setEmail(tfEmail.getText());
        patient.setAdresse(tfAdresse.getText());
        
        if (!tfDateNaissance.getText().isEmpty()) {
            try {
                 patient.setDateNaissance(LocalDate.parse(tfDateNaissance.getText())); 
            } catch (Exception e) {}
        }

        patient.setAntecedents(currentAntecedents);
        saved = true;
        dispose();
    }

    public boolean isSaved() {
        return saved;
    }
    
    public PatientDTO getPatient() {
        return patient;
    }
}
