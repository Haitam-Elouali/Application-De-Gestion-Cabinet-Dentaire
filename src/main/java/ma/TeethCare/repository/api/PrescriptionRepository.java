package ma.TeethCare.repository.api;

import ma.TeethCare.entities.prescription.prescription;
import ma.TeethCare.repository.common.CrudRepository;

import java.util.Optional;

public interface PrescriptionRepository extends CrudRepository<prescription, Long> {
}
