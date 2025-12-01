package ma.TeethCare.repository.modules.consultation;

import ma.TeethCare.entities.consultation.consultation;
import ma.TeethCare.repository.common.BaseRepository;
import java.util.List;

public interface ConsultationRepository extends BaseRepository<consultation, Long> {
    List<consultation> findByPatientId(Long patientId) throws Exception;
    List<consultation> findByMedecinId(Long medecinId) throws Exception;
    List<consultation> findByRdvId(Long rdvId) throws Exception;
}
