package ma.TeethCare.repository.modules.admin;

import ma.TeethCare.entities.admin.admin;
import ma.TeethCare.repository.common.BaseRepository;
import java.util.List;

public interface AdminRepository extends BaseRepository<admin, Long> {
    List<admin> findByDomaine(String domaine) throws Exception;
    List<admin> findAll() throws Exception;
}
