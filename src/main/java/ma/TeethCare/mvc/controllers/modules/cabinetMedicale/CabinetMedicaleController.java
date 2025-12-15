package ma.TeethCare.mvc.controllers.cabinetMedicale;

import ma.TeethCare.mvc.dto.cabinetMedicale.CabinetMedicaleDTO;
import java.util.List;

public interface CabinetMedicaleController {
    void create(CabinetMedicaleDTO cabinet) throws Exception;
    CabinetMedicaleDTO getById(Long id) throws Exception;
    List<CabinetMedicaleDTO> getAll() throws Exception;
    void update(Long id, CabinetMedicaleDTO cabinet) throws Exception;
    void delete(Long id) throws Exception;
}
