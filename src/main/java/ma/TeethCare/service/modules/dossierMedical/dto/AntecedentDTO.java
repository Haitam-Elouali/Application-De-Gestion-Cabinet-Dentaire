package ma.TeethCare.service.modules.dossierMedical.dto;

import java.time.LocalDate;

public class AntecedentDTO {

	private String nom;
	private String description;
	private LocalDate date;

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}


