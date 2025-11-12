package ma.TeethCare.service.modules.cabinet.dto;

public class CabinetDTO {

	private Long id;
	private String nom;
	private String adresse;
	private String phone;
	private String email;
	private String description;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getNom() { return nom; }
	public void setNom(String nom) { this.nom = nom; }
	public String getAdresse() { return adresse; }
	public void setAdresse(String adresse) { this.adresse = adresse; }
	public String getPhone() { return phone; }
	public void setPhone(String phone) { this.phone = phone; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
}


