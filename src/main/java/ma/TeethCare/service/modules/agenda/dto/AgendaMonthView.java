package ma.TeethCare.service.modules.agenda.dto;

import java.util.List;

public class AgendaMonthView {

	private Long idMedecin;
	private String mois; // e.g., "2025-11"
	private List<Integer> joursNonDisponibles;

	public Long getIdMedecin() {
		return idMedecin;
	}

	public void setIdMedecin(Long idMedecin) {
		this.idMedecin = idMedecin;
	}

	public String getMois() {
		return mois;
	}

	public void setMois(String mois) {
		this.mois = mois;
	}

	public List<Integer> getJoursNonDisponibles() {
		return joursNonDisponibles;
	}

	public void setJoursNonDisponibles(List<Integer> joursNonDisponibles) {
		this.joursNonDisponibles = joursNonDisponibles;
	}
}


