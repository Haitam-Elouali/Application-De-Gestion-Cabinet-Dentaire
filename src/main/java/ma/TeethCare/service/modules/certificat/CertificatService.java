package ma.TeethCare.service.modules.certificat;

import ma.TeethCare.entities.certificat.certificat;
import ma.TeethCare.service.common.BaseService;
import java.util.List;

public interface CertificatService extends BaseService<certificat, Long> {
    List<certificat> findByConsultationId(Long consultationId) throws Exception;
    List<certificat> findByMedecinId(Long medecinId) throws Exception;
    List<certificat> findByPatientId(Long patientId) throws Exception;
    void sendCertificate(Long certificatId, String email) throws Exception;
}
