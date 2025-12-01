package ma.TeethCare.service.modules.ordonnance;

import ma.TeethCare.entities.ordonnance.ordonnance;
import ma.TeethCare.service.common.BaseService;
import java.util.List;

public interface OrdonnanceService extends BaseService<ordonnance, Long> {
    List<ordonnance> findByPatientId(Long patientId) throws Exception;
    List<ordonnance> findByMedecinId(Long medecinId) throws Exception;
    List<ordonnance> findByConsultationId(Long consultationId) throws Exception;
    List<ordonnance> findActiveOrdonnances() throws Exception;
}
