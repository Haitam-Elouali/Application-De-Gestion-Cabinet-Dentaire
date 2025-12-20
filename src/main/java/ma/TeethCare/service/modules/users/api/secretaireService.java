package ma.TeethCare.service.modules.users.api;

import ma.TeethCare.entities.secretaire.secretaire;
import ma.TeethCare.service.common.BaseService;

import java.util.Optional;

public interface secretaireService extends BaseService<secretaire, Long> {
    Optional<secretaire> findByNumCNSS(String numCNSS) throws Exception;

    Optional<secretaire> findByCin(String cin) throws Exception;
}
