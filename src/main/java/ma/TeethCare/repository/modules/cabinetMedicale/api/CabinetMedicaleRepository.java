package ma.TeethCare.repository.modules.cabinetMedicale.api;

import ma.TeethCare.entities.cabinetMedicale.cabinetMedicale;
import ma.TeethCare.repository.common.CrudRepository;

import java.util.Optional;

public interface CabinetMedicaleRepository extends CrudRepository<cabinetMedicale, Long> {
    Optional<cabinetMedicale> findByEmail(String email);
}