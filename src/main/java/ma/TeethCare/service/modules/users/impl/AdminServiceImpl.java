package ma.TeethCare.service.modules.users.impl;

import ma.TeethCare.entities.admin.admin;
import ma.TeethCare.repository.api.AdminRepository;
import ma.TeethCare.repository.mySQLImpl.AdminRepositoryImpl;
import ma.TeethCare.service.modules.users.api.adminService;

import java.util.List;
import java.util.Optional;

/**
 * @author Salma BAKAROUM
 * @date 2025-12-10
 */

public class AdminServiceImpl implements adminService {

    private final AdminRepository adminRepository;

    public AdminServiceImpl() {
        this.adminRepository = new AdminRepositoryImpl();
    }

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public admin create(admin entity) throws Exception {
        adminRepository.create(entity);
        return entity;
    }

    @Override
    public Optional<admin> findById(Long id) throws Exception {
        return Optional.ofNullable(adminRepository.findById(id));
    }

    @Override
    public List<admin> findAll() throws Exception {
        return adminRepository.findAll();
    }

    @Override
    public admin update(admin entity) throws Exception {
        adminRepository.update(entity);
        return entity;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        adminRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean exists(Long id) throws Exception {
        return adminRepository.findById(id) != null;
    }

    @Override
    public long count() throws Exception {
        return adminRepository.findAll().size();
    }

    @Override
    public List<admin> findByDomaine(String domaine) throws Exception {
        return adminRepository.findByDomaine(domaine);
    }
}
