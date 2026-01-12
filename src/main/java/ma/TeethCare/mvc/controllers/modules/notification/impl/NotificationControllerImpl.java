package ma.TeethCare.mvc.controllers.modules.notification.impl;

import ma.TeethCare.mvc.controllers.modules.notification.api.NotificationController;
import ma.TeethCare.service.modules.auth.dto.UserPrincipal;
import ma.TeethCare.service.modules.notifications.api.notificationService;
import ma.TeethCare.mvc.ui.pages.notification.NotificationPanel;
import javax.swing.JPanel;

public class NotificationControllerImpl implements NotificationController {

    private final notificationService service;

    public NotificationControllerImpl(notificationService service) {
        this.service = service;
    }

    @Override
    public JPanel getView(UserPrincipal principal) {
        return new NotificationPanel(this, service, principal);
    }
}
