package ma.TeethCare.service.modules.dossierMedical.dto;

import java.util.List;

public class DossierSummaryDTO {

	private Long idPatient;
	private Long idDossier;
	private List<AntecedentDTO> antecedents;
	private int consultationsCount;

	public Long getIdPatient() {
		return idPatient;
	}

	public void setIdPatient(Long idPatient) {
		this.idPatient = idPatient;
	}

	public Long getIdDossier() {
		return idDossier;
	}

	public void setIdDossier(Long idDossier) {
		this.idDossier = idDossier;
	}

	public List<AntecedentDTO> getAntecedents() {
		return antecedents;
	}

	public void setAntecedents(List<AntecedentDTO> antecedents) {
		this.antecedents = antecedents;
	}

	public int getConsultationsCount() {
		return consultationsCount;
	}

	public void setConsultationsCount(int consultationsCount) {
		this.consultationsCount = consultationsCount;
	}
}


