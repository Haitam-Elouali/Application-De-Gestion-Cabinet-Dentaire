package ma.TeethCare.mvc.ui.pages.notification;

import ma.TeethCare.mvc.controllers.modules.notification.api.NotificationController;
import ma.TeethCare.service.modules.auth.dto.UserPrincipal;
import ma.TeethCare.service.modules.notifications.api.notificationService;
import ma.TeethCare.mvc.dto.notification.NotificationDTO;
import ma.TeethCare.mvc.ui.palette.utils.UIConstants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class NotificationPanel extends JPanel {

    private final NotificationController controller;
    private final notificationService service;
    private final UserPrincipal principal;
    
    private JTable table;
    private DefaultTableModel tableModel;

    public NotificationPanel(NotificationController controller, notificationService service, UserPrincipal principal) {
        this.controller = controller;
        this.service = service;
        this.principal = principal;

        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(createHeader(), BorderLayout.NORTH);
        add(createListArea(), BorderLayout.CENTER);
        
        refreshData();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        JLabel title = new JLabel("Notifications");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(UIConstants.TEXT_DARK);
        header.add(title, BorderLayout.WEST);
        return header;
    }
    
    private JComponent createListArea() {
        String[] columns = {"ID", "Message", "Date", "Statut"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setShowGrid(false);
        
        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        return scroll;
    }
    
    private void refreshData() {
        try {
            // Assuming findAll exists and returns NotificationDTO list
             List<NotificationDTO> notifs = service.findAll();
             tableModel.setRowCount(0);
             for(NotificationDTO n : notifs) {
                 tableModel.addRow(new Object[]{ n.getId(), n.getMessage(), n.getDateCreation(), Boolean.TRUE.equals(n.getLue()) ? "Lue" : "Non Lue" });
             }
        } catch (Exception e) {
            // ignore or log
        }
    }
}
