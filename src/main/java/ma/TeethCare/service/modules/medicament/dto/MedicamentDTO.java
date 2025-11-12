package ma.TeethCare.service.modules.medicament.dto;

public class MedicamentDTO {

	private Long id;
	private String nom;
	private String laboratoire;
	private String description;
	private int stockDisponible;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getLaboratoire() {
		return laboratoire;
	}

	public void setLaboratoire(String laboratoire) {
		this.laboratoire = laboratoire;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStockDisponible() {
		return stockDisponible;
	}

	public void setStockDisponible(int stockDisponible) {
		this.stockDisponible = stockDisponible;
	}
}


