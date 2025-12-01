package ma.TeethCare.repository.modules.caisse;

import ma.TeethCare.entities.caisse.caisse;
import ma.TeethCare.repository.common.BaseRepository;
import java.util.List;

public interface CaisseRepository extends BaseRepository<caisse, Long> {
    List<caisse> findByFactureId(Long factureId) throws Exception;
    List<caisse> findByModeEncaissement(String mode) throws Exception;
    Double getTotalEncaisse() throws Exception;
}
