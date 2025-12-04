package ma.TeethCare.repository.api;

import ma.TeethCare.entities.role.role;
import ma.TeethCare.entities.enums.Libeller;
import ma.TeethCare.repository.common.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<role, Long> {
    Optional<role> findByIdRole(Long idRole);
    Optional<role> findByLibeller(Libeller libeller);
}
