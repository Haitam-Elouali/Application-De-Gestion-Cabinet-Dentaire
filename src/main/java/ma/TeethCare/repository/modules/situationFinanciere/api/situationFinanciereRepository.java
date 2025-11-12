package ma.TeethCare.repository.modules.situationFinanciere.api;

import ma.TeethCare.entities.situationFinanciere.situationFinanciere;
import ma.TeethCare.repository.common.CrudRepository;

import java.util.Optional;

public interface situationFinanciereRepository extends CrudRepository<situationFinanciere, Long> {
    Optional<situationFinanciere> findByIdSF(Long idSF);
}