package ma.TeethCare.service.modules.facture.api;

import java.math.BigDecimal;

public interface FactureService {

	Long createForConsultation(Long idConsultation);

	void pay(Long idFacture, BigDecimal amount, String mode);

	void refund(Long idFacture, BigDecimal amount);
}


