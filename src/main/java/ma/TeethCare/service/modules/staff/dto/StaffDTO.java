package ma.TeethCare.service.modules.staff.dto;

import java.time.LocalDate;

public class StaffDTO {

	private Long id;
	private Long idCabinet;
	private String role; // MEDECIN, SECRETAIRE, ASSISTANT, ...
	private double salaire;
	private LocalDate dateRecrutement;
	private int soldeConges;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public Long getIdCabinet() { return idCabinet; }
	public void setIdCabinet(Long idCabinet) { this.idCabinet = idCabinet; }
	public String getRole() { return role; }
	public void setRole(String role) { this.role = role; }
	public double getSalaire() { return salaire; }
	public void setSalaire(double salaire) { this.salaire = salaire; }
	public LocalDate getDateRecrutement() { return dateRecrutement; }
	public void setDateRecrutement(LocalDate dateRecrutement) { this.dateRecrutement = dateRecrutement; }
	public int getSoldeConges() { return soldeConges; }
	public void setSoldeConges(int soldeConges) { this.soldeConges = soldeConges; }
}


