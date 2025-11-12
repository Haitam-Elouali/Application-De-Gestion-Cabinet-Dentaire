package ma.TeethCare.repository.modules.antecedent.api;

import ma.TeethCare.entities.antecedent.antecedent;
import ma.TeethCare.repository.common.CrudRepository;

import java.util.List;

public interface AntecedentRepository extends CrudRepository<antecedent, Long> {
    List<antecedent> findByCategorie(String categorie);
}