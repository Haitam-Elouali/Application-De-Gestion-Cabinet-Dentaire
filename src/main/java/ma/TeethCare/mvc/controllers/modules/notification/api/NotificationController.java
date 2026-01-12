package ma.TeethCare.mvc.controllers.modules.notification.api;

import ma.TeethCare.service.modules.auth.dto.UserPrincipal;
import javax.swing.JPanel;

public interface NotificationController {
    JPanel getView(UserPrincipal principal);
}
