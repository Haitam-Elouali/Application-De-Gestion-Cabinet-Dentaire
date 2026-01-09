package ma.TeethCare.mvc.ui.pages.otherPages;

import ma.TeethCare.mvc.dto.authentificationDtos.UserPrincipal;
import ma.TeethCare.mvc.ui.pages.otherPages.common.BasePlaceholderPanel;

public class UsersPanel extends BasePlaceholderPanel {
    public UsersPanel(UserPrincipal principal) {
        super("Backoffice — Users", principal);
    }
}
