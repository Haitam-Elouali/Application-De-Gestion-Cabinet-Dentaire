package ma.TeethCare.repository.modules.certificat;

import ma.TeethCare.entities.certificat.certificat;
import ma.TeethCare.repository.common.BaseRepository;
import java.util.List;

public interface CertificatRepository extends BaseRepository<certificat, Long> {
    List<certificat> findByConsultationId(Long consultationId) throws Exception;
    List<certificat> findByMedecinId(Long medecinId) throws Exception;
    List<certificat> findByPatientId(Long patientId) throws Exception;
}
