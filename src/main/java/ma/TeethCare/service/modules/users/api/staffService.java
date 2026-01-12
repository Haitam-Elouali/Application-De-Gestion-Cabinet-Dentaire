package ma.TeethCare.service.modules.users.api;

import ma.TeethCare.mvc.dto.staff.StaffDTO;
import ma.TeethCare.service.common.BaseService;

import java.util.Optional;

public interface staffService extends BaseService<StaffDTO, Long> {
    Optional<StaffDTO> findByEmail(String email) throws Exception;

    Optional<StaffDTO> findByCin(String cin) throws Exception;
}
