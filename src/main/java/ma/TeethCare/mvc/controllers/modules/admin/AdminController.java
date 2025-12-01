package ma.TeethCare.mvc.controllers.admin;

import ma.TeethCare.mvc.dto.admin.AdminDTO;
import java.util.List;

public interface AdminController {
    void create(AdminDTO admin) throws Exception;
    AdminDTO getById(Long id) throws Exception;
    List<AdminDTO> getAll() throws Exception;
    void update(Long id, AdminDTO admin) throws Exception;
    void delete(Long id) throws Exception;
}
