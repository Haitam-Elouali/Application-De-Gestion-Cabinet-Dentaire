package ma.TeethCare.service.modules.consultation;

import ma.TeethCare.entities.consultation.consultation;
import ma.TeethCare.service.common.BaseService;
import java.util.List;

public interface ConsultationService extends BaseService<consultation, Long> {
    List<consultation> findByPatientId(Long patientId) throws Exception;
    List<consultation> findByMedecinId(Long medecinId) throws Exception;
    List<consultation> findByRdvId(Long rdvId) throws Exception;
}
