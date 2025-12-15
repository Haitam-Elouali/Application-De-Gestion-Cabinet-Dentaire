package ma.TeethCare.repository.api;

import ma.TeethCare.entities.interventionMedecin.interventionMedecin;
import ma.TeethCare.repository.common.CrudRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface InterventionMedecinRepository extends CrudRepository<interventionMedecin, Long> {
}
