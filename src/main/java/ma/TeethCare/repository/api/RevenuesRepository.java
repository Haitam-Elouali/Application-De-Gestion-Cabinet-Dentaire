package ma.TeethCare.repository.api;

import ma.TeethCare.entities.revenues.revenues;
import ma.TeethCare.repository.common.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RevenuesRepository extends CrudRepository<revenues, Long> {
    Optional<revenues> findByTitre(String titre);
    List<revenues> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
