package ma.TeethCare.service.modules.users.api;

import ma.TeethCare.mvc.dto.secretaire.SecretaireDTO;
import ma.TeethCare.service.common.BaseService;
import java.util.Optional;

public interface secretaireService extends BaseService<SecretaireDTO, Long> {

    Optional<SecretaireDTO> findByNumCNSS(String numCNSS) throws Exception;

    Optional<SecretaireDTO> findByCin(String cin) throws Exception;
}
