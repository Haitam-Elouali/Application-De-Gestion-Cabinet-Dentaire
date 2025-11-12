package ma.TeethCare.service.modules.caisse.dto;

import java.time.LocalDateTime;

public class ChargeDTO {

	private Long id;
	private String type;
	private String description;
	private double montant;
	private LocalDateTime date;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public double getMontant() { return montant; }
	public void setMontant(double montant) { this.montant = montant; }
	public LocalDateTime getDate() { return date; }
	public void setDate(LocalDateTime date) { this.date = date; }
}


