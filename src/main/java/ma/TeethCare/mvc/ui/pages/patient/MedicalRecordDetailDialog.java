package ma.TeethCare.mvc.ui.pages.patient;

import ma.TeethCare.mvc.dto.patient.PatientDTO;
import ma.TeethCare.mvc.dto.antecedent.AntecedentDTO;
import ma.TeethCare.mvc.ui.palette.utils.UIConstants;
import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.containers.RoundedPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class MedicalRecordDetailDialog extends JDialog {

    public MedicalRecordDetailDialog(Window owner, PatientDTO patient) {
        super(owner, "Détails Dossier Médical", ModalityType.APPLICATION_MODAL);
        
        setLayout(new BorderLayout());
        setSize(500, 600);
        setLocationRelativeTo(owner);
        getContentPane().setBackground(Color.WHITE);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(20, 20, 10, 20));
        
        JLabel title = new JLabel("Détails Dossier Médical");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(UIConstants.TEXT_DARK);
        
        JLabel date = new JLabel(LocalDate.now().toString()); // Placeholder date
        date.setForeground(UIConstants.TEXT_GRAY);
        
        header.add(title, BorderLayout.WEST);
        header.add(date, BorderLayout.EAST);
        
        add(header, BorderLayout.NORTH);

        // Content
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);
        content.setBorder(new EmptyBorder(10, 20, 20, 20));

        // Patient Card
        content.add(createPatientCard(patient));
        content.add(Box.createVerticalStrut(20));

        // Alerts Row (Allergies + Antecedents)
        JPanel alertsPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        alertsPanel.setOpaque(false);
        alertsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        
        alertsPanel.add(createAlertCard("Allergies", "Aucune", new Color(254, 242, 242), new Color(185, 28, 28))); // Red-50, Red-700
        alertsPanel.add(createAlertCard("Antécédents", formatAntecedents(patient.getAntecedents()), new Color(255, 251, 235), new Color(180, 83, 9))); // Amber-50, Amber-700
        
        content.add(alertsPanel);
        content.add(Box.createVerticalStrut(20));

        // Details Fields
        content.add(createDetailField("Diagnostic Principal", "Gingivite légère"));
        content.add(Box.createVerticalStrut(15));
        content.add(createDetailField("Traitement en cours", "Détartrage + bain de bouche"));

        add(content, BorderLayout.CENTER);

        // Footer Actions
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBackground(Color.WHITE);
        footer.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        JButton btnClose = new JButton("Fermer");
        btnClose.setFocusPainted(false);
        btnClose.setBackground(UIConstants.SURFACE_MAIN);
        btnClose.addActionListener(e -> dispose());
        
        footer.add(btnClose);
        
        add(footer, BorderLayout.SOUTH);
    }

    private JPanel createPatientCard(PatientDTO p) {
        RoundedPanel card = new RoundedPanel(8);
        card.setBackground(UIConstants.SURFACE_MAIN);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(15, 15, 15, 15));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        JLabel name = new JLabel(p.getPrenom() + " " + p.getNom());
        name.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        JLabel id = new JLabel("ID: #" + (p.getId() != null ? p.getId() : "N/A"));
        id.setForeground(UIConstants.TEXT_GRAY);
        
        JPanel info = new JPanel(new GridLayout(2, 1));
        info.setOpaque(false);
        info.add(name);
        info.add(id);
        
        card.add(info, BorderLayout.CENTER);
        return card;
    }

    private JPanel createAlertCard(String title, String value, Color bg, Color text) {
        RoundedPanel card = new RoundedPanel(8);
        card.setBackground(bg);
        card.setLayout(new GridLayout(2, 1, 0, 5));
        card.setBorder(new EmptyBorder(10, 15, 10, 15));
        
        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 13));
        t.setForeground(text);
        
        JLabel v = new JLabel(value);
        v.setForeground(text.darker());
        
        card.add(t);
        card.add(v);
        return card;
    }

    private JPanel createDetailField(String label, String value) {
        JPanel p = new JPanel(new BorderLayout(0, 8));
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        
        JLabel l = new JLabel(label);
        l.setFont(new Font("Segoe UI", Font.BOLD, 13));
        l.setForeground(UIConstants.TEXT_DARK);
        
        JTextField f = new JTextField(value);
        f.setEditable(false);
        f.setBackground(Color.WHITE);
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.BORDER_GRAY),
            new EmptyBorder(8, 10, 8, 10)
        ));
        
        p.add(l, BorderLayout.NORTH);
        p.add(f, BorderLayout.CENTER);
        return p;
    }
    
    private String formatAntecedents(List<AntecedentDTO> list) {
        if (list == null || list.isEmpty()) return "Aucun";
        return list.size() + " enregistré(s)";
    }
}
