package ma.TeethCare.mvc.ui.pages.otherPages;

import ma.TeethCare.mvc.dto.authentificationDtos.UserPrincipal;
import ma.TeethCare.mvc.ui.pages.otherPages.common.BasePlaceholderPanel;

public class NotificationsPanel extends BasePlaceholderPanel {
    public NotificationsPanel(UserPrincipal principal) {
        super("Notifications", principal);
    }
}
