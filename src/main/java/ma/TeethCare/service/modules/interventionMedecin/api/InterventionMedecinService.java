package ma.TeethCare.service.modules.interventionMedecin.api;

import ma.TeethCare.service.modules.interventionMedecin.dto.InterventionDTO;

public interface InterventionMedecinService {

	Long planifier(InterventionDTO intervention);

	void lierConsultation(Long idIntervention, Long idConsultation);
}


