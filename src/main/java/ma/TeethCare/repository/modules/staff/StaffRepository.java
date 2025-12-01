package ma.TeethCare.repository.modules.staff;

import ma.TeethCare.entities.staff.staff;
import ma.TeethCare.repository.common.BaseRepository;
import java.util.List;

public interface StaffRepository extends BaseRepository<staff, Long> {
    List<staff> findAll() throws Exception;
    List<staff> findByEmail(String email) throws Exception;
}
