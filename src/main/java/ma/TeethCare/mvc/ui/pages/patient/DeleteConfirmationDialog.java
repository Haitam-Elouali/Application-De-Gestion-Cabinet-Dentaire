package ma.TeethCare.mvc.ui.pages.patient;

import ma.TeethCare.mvc.ui.palette.utils.UIConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DeleteConfirmationDialog extends JDialog {

    private boolean confirmed = false;

    public DeleteConfirmationDialog(Window owner, String patientName) {
        super(owner, "Confirmation", ModalityType.APPLICATION_MODAL);
        
        setLayout(new BorderLayout());
        setSize(400, 200);
        setLocationRelativeTo(owner);
        
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Color.WHITE);
        content.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        JLabel msg = new JLabel("<html>Êtes-vous sûr de vouloir supprimer le patient <b>" + patientName + "</b> ?</html>");
        msg.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        
        content.add(msg, BorderLayout.CENTER);
        
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.setBackground(Color.WHITE);
        
        JButton btnCancel = new JButton("Annuler");
        JButton btnDelete = new JButton("Supprimer");
        
        btnDelete.setBackground(new Color(220, 53, 69)); // Red
        btnDelete.setForeground(Color.WHITE);
        
        btnCancel.addActionListener(e -> dispose());
        btnDelete.addActionListener(e -> {
            confirmed = true;
            dispose();
        });
        
        buttons.add(btnCancel);
        buttons.add(btnDelete);
        
        add(content, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
