package ma.TeethCare.mvc.controllers.certificat;

import ma.TeethCare.mvc.dto.certificat.CertificatDTO;
import java.util.List;

public interface CertificatController {
    void create(CertificatDTO certificat) throws Exception;
    CertificatDTO getById(Long id) throws Exception;
    List<CertificatDTO> getAll() throws Exception;
    void update(Long id, CertificatDTO certificat) throws Exception;
    void delete(Long id) throws Exception;
    List<CertificatDTO> findByPatientId(Long patientId) throws Exception;
    void sendCertificate(Long certificatId, String email) throws Exception;
}
