package ma.TeethCare.service.modules.dossierMedical.api;

import ma.TeethCare.service.modules.dossierMedical.dto.AntecedentDTO;
import ma.TeethCare.service.modules.dossierMedical.dto.DossierSummaryDTO;

public interface DossierMedicalService {

	Long createForPatient(Long idPatient);

	void addAntecedent(Long idDossier, AntecedentDTO antecedent);

	DossierSummaryDTO getSummary(Long idPatient);
}


