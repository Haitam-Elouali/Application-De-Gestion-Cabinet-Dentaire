package ma.TeethCare.service.modules.admin;

import ma.TeethCare.entities.admin.admin;
import ma.TeethCare.service.common.BaseService;
import java.util.List;

public interface AdminService extends BaseService<admin, Long> {
    List<admin> findByDomaine(String domaine) throws Exception;
    List<admin> findAll() throws Exception;
}
