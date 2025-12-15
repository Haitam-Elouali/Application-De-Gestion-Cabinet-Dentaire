package ma.TeethCare.mvc.controllers.staff;

import ma.TeethCare.mvc.dto.staff.StaffDTO;
import java.util.List;

public interface StaffController {
    void create(StaffDTO staff) throws Exception;
    StaffDTO getById(Long id) throws Exception;
    List<StaffDTO> getAll() throws Exception;
    void update(Long id, StaffDTO staff) throws Exception;
    void delete(Long id) throws Exception;
}
