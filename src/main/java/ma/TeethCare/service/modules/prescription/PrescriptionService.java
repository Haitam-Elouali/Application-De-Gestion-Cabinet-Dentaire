package ma.TeethCare.service.modules.prescription;

import ma.TeethCare.entities.prescription.prescription;
import ma.TeethCare.service.common.BaseService;
import java.util.List;

public interface PrescriptionService extends BaseService<prescription, Long> {
    List<prescription> findByOrdonnanceId(Long ordonnanceId) throws Exception;
    List<prescription> findByMedicamentId(Long medicamentId) throws Exception;
}
