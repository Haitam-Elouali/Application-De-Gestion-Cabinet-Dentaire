package ma.TeethCare.repository.modules.prescription;

import ma.TeethCare.entities.prescription.prescription;
import ma.TeethCare.repository.common.BaseRepository;
import java.util.List;

public interface PrescriptionRepository extends BaseRepository<prescription, Long> {
    List<prescription> findByOrdonnanceId(Long ordonnanceId) throws Exception;
    List<prescription> findByMedicamentId(Long medicamentId) throws Exception;
}
