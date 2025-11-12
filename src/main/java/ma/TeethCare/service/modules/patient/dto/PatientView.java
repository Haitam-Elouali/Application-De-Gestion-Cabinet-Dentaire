package ma.TeethCare.service.modules.patient.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PatientView {

	private Long id;
	private String nomComplet;
	private LocalDate dateNaissance;
	private Integer age;
	private String assurance;
	private LocalDateTime dateCreation;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getNomComplet() { return nomComplet; }
	public void setNomComplet(String nomComplet) { this.nomComplet = nomComplet; }
	public LocalDate getDateNaissance() { return dateNaissance; }
	public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }
	public Integer getAge() { return age; }
	public void setAge(Integer age) { this.age = age; }
	public String getAssurance() { return assurance; }
	public void setAssurance(String assurance) { this.assurance = assurance; }
	public LocalDateTime getDateCreation() { return dateCreation; }
	public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
}


