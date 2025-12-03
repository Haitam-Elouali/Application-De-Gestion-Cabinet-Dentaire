package ma.TeethCare.repository.modules.interventionMedecin.api;

import ma.TeethCare.entities.interventionMedecin.interventionMedecin;
import ma.TeethCare.repository.common.CrudRepository;

import java.util.Optional;

public interface InterventionMedecinRepository extends CrudRepository<interventionMedecin, Long> {
    Optional<interventionMedecin> findByIdIM(Long idIM);
    List<interventionMedecin> findByMedecinId(Long medecinId) throws Exception;
    List<interventionMedecin> findByConsultationId(Long consultationId) throws Exception;
    List<interventionMedecin> findByActeId(Long acteId) throws Exception;
}