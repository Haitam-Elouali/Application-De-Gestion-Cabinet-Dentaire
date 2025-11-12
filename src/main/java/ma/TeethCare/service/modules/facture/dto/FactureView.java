package ma.TeethCare.service.modules.facture.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FactureView {

	private Long idFacture;
	private Long idConsultation;
	private BigDecimal total;
	private String statut; // e.g., OUVERTE, PAYEE, REMBOURSEE
	private LocalDateTime dateFacture;

	public Long getIdFacture() {
		return idFacture;
	}

	public void setIdFacture(Long idFacture) {
		this.idFacture = idFacture;
	}

	public Long getIdConsultation() {
		return idConsultation;
	}

	public void setIdConsultation(Long idConsultation) {
		this.idConsultation = idConsultation;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public LocalDateTime getDateFacture() {
		return dateFacture;
	}

	public void setDateFacture(LocalDateTime dateFacture) {
		this.dateFacture = dateFacture;
	}
}


