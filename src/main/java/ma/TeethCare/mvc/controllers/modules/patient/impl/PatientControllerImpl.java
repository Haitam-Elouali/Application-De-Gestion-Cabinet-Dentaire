package ma.TeethCare.mvc.controllers.modules.patient.impl;

import ma.TeethCare.mvc.controllers.modules.patient.api.PatientController;
import ma.TeethCare.service.modules.auth.dto.UserPrincipal;
import ma.TeethCare.service.modules.patient.api.PatientService;
import ma.TeethCare.mvc.ui.pages.patient.PatientPanel;
import javax.swing.JPanel;

public class PatientControllerImpl implements PatientController {

    private final PatientService patientService;

    public PatientControllerImpl(PatientService patientService) {
        this.patientService = patientService;
    }

    @Override
    public JPanel getView(UserPrincipal principal) {
        return new PatientPanel(this, patientService, principal);
    }
}
