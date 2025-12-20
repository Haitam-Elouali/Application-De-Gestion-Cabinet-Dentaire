package ma.TeethCare.service.modules.users.api;

import ma.TeethCare.entities.staff.staff;
import ma.TeethCare.service.common.BaseService;

import java.util.Optional;

public interface staffService extends BaseService<staff, Long> {
    Optional<staff> findByEmail(String email) throws Exception;

    Optional<staff> findByCin(String cin) throws Exception;
}
