package ma.TeethCare.mvc.controllers.modules.agenda.api;

import ma.TeethCare.service.modules.auth.dto.UserPrincipal;
import javax.swing.JPanel;

public interface AgendaController {
    JPanel getView(UserPrincipal principal);
}
