package ma.TeethCare.repository.api;

import ma.TeethCare.entities.medicaments.medicaments;
import ma.TeethCare.repository.common.CrudRepository;

import java.util.Optional;

public interface MedicamentRepository extends CrudRepository<medicaments, Long> {
    Optional<medicaments> findByIdMed(Long idMed);
    Optional<medicaments> findByNom(String nom);
}
