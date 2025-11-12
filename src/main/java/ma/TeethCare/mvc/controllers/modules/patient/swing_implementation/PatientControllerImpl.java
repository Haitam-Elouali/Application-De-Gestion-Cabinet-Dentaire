package ma.TeethCare.mvc.controllers.modules.patient.swing_implementation;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.mvc.controllers.modules.patient.api.PatientController;
import ma.TeethCare.mvc.dto.PatientDTO;
import ma.TeethCare.mvc.ui.modules.patient.PatientView;
import ma.TeethCare.service.modules.patient.api.PatientService;

@Data @AllArgsConstructor @NoArgsConstructor
public class PatientControllerImpl implements PatientController {

    private PatientService service;

    @Override
    public void showRecentPatients() {
        List<PatientDTO> dtos = service.getTodayPatientsAsDTO();
        PatientView.showAsync(dtos);
    }
}
