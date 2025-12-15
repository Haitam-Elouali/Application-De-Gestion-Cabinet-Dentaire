package ma.TeethCare.mvc.controllers.secretaire;

import ma.TeethCare.mvc.dto.secretaire.SecretaireDTO;
import java.util.List;

public interface SecretaireController {
    void create(SecretaireDTO secretaire) throws Exception;
    SecretaireDTO getById(Long id) throws Exception;
    List<SecretaireDTO> getAll() throws Exception;
    void update(Long id, SecretaireDTO secretaire) throws Exception;
    void delete(Long id) throws Exception;
}
