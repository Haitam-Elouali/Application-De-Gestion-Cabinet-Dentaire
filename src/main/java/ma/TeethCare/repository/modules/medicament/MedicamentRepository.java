package ma.TeethCare.repository.modules.medicament;

import ma.TeethCare.entities.medicaments.medicament;
import ma.TeethCare.repository.common.BaseRepository;
import java.util.List;
import java.util.Optional;

public interface MedicamentRepository extends BaseRepository<medicament, Long> {
    Optional<medicament> findByNom(String nom) throws Exception;
    List<medicament> findByLaboratoire(String laboratoire) throws Exception;
    List<medicament> findByRemboursable(Boolean remboursable) throws Exception;
}
