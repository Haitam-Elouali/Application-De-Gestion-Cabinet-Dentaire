package ma.TeethCare.repository.api;

import ma.TeethCare.entities.medecin.medecin;
import ma.TeethCare.repository.common.CrudRepository;

import java.util.Optional;

public interface MedecinRepository extends CrudRepository<medecin, Long> {
    Optional<medecin> findByCin(String cin);
    Optional<medecin> findByEmail(String email);
}
