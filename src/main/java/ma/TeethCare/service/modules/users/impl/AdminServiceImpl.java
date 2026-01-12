package ma.TeethCare.service.modules.users.impl;

import ma.TeethCare.entities.admin.admin;
import ma.TeethCare.mvc.dto.admin.AdminDTO;
import ma.TeethCare.repository.api.AdminRepository;
import ma.TeethCare.repository.mySQLImpl.AdminRepositoryImpl;
import ma.TeethCare.service.modules.users.api.adminService;
import ma.TeethCare.service.modules.users.mapper.AdminMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public AdminDTO create(AdminDTO dto) throws Exception {
        admin entity = AdminMapper.toEntity(dto);
        adminRepository.create(entity);
        if(entity.getId() != null) {
            return AdminMapper.toDTO(adminRepository.findById(entity.getId()));
        }
        return AdminMapper.toDTO(entity);
    }

    @Override
    public Optional<AdminDTO> findById(Long id) throws Exception {
        return Optional.ofNullable(AdminMapper.toDTO(adminRepository.findById(id)));
    }

    @Override
    public List<AdminDTO> findAll() throws Exception {
        return adminRepository.findAll().stream()
                .map(AdminMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AdminDTO update(AdminDTO dto) throws Exception {
        admin entity = AdminMapper.toEntity(dto);
        adminRepository.update(entity);
        return AdminMapper.toDTO(adminRepository.findById(entity.getId()));
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
    public List<AdminDTO> findByDomaine(String domaine) throws Exception {
        return adminRepository.findByDomaine(domaine).stream()
                .map(AdminMapper::toDTO)
                .collect(Collectors.toList());
    }
}
