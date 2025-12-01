package ma.TeethCare.repository.modules.situationFinanciere;

import ma.TeethCare.entities.situationFinanciere.situationFinanciere;
import ma.TeethCare.repository.common.BaseRepository;
import java.util.Optional;

public interface SituationFinanciereRepository extends BaseRepository<situationFinanciere, Long> {
    Optional<situationFinanciere> findByPatientId(Long patientId) throws Exception;
    Double getTotalReste(Long patientId) throws Exception;
}
