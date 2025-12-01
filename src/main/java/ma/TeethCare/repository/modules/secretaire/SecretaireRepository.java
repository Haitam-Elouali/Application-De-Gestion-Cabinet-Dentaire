package ma.TeethCare.repository.modules.secretaire;

import ma.TeethCare.entities.secretaire.secretaire;
import ma.TeethCare.repository.common.BaseRepository;
import java.util.List;

public interface SecretaireRepository extends BaseRepository<secretaire, Long> {
    List<secretaire> findAll() throws Exception;
    List<secretaire> findByNumCNSS(String numCNSS) throws Exception;
}
