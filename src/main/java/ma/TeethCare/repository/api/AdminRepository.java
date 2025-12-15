package ma.TeethCare.repository.api;

import ma.TeethCare.entities.admin.admin;
import ma.TeethCare.repository.common.CrudRepository;

import java.util.List;

public interface AdminRepository extends CrudRepository<admin, Long> {
    List<admin> findByDomaine(String domaine) throws Exception;
}
