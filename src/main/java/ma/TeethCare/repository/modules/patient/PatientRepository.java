package ma.TeethCare.repository.modules.patient;

import ma.TeethCare.entities.patient.Patient;
import ma.TeethCare.repository.common.BaseRepository;
import java.util.Optional;
import java.util.List;

/**
 * Repository pour l'entité Patient
 * Gère les opérations CRUD sur les patients
 */
public interface PatientRepository extends BaseRepository<Patient, Long> {

    /**
     * Trouve un patient par email
     */
    Optional<Patient> findByEmail(String email) throws Exception;

    /**
     * Trouve un patient par téléphone
     */
    Optional<Patient> findByTelephone(String telephone) throws Exception;

    /**
     * Trouve les patients par nom et prénom
     */
    List<Patient> findByNomAndPrenom(String nom, String prenom) throws Exception;

    /**
     * Trouve les patients par assurance
     */
    List<Patient> findByAssurance(String assurance) throws Exception;
}
