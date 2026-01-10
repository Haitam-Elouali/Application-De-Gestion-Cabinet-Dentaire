package ma.TeethCare.mvc.ui.pages.otherPages;

import ma.TeethCare.service.modules.auth.dto.UserPrincipal;
import ma.TeethCare.mvc.ui.pages.otherPages.common.BasePlaceholderPanel;

public class DossiersPanel extends BasePlaceholderPanel {
    public DossiersPanel(UserPrincipal principal) {
        super("Dossiers Médicaux", principal);
    }
}
