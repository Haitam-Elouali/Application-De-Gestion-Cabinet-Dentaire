package ma.TeethCare.service.modules.secretaire.api;

import ma.TeethCare.service.modules.secretaire.dto.SecretaireDTO;

public interface SecretaireService {

	Long create(SecretaireDTO secretaire);

	void update(Long idSecretaire, SecretaireDTO secretaire);
}


