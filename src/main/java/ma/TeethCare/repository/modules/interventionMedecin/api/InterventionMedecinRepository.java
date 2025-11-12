package ma.TeethCare.repository.modules.interventionMedecin.api;

import ma.TeethCare.entities.interventionMedecin.interventionMedecin;
import ma.TeethCare.repository.common.CrudRepository;

import java.util.Optional;

public interface InterventionMedecinRepository extends CrudRepository<interventionMedecin, Long> {
    Optional<interventionMedecin> findByIdIM(Long idIM);
}