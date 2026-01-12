package ma.TeethCare.mvc.controllers.modules.caisse.api;

import ma.TeethCare.service.modules.auth.dto.UserPrincipal;
import javax.swing.JPanel;

public interface CaisseController {
    JPanel getView(UserPrincipal principal);
}
