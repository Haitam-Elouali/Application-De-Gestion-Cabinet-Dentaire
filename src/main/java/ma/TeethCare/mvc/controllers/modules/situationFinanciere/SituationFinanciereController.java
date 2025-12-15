package ma.TeethCare.mvc.controllers.situationFinanciere;

import ma.TeethCare.mvc.dto.situationFinanciere.SituationFinanciereDTO;
import java.util.List;

public interface SituationFinanciereController {
    void create(SituationFinanciereDTO situation) throws Exception;
    SituationFinanciereDTO getById(Long id) throws Exception;
    List<SituationFinanciereDTO> getAll() throws Exception;
    void update(Long id, SituationFinanciereDTO situation) throws Exception;
    void delete(Long id) throws Exception;
    SituationFinanciereDTO findByPatientId(Long patientId) throws Exception;
}
