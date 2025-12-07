package ma.TeethCare.repository.api;

import ma.TeethCare.entities.medicaments.medicaments;
import ma.TeethCare.repository.common.CrudRepository;

import java.util.Optional;

public interface MedicamentRepository extends CrudRepository<medicaments, Long> {
    Optional<medicaments> findByNom(String nom);
}
