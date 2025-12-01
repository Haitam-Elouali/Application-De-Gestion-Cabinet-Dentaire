package ma.TeethCare.service.modules.cabinetMedicale;

import ma.TeethCare.entities.cabinetMedicale.cabinetMedicale;
import ma.TeethCare.service.common.BaseService;
import java.util.Optional;

public interface CabinetMedicaleService extends BaseService<cabinetMedicale, Long> {
    Optional<cabinetMedicale> findByCin(String cin) throws Exception;
    Optional<cabinetMedicale> findByNom(String nom) throws Exception;
}
