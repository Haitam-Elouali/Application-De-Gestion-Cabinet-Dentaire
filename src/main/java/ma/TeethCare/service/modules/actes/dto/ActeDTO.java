package ma.TeethCare.service.modules.actes.dto;

public class ActeDTO {

	private Long id;
	private String libelle;
	private String code;
	private double prixDeBase;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getLibelle() { return libelle; }
	public void setLibelle(String libelle) { this.libelle = libelle; }
	public String getCode() { return code; }
	public void setCode(String code) { this.code = code; }
	public double getPrixDeBase() { return prixDeBase; }
	public void setPrixDeBase(double prixDeBase) { this.prixDeBase = prixDeBase; }
}


