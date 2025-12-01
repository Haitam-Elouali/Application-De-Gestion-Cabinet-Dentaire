package ma.TeethCare.repository.modules.utilisateur;

import ma.TeethCare.entities.utilisateur.utilisateur;
import ma.TeethCare.repository.common.BaseRepository;
import java.util.Optional;
import java.util.List;

/**
 * Repository pour l'entité Utilisateur
 * Gère les opérations CRUD sur les utilisateurs
 */
public interface UtilisateurRepository extends BaseRepository<utilisateur, Long> {

    /**
     * Trouve un utilisateur par login
     */
    Optional<utilisateur> findByLogin(String login) throws Exception;

    /**
     * Trouve un utilisateur par email
     */
    Optional<utilisateur> findByEmail(String email) throws Exception;

    /**
     * Trouve les utilisateurs par rôle
     */
    List<utilisateur> findByRoleId(Long roleId) throws Exception;

    /**
     * Trouve les utilisateurs par statut
     */
    List<utilisateur> findByStatut(String statut) throws Exception;

    /**
     * Authentifie un utilisateur
     */
    Optional<utilisateur> authenticate(String login, String password) throws Exception;
}
