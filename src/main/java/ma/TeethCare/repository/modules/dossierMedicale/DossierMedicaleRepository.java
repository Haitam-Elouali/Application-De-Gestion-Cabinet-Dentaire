package ma.TeethCare.repository.modules.dossierMedicale;

import ma.TeethCare.entities.dossierMedicale.dossierMedicale;
import ma.TeethCare.repository.common.BaseRepository;
import java.util.Optional;

public interface DossierMedicaleRepository extends BaseRepository<dossierMedicale, Long> {
    Optional<dossierMedicale> findByPatientId(Long patientId) throws Exception;
}
