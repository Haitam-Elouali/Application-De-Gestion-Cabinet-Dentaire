package ma.TeethCare.service.modules.secretaire.dto;

public class SecretaireDTO {

	private Long id;
	private String numCNSS;
	private double commission;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getNumCNSS() { return numCNSS; }
	public void setNumCNSS(String numCNSS) { this.numCNSS = numCNSS; }
	public double getCommission() { return commission; }
	public void setCommission(double commission) { this.commission = commission; }
}


