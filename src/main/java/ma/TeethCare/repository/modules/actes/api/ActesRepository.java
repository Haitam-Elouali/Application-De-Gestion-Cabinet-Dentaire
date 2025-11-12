package ma.TeethCare.repository.modules.actes.api;

import ma.TeethCare.entities.actes.actes;
import ma.TeethCare.repository.common.CrudRepository;

import java.util.List;


public interface ActesRepository extends CrudRepository<actes, Long> {

    List<actes> findByCategorie(String categorie);

}