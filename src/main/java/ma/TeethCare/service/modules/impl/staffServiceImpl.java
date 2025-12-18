package ma.TeethCare.service.modules.impl;

import ma.TeethCare.entities.staff.staff;
import ma.TeethCare.service.modules.api.staffService;
import java.util.List;
import java.util.Optional;

import ma.TeethCare.repository.api.StaffRepository;
import ma.TeethCare.repository.mySQLImpl.StaffRepositoryImpl;

/**
 * @author Salma BAKAROUM
 * @date 2025-12-17
 */

public class staffServiceImpl implements staffService {

    private final StaffRepository staffRepository;

    public staffServiceImpl() {
        this.staffRepository = new StaffRepositoryImpl();
    }

    public staffServiceImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public staff create(staff entity) throws Exception {
        staffRepository.create(entity);
        return entity;
    }

    @Override
    public Optional<staff> findById(Long id) throws Exception {
        return Optional.ofNullable(staffRepository.findById(id));
    }

    @Override
    public List<staff> findAll() throws Exception {
        return staffRepository.findAll();
    }

    @Override
    public staff update(staff entity) throws Exception {
        staffRepository.update(entity);
        return entity;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        staffRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean exists(Long id) throws Exception {
        return staffRepository.findById(id) != null;
    }

    @Override
    public long count() throws Exception {
        return staffRepository.findAll().size();
    }

    @Override
    public Optional<staff> findByEmail(String email) throws Exception {
        return staffRepository.findByEmail(email);
    }

    @Override
    public Optional<staff> findByCin(String cin) throws Exception {
        return staffRepository.findByCin(cin);
    }
}
