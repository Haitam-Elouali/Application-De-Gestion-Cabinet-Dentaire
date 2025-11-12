package ma.TeethCare.service.modules.consultation.dto;

import java.time.LocalDateTime;

public class ConsultationView {

	private Long idConsultation;
	private Long idRdv;
	private Long idPatient;
	private Long idMedecin;
	private LocalDateTime date;
	private String etat; // e.g., OUVERTE, FERMEE
	private String observations;

	public Long getIdConsultation() {
		return idConsultation;
	}

	public void setIdConsultation(Long idConsultation) {
		this.idConsultation = idConsultation;
	}

	public Long getIdRdv() {
		return idRdv;
	}

	public void setIdRdv(Long idRdv) {
		this.idRdv = idRdv;
	}

	public Long getIdPatient() {
		return idPatient;
	}

	public void setIdPatient(Long idPatient) {
		this.idPatient = idPatient;
	}

	public Long getIdMedecin() {
		return idMedecin;
	}

	public void setIdMedecin(Long idMedecin) {
		this.idMedecin = idMedecin;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}
}


