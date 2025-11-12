package ma.TeethCare.service.modules.caisse.dto;

import java.time.LocalDateTime;

public class RevenuDTO {

	private Long id;
	private String source;
	private String description;
	private double montant;
	private LocalDateTime date;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getSource() { return source; }
	public void setSource(String source) { this.source = source; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public double getMontant() { return montant; }
	public void setMontant(double montant) { this.montant = montant; }
	public LocalDateTime getDate() { return date; }
	public void setDate(LocalDateTime date) { this.date = date; }
}


