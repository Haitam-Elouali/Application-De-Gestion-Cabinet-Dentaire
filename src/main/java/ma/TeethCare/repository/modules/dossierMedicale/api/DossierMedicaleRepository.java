package ma.TeethCare.repository.modules.dossierMedicale.api;

import ma.TeethCare.entities.dossierMedicale.dossierMedicale;
import ma.TeethCare.repository.common.CrudRepository;

import java.util.Optional;

public interface DossierMedicaleRepository extends CrudRepository<dossierMedicale, Long> {
    Optional<dossierMedicale> findByIdDM(Long idDM);
}