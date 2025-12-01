package ma.TeethCare.repository.modules.cabinetMedicale;

import ma.TeethCare.entities.cabinetMedicale.cabinetMedicale;
import ma.TeethCare.repository.common.BaseRepository;
import java.util.Optional;

public interface CabinetMedicaleRepository extends BaseRepository<cabinetMedicale, Long> {
    Optional<cabinetMedicale> findByCin(String cin) throws Exception;
    Optional<cabinetMedicale> findByNom(String nom) throws Exception;
}
