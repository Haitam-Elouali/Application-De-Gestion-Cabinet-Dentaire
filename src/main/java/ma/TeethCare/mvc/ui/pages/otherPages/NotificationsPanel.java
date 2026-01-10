package ma.TeethCare.mvc.ui.pages.otherPages;

import ma.TeethCare.service.modules.auth.dto.UserPrincipal;
import ma.TeethCare.mvc.ui.pages.otherPages.common.BasePlaceholderPanel;

public class NotificationsPanel extends BasePlaceholderPanel {
    public NotificationsPanel(UserPrincipal principal) {
        super("Notifications", principal);
    }
}
