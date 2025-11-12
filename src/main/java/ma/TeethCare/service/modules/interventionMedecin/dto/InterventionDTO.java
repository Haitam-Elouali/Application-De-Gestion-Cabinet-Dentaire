package ma.TeethCare.service.modules.interventionMedecin.dto;

import java.time.LocalDateTime;

public class InterventionDTO {

	private Long id;
	private Long idMedecin;
	private Long idPatient;
	private String type;
	private LocalDateTime date;
	private Integer numDent;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public Long getIdMedecin() { return idMedecin; }
	public void setIdMedecin(Long idMedecin) { this.idMedecin = idMedecin; }
	public Long getIdPatient() { return idPatient; }
	public void setIdPatient(Long idPatient) { this.idPatient = idPatient; }
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
	public LocalDateTime getDate() { return date; }
	public void setDate(LocalDateTime date) { this.date = date; }
	public Integer getNumDent() { return numDent; }
	public void setNumDent(Integer numDent) { this.numDent = numDent; }
}


