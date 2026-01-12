package ma.TeethCare.service.modules.users.api;

import ma.TeethCare.mvc.dto.admin.AdminDTO;
import ma.TeethCare.service.common.BaseService;

public interface adminService extends BaseService<AdminDTO, Long> {
    java.util.List<AdminDTO> findByDomaine(String domaine) throws Exception;
}
