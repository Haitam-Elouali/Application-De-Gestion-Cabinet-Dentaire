package ma.TeethCare.mvc.controllers.dossierMedicale;

import ma.TeethCare.mvc.dto.dossierMedicale.DossierMedicaleDTO;
import java.util.List;

public interface DossierMedicaleController {
    void create(DossierMedicaleDTO dossier) throws Exception;
    DossierMedicaleDTO getById(Long id) throws Exception;
    List<DossierMedicaleDTO> getAll() throws Exception;
    void update(Long id, DossierMedicaleDTO dossier) throws Exception;
    void delete(Long id) throws Exception;
    DossierMedicaleDTO findByPatientId(Long patientId) throws Exception;
}
