package ma.TeethCare.service.modules.utilisateur;

import ma.TeethCare.entities.utilisateur.utilisateur;
import ma.TeethCare.service.common.BaseService;
import java.util.List;
import java.util.Optional;

public interface UtilisateurService extends BaseService<utilisateur, Long> {
    Optional<utilisateur> findByLogin(String login) throws Exception;
    Optional<utilisateur> findByEmail(String email) throws Exception;
    List<utilisateur> findByRoleId(Long roleId) throws Exception;
    List<utilisateur> findByStatut(String statut) throws Exception;
    Optional<utilisateur> authenticate(String login, String password) throws Exception;
}
