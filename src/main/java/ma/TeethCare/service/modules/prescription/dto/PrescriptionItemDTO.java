package ma.TeethCare.service.modules.prescription.dto;

public class PrescriptionItemDTO {

	private Long idMedicament;
	private String dose;
	private String frequence;
	private int dureeEnJours;

	public Long getIdMedicament() {
		return idMedicament;
	}

	public void setIdMedicament(Long idMedicament) {
		this.idMedicament = idMedicament;
	}

	public String getDose() {
		return dose;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}

	public String getFrequence() {
		return frequence;
	}

	public void setFrequence(String frequence) {
		this.frequence = frequence;
	}

	public int getDureeEnJours() {
		return dureeEnJours;
	}

	public void setDureeEnJours(int dureeEnJours) {
		this.dureeEnJours = dureeEnJours;
	}
}


