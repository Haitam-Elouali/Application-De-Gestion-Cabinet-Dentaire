package ma.TeethCare.service.modules.medecin;

import ma.TeethCare.entities.medecin.medecin;
import ma.TeethCare.service.common.BaseService;
import java.util.List;
import java.util.Optional;

public interface MedecinService extends BaseService<medecin, Long> {
    List<medecin> findBySpecialite(String specialite) throws Exception;
    Optional<medecin> findByNumeroOrdre(String numeroOrdre) throws Exception;
    List<medecin> findAll() throws Exception;
}
