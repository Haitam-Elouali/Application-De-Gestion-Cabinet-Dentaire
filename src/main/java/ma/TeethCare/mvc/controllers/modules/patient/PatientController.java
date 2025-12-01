package ma.TeethCare.mvc.controllers.patient;

import ma.TeethCare.mvc.dto.patient.PatientDTO;
import java.util.List;

public interface PatientController {
    void create(PatientDTO patient) throws Exception;
    PatientDTO getById(Long id) throws Exception;
    List<PatientDTO> getAll() throws Exception;
    void update(Long id, PatientDTO patient) throws Exception;
    void delete(Long id) throws Exception;
    PatientDTO findByEmail(String email) throws Exception;
    List<PatientDTO> findByNomAndPrenom(String nom, String prenom) throws Exception;
}
