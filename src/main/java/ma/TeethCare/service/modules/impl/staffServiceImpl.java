package ma.TeethCare.service.modules.impl;
import ma.TeethCare.entities.staff.staff;
import ma.TeethCare.service.modules.api.staffService;
import java.util.List;
import java.util.Optional;

import ma.TeethCare.repository.api.StaffRepository;

public class staffServiceImpl implements staffService {

    private StaffRepository staffRepository;

    public staffServiceImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public staff create(staff entity) throws Exception {
        // TODO: Implement method
        return null;
    }

    @Override
    public Optional<staff> findById(Long id) throws Exception {
        // TODO: Implement method
        return Optional.empty();
    }

    @Override
    public List<staff> findAll() throws Exception {
        // TODO: Implement method
        return null;
    }

    @Override
    public staff update(staff entity) throws Exception {
        // TODO: Implement method
        return null;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        // TODO: Implement method
        return false;
    }

    @Override
    public boolean exists(Long id) throws Exception {
        // TODO: Implement method
        return false;
    }

    @Override
    public long count() throws Exception {
        // TODO: Implement method
        return 0;
    }
}
