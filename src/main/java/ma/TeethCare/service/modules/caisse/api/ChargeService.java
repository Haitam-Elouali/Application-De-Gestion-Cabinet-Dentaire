package ma.TeethCare.service.modules.caisse.api;

import java.util.List;

import ma.TeethCare.service.modules.caisse.dto.ChargeDTO;

public interface ChargeService {

	Long enregistrerCharge(ChargeDTO charge);

	List<ChargeDTO> listByPeriode(String dateDebutInclusive, String dateFinExclusive);
}


