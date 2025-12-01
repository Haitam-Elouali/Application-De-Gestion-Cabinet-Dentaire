package ma.TeethCare.repository.modules.antecedent;

import ma.TeethCare.entities.antecedent.antecedent;
import ma.TeethCare.repository.common.BaseRepository;
import java.util.List;

public interface AntecedentRepository extends BaseRepository<antecedent, Long> {
    List<antecedent> findByDossierMedicaleId(Long dossierMedicaleId) throws Exception;
    List<antecedent> findByCategorie(String categorie) throws Exception;
    List<antecedent> findByNiveauRisque(String niveauRisque) throws Exception;
}
