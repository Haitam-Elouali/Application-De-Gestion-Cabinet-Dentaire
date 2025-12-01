package ma.TeethCare.service.modules.actes;

import ma.TeethCare.entities.actes.actes;
import ma.TeethCare.service.common.BaseService;
import java.util.List;

public interface ActesService extends BaseService<actes, Long> {
    List<actes> findByCategorie(String categorie) throws Exception;
    List<actes> findByCodeSECU(String codeSECU) throws Exception;
}
