package ma.TeethCare.repository.modules.utilisateur.api;

import ma.TeethCare.entities.utilisateur.utilisateur;
import ma.TeethCare.repository.common.CrudRepository;

import java.util.Optional;

public interface UtilisateurRepository extends CrudRepository<utilisateur, Long> {
    Optional<utilisateur> findByEmail(String email);
    Optional<utilisateur> findByLogin(String login);
}