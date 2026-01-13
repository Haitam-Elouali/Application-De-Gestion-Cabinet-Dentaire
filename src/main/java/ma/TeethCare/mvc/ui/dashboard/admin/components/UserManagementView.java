package ma.TeethCare.mvc.ui.dashboard.admin.components;

import ma.TeethCare.mvc.ui.palette.buttons.ModernButton;
import ma.TeethCare.mvc.ui.palette.data.ModernTable;
import ma.TeethCare.mvc.ui.palette.utils.TailwindPalette;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UserManagementView extends JPanel {

    private ma.TeethCare.repository.api.UtilisateurRepository utilisateurRepository;

    public UserManagementView() {
        this.utilisateurRepository = new ma.TeethCare.repository.mySQLImpl.UtilisateurRepositoryImpl();
        
        setLayout(new BorderLayout());
        setOpaque(false); // Transparent
        setBorder(new EmptyBorder(24, 24, 24, 24));
        
        initUI();
    }

    private void initUI() {
        // Toolbar
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setOpaque(false);
        toolbar.setBorder(new EmptyBorder(0, 0, 16, 0));
        
        JLabel title = new JLabel("Gestion des Utilisateurs");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.decode("#1f2937"));

        ModernButton addBtn = new ModernButton("Nouvel Utilisateur", ModernButton.Variant.DESTRUCTIVE);
        // Placeholder action
        addBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Fonctionnalité à implémenter"));
        
        toolbar.add(title, BorderLayout.WEST);
        toolbar.add(addBtn, BorderLayout.EAST);
        
        // Card
        ma.TeethCare.mvc.ui.palette.containers.RoundedPanel card = new ma.TeethCare.mvc.ui.palette.containers.RoundedPanel(12);
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(24, 24, 24, 24));

        card.add(toolbar, BorderLayout.NORTH);
        
        add(card, BorderLayout.CENTER);
        
        // Fetch Data
        java.util.List<ma.TeethCare.entities.utilisateur.utilisateur> users = new java.util.ArrayList<>();
        try {
            users = utilisateurRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Table
        String[] columns = {"ID", "Utilisateur", "Rôle", "Email", "Statut", "Dernière Connexion", "Actions"};
        Object[][] data = new Object[users.size()][7];
        
        java.time.format.DateTimeFormatter dtf = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        for (int i = 0; i < users.size(); i++) {
            ma.TeethCare.entities.utilisateur.utilisateur u = users.get(i);
            
            // Determine Role string (assuming single role for display or joining them)
            String roleStr = "Aucun";
            if (u.getRoles() != null && !u.getRoles().isEmpty()) {
                roleStr = u.getRoles().get(0).getLibelle();
            }
            
            data[i][0] = u.getId();
            data[i][1] = (u.getNom() != null ? u.getNom() : "") + " " + (u.getPrenom() != null ? u.getPrenom() : "");
            data[i][2] = roleStr;
            data[i][3] = u.getEmail() != null ? u.getEmail() : "";
            data[i][4] = "Actif"; // Placeholder status, entity might not have active/inactive field yet
            data[i][5] = u.getDateDerniereModification() != null ? u.getDateDerniereModification().format(dtf) : "-"; // Using last mod as proxy or placeholder
            data[i][6] = ""; // Actions
        }

        ModernTable table = new ModernTable();
        table.setModel(new DefaultTableModel(data, columns));
        table.setRowHeight(60);
        
        // Renderers
        table.getColumnModel().getColumn(2).setCellRenderer(new ma.TeethCare.mvc.ui.palette.renderers.RolePillRenderer());
        table.getColumnModel().getColumn(4).setCellRenderer(new ma.TeethCare.mvc.ui.palette.renderers.StatusPillRenderer());
        
        // Actions
        ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer actionRenderer = new ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer(
            null,
            ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.VIEW_FULL,
            ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.EDIT,
            ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.SUSPEND,
            ma.TeethCare.mvc.ui.palette.renderers.TableActionCellRenderer.ActionType.DELETE
        );
        table.getColumnModel().getColumn(6).setCellRenderer(actionRenderer);
        table.getColumnModel().getColumn(6).setCellEditor(actionRenderer);
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(TailwindPalette.BORDER));
        sp.getViewport().setBackground(Color.WHITE);
        
        card.add(sp, BorderLayout.CENTER);
    }
}
