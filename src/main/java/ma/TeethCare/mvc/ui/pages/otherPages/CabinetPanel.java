package ma.TeethCare.mvc.ui.pages.otherPages;

import ma.TeethCare.mvc.dto.authentificationDtos.UserPrincipal;
import ma.TeethCare.mvc.ui.pages.otherPages.common.BasePlaceholderPanel;

public class CabinetPanel extends BasePlaceholderPanel {
    public CabinetPanel(UserPrincipal principal) {
        super("Cabinet courant", principal);
    }
}
