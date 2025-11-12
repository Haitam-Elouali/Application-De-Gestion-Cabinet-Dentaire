package ma.TeethCare.service.modules.caisse.api;

import java.util.List;

import ma.TeethCare.service.modules.caisse.dto.RevenuDTO;

public interface RevenuService {

	Long enregistrerRevenu(RevenuDTO revenu);

	List<RevenuDTO> listByPeriode(String dateDebutInclusive, String dateFinExclusive);
}


