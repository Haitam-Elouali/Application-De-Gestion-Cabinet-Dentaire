package ma.TeethCare.service.modules.caisse;

import ma.TeethCare.entities.caisse.caisse;
import ma.TeethCare.service.common.BaseService;
import java.util.List;

public interface CaisseService extends BaseService<caisse, Long> {
    List<caisse> findByFactureId(Long factureId) throws Exception;
    List<caisse> findByModeEncaissement(String mode) throws Exception;
    Double getTotalEncaisse() throws Exception;
}
