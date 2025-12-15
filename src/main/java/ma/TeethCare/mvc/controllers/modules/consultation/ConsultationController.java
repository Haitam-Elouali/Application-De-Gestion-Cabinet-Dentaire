package ma.TeethCare.mvc.controllers.consultation;

import ma.TeethCare.mvc.dto.consultation.ConsultationDTO;
import java.util.List;

public interface ConsultationController {
    void create(ConsultationDTO consultation) throws Exception;
    ConsultationDTO getById(Long id) throws Exception;
    List<ConsultationDTO> getAll() throws Exception;
    void update(Long id, ConsultationDTO consultation) throws Exception;
    void delete(Long id) throws Exception;
    List<ConsultationDTO> findByPatientId(Long patientId) throws Exception;
    List<ConsultationDTO> findByMedecinId(Long medecinId) throws Exception;
}
