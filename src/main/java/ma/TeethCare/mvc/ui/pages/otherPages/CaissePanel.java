package ma.TeethCare.mvc.ui.pages.otherPages;

import ma.TeethCare.mvc.dto.authentificationDtos.UserPrincipal;
import ma.TeethCare.mvc.ui.pages.otherPages.common.BasePlaceholderPanel;

public class CaissePanel extends BasePlaceholderPanel {
    public CaissePanel(UserPrincipal principal) {
        super("Module Caisse", principal);
    }
}
