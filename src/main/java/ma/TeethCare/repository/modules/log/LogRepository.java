package ma.TeethCare.repository.modules.log;

import ma.TeethCare.entities.log.log;
import ma.TeethCare.repository.common.BaseRepository;
import java.util.List;

public interface LogRepository extends BaseRepository<log, Long> {
    List<log> findByUtilisateur(String utilisateur) throws Exception;
    List<log> findByAction(String action) throws Exception;
    List<log> findAll() throws Exception;
}
