package ma.TeethCare.service.modules.patient;

import ma.TeethCare.entities.patient.Patient;
import ma.TeethCare.service.common.BaseService;
import java.util.List;
import java.util.Optional;

public interface PatientService extends BaseService<Patient, Long> {
    Optional<Patient> findByEmail(String email) throws Exception;
    List<Patient> findByTelephone(String telephone) throws Exception;
    List<Patient> findByNomAndPrenom(String nom, String prenom) throws Exception;
    List<Patient> findByAssurance(String assurance) throws Exception;
}
