package ma.TeethCare.repository.api;

import ma.TeethCare.entities.charges.charges;
import ma.TeethCare.repository.common.CrudRepository;

import java.util.Optional;
import java.time.LocalDateTime;

public interface ChargesRepository extends CrudRepository<charges, Long> {
    Optional<charges> findByTitre(String titre);
    
    // Aggregation Methods
    Double calculateTotalAmount(LocalDateTime startDate, LocalDateTime endDate);
    java.util.Map<Integer, Double> groupTotalByMonth(int year);
}
