package ma.TeethCare.mvc.ui.pages.otherPages;

import ma.TeethCare.service.modules.auth.dto.UserPrincipal;
import ma.TeethCare.mvc.ui.pages.otherPages.common.BasePlaceholderPanel;

public class ParametragePanel extends BasePlaceholderPanel {
    public ParametragePanel(UserPrincipal principal) {
        super("Paramétrage Cabinet", principal);
    }
}
