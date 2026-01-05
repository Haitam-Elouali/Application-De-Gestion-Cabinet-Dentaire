package ma.TeethCare.mvc.ui.pages.otherPages;

import ma.TeethCare.mvc.dto.authentificationDtos.UserPrincipal;
import ma.TeethCare.mvc.ui.pages.otherPages.common.BasePlaceholderPanel;

public class PatientsPanel extends BasePlaceholderPanel {
    public PatientsPanel(UserPrincipal principal) {
        super("Module Patients", principal);
    }
}
