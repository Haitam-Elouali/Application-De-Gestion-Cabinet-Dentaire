package ma.TeethCare.mvc.controllers.modules.patient.api;

import ma.TeethCare.service.modules.auth.dto.UserPrincipal;
import javax.swing.JPanel;

public interface PatientController {
    JPanel getView(UserPrincipal principal);
}
