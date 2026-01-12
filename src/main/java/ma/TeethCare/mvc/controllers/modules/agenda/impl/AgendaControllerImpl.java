package ma.TeethCare.mvc.controllers.modules.agenda.impl;

import ma.TeethCare.mvc.controllers.modules.agenda.api.AgendaController;
import ma.TeethCare.service.modules.auth.dto.UserPrincipal;
import ma.TeethCare.service.modules.agenda.api.rdvService;
import ma.TeethCare.service.modules.patient.api.PatientService;
import ma.TeethCare.mvc.ui.pages.agenda.AgendaPanel;
import javax.swing.JPanel;

public class AgendaControllerImpl implements AgendaController {

    private final rdvService service;
    private final PatientService patientService;

    public AgendaControllerImpl(rdvService service, PatientService patientService) {
        this.service = service;
        this.patientService = patientService;
    }

    @Override
    public JPanel getView(UserPrincipal principal) {
        return new AgendaPanel(this, service, patientService, principal);
    }
}
