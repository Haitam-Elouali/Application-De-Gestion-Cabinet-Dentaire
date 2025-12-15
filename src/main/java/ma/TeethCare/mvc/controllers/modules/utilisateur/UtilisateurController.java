package ma.TeethCare.mvc.controllers.utilisateur;

import ma.TeethCare.mvc.dto.utilisateur.UtilisateurDTO;
import java.util.List;

public interface UtilisateurController {
    void create(UtilisateurDTO utilisateur) throws Exception;
    UtilisateurDTO getById(Long id) throws Exception;
    List<UtilisateurDTO> getAll() throws Exception;
    void update(Long id, UtilisateurDTO utilisateur) throws Exception;
    void delete(Long id) throws Exception;
    UtilisateurDTO findByLogin(String login) throws Exception;
    List<UtilisateurDTO> findByRoleId(Long roleId) throws Exception;
}
