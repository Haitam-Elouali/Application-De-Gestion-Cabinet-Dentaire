package ma.TeethCare.service.modules.situationFinanciere;

import ma.TeethCare.entities.situationFinanciere.situationFinanciere;
import ma.TeethCare.service.common.BaseService;
import java.util.Optional;

public interface SituationFinanciereService extends BaseService<situationFinanciere, Long> {
    Optional<situationFinanciere> findByPatientId(Long patientId) throws Exception;
    Double getTotalReste(Long patientId) throws Exception;
}
