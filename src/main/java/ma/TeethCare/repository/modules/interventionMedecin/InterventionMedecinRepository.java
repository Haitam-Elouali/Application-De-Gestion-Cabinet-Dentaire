package ma.TeethCare.repository.modules.interventionMedecin;

import ma.TeethCare.entities.interventionMedecin.interventionMedecin;
import ma.TeethCare.repository.common.BaseRepository;
import java.util.List;

public interface InterventionMedecinRepository extends BaseRepository<interventionMedecin, Long> {
    List<interventionMedecin> findByMedecinId(Long medecinId) throws Exception;
    List<interventionMedecin> findByConsultationId(Long consultationId) throws Exception;
    List<interventionMedecin> findByActeId(Long acteId) throws Exception;
}
