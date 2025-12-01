package ma.TeethCare.service.modules.secretaire;

import ma.TeethCare.entities.secretaire.secretaire;
import ma.TeethCare.service.common.BaseService;
import java.util.List;

public interface SecretaireService extends BaseService<secretaire, Long> {
    List<secretaire> findAll() throws Exception;
    List<secretaire> findByNumCNSS(String numCNSS) throws Exception;
}
