package ma.TeethCare.mvc.ui.pages.otherPages;

import ma.TeethCare.service.modules.auth.dto.UserPrincipal;
import ma.TeethCare.mvc.ui.pages.otherPages.common.BasePlaceholderPanel;

public class CabinetsPanel extends BasePlaceholderPanel {
    public CabinetsPanel(UserPrincipal principal) {
        super("Backoffice — Cabinets", principal);
    }
}
