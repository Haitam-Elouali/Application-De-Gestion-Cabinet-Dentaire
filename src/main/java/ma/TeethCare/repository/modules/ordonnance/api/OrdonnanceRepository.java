package ma.TeethCare.repository.modules.ordonnance.api;

import ma.TeethCare.entities.ordonnance.ordonnance;
import ma.TeethCare.repository.common.CrudRepository;

import java.util.Optional;

public interface OrdonnanceRepository extends CrudRepository<ordonnance, Long> {
    Optional<ordonnance> findByIdOrd(Long idOrd);
}