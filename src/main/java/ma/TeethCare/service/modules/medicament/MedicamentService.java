package ma.TeethCare.service.modules.medicament;

import ma.TeethCare.entities.medicaments.medicament;
import ma.TeethCare.service.common.BaseService;
import java.util.List;
import java.util.Optional;

public interface MedicamentService extends BaseService<medicament, Long> {
    Optional<medicament> findByNom(String nom) throws Exception;
    List<medicament> findByLaboratoire(String laboratoire) throws Exception;
    List<medicament> findByRemboursable(Boolean remboursable) throws Exception;
}
