package ma.TeethCare.repository.modules.role;

import ma.TeethCare.entities.role.role;
import ma.TeethCare.repository.common.BaseRepository;
import java.util.Optional;

public interface RoleRepository extends BaseRepository<role, Long> {
    Optional<role> findByLibeller(String libeller) throws Exception;
}
