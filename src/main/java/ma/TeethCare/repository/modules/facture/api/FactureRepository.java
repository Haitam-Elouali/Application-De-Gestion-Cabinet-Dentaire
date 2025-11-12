package ma.TeethCare.repository.modules.facture.api;

import ma.TeethCare.entities.facture.facture;
import ma.TeethCare.repository.common.CrudRepository;

import java.util.Optional;

public interface FactureRepository extends CrudRepository<facture, Long> {
    Optional<facture> findByIdFacture(Long idFacture);
}