package ma.TeethCare.repository.modules.auth.api;

import ma.TeethCare.entities.auth.auth;
import ma.TeethCare.repository.common.CrudRepository;

import java.util.Optional;

public interface AuthRepository extends CrudRepository<auth, Long> {
    Optional<auth> findByEmail(String email);
}