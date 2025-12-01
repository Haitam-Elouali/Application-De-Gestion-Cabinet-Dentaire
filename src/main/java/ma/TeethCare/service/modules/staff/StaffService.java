package ma.TeethCare.service.modules.staff;

import ma.TeethCare.entities.staff.staff;
import ma.TeethCare.service.common.BaseService;
import java.util.List;

public interface StaffService extends BaseService<staff, Long> {
    List<staff> findAll() throws Exception;
    List<staff> findByEmail(String email) throws Exception;
}
