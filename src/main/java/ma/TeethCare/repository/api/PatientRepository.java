package ma.TeethCare.repository.api;

import ma.TeethCare.entities.patient.Patient;
import ma.TeethCare.repository.common.CrudRepository;

public interface PatientRepository extends CrudRepository<Patient, Long>

{
    java.util.List<Patient> search(String keyword);
}

