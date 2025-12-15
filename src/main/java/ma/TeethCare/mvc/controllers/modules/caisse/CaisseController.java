package ma.TeethCare.mvc.controllers.caisse;

import ma.TeethCare.mvc.dto.caisse.CaisseDTO;
import java.util.List;

public interface CaisseController {
    void create(CaisseDTO caisse) throws Exception;
    CaisseDTO getById(Long id) throws Exception;
    List<CaisseDTO> getAll() throws Exception;
    void update(Long id, CaisseDTO caisse) throws Exception;
    void delete(Long id) throws Exception;
    List<CaisseDTO> findByFactureId(Long factureId) throws Exception;
    List<CaisseDTO> findByModeEncaissement(String mode) throws Exception;
    Double getTotalEncaisse() throws Exception;
}
