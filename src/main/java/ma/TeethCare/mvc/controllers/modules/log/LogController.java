package ma.TeethCare.mvc.controllers.log;

import ma.TeethCare.mvc.dto.log.LogDTO;
import java.util.List;

public interface LogController {
    LogDTO getById(Long id) throws Exception;
    List<LogDTO> getAll() throws Exception;
    List<LogDTO> findByUtilisateur(String utilisateur) throws Exception;
    List<LogDTO> findByAction(String action) throws Exception;
}
