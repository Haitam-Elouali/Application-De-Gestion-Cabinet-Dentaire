package ma.TeethCare.repository.modules.actes;

import ma.TeethCare.entities.actes.actes;
import ma.TeethCare.repository.common.BaseRepository;
import java.util.List;

public interface ActesRepository extends BaseRepository<actes, Long> {
    List<actes> findByCategorie(String categorie) throws Exception;
    List<actes> findByCodeSECU(String codeSECU) throws Exception;
}
