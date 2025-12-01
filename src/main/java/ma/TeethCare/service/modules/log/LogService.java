package ma.TeethCare.service.modules.log;

import ma.TeethCare.entities.log.log;
import ma.TeethCare.service.common.BaseService;
import java.util.List;

public interface LogService extends BaseService<log, Long> {
    List<log> findByUtilisateur(String utilisateur) throws Exception;
    List<log> findByAction(String action) throws Exception;
    void logAction(String utilisateur, String action, String description, String ip) throws Exception;
}
