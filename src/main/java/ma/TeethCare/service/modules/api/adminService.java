package ma.TeethCare.service.modules.api;

import ma.TeethCare.entities.admin.admin;
import ma.TeethCare.service.common.BaseService;

public interface adminService extends BaseService<admin, Long> {
    java.util.List<admin> findByDomaine(String domaine) throws Exception;
}
