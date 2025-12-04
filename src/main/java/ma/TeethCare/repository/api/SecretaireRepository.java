package ma.TeethCare.repository.api;

import ma.TeethCare.entities.secretaire.secretaire;
import ma.TeethCare.repository.common.CrudRepository;

import java.util.Optional;

public interface SecretaireRepository extends CrudRepository<secretaire, Long> {
    Optional<secretaire> findByNumCNSS(String numCNSS);
    Optional<secretaire> findByCin(String cin);
}
