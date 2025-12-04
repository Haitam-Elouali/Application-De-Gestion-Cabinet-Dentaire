package ma.TeethCare.repository.api;

import ma.TeethCare.entities.staff.staff;
import ma.TeethCare.repository.common.CrudRepository;

import java.util.Optional;

public interface StaffRepository extends CrudRepository<staff, Long> {
    Optional<staff> findByEmail(String email);
    Optional<staff> findByCin(String cin);
}
