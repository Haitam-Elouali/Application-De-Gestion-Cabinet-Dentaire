package ma.TeethCare.service.modules.cabinet.api;

import ma.TeethCare.mvc.dto.cabinetMedicale.CabinetMedicaleDTO;
import ma.TeethCare.service.common.BaseService;

import java.util.Optional;

public interface cabinetMedicaleService extends BaseService<CabinetMedicaleDTO, Long> {
    Optional<CabinetMedicaleDTO> findByEmail(String email) throws Exception;
}
