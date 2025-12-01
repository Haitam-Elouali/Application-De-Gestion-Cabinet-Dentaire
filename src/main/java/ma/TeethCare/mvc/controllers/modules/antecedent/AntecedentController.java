package ma.TeethCare.mvc.controllers.antecedent;

import ma.TeethCare.mvc.dto.antecedent.AntecedentDTO;
import java.util.List;

public interface AntecedentController {
    void create(AntecedentDTO antecedent) throws Exception;
    AntecedentDTO getById(Long id) throws Exception;
    List<AntecedentDTO> getAll() throws Exception;
    void update(Long id, AntecedentDTO antecedent) throws Exception;
    void delete(Long id) throws Exception;
    List<AntecedentDTO> findByDossierMedicaleId(Long dossierMedicaleId) throws Exception;
}
