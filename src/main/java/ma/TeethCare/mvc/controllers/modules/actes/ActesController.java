package ma.TeethCare.mvc.controllers.actes;

import ma.TeethCare.entities.actes.actes;
import ma.TeethCare.mvc.dto.actes.ActesDTO;
import java.util.List;

public interface ActesController {
    void create(ActesDTO actes) throws Exception;
    ActesDTO getById(Long id) throws Exception;
    List<ActesDTO> getAll() throws Exception;
    void update(Long id, ActesDTO actes) throws Exception;
    void delete(Long id) throws Exception;
    List<ActesDTO> findByCategorie(String categorie) throws Exception;
    List<ActesDTO> findByCodeSECU(String codeSECU) throws Exception;
}
