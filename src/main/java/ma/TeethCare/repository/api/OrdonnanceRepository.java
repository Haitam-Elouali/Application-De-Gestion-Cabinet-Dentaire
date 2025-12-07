package ma.TeethCare.repository.api;

import ma.TeethCare.entities.ordonnance.ordonnance;
import ma.TeethCare.repository.common.CrudRepository;

import java.util.Optional;

public interface OrdonnanceRepository extends CrudRepository<ordonnance, Long> {
}
