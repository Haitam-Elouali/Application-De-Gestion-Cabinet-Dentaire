package ma.TeethCare.service.modules.interventionMedecin;

import ma.TeethCare.entities.interventionMedecin.interventionMedecin;
import ma.TeethCare.service.common.BaseService;
import java.util.List;

public interface InterventionMedecinService extends BaseService<interventionMedecin, Long> {
    List<interventionMedecin> findByMedecinId(Long medecinId) throws Exception;
    List<interventionMedecin> findByConsultationId(Long consultationId) throws Exception;
    List<interventionMedecin> findByActeId(Long acteId) throws Exception;
}
