package ma.TeethCare.service.modules.dossierMedicale;

import ma.TeethCare.entities.dossierMedicale.dossierMedicale;
import ma.TeethCare.service.common.BaseService;
import java.util.Optional;

public interface DossierMedicaleService extends BaseService<dossierMedicale, Long> {
    Optional<dossierMedicale> findByPatientId(Long patientId) throws Exception;
}
