package ma.TeethCare.repository.api;

import ma.TeethCare.entities.rdv.rdv;
import ma.TeethCare.repository.common.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RdvRepository extends CrudRepository<rdv, Long> {
    Optional<rdv> findByIdRDV(Long idRDV);
    List<rdv> findByDate(LocalDate date);
}
