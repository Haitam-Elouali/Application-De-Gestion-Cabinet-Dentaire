package ma.TeethCare.repository.modules.charges.api;

import ma.TeethCare.entities.charges.charges;
import ma.TeethCare.repository.common.CrudRepository;

import java.util.Optional;

public interface ChargesRepository extends CrudRepository<charges, Long> {
    Optional<charges> findByTitre(String titre);
}