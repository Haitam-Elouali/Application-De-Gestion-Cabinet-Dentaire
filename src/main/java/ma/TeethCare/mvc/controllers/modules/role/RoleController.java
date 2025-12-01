package ma.TeethCare.mvc.controllers.role;

import ma.TeethCare.mvc.dto.role.RoleDTO;
import java.util.List;

public interface RoleController {
    void create(RoleDTO role) throws Exception;
    RoleDTO getById(Long id) throws Exception;
    List<RoleDTO> getAll() throws Exception;
    void update(Long id, RoleDTO role) throws Exception;
    void delete(Long id) throws Exception;
}
