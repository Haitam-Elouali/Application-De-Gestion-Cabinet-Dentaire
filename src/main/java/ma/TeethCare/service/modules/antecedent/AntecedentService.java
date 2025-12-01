package ma.TeethCare.service.modules.antecedent;

import ma.TeethCare.entities.antecedent.antecedent;
import ma.TeethCare.service.common.BaseService;
import java.util.List;

public interface AntecedentService extends BaseService<antecedent, Long> {
    List<antecedent> findByDossierMedicaleId(Long dossierMedicaleId) throws Exception;
    List<antecedent> findByCategorie(String categorie) throws Exception;
    List<antecedent> findByNiveauRisque(String niveauRisque) throws Exception;
}
