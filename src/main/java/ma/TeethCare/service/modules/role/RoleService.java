package ma.TeethCare.service.modules.role;

import ma.TeethCare.entities.role.role;
import ma.TeethCare.service.common.BaseService;
import java.util.Optional;

public interface RoleService extends BaseService<role, Long> {
    Optional<role> findByLibeller(String libeller) throws Exception;
}
