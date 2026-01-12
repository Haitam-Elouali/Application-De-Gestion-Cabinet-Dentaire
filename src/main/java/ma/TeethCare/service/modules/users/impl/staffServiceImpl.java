package ma.TeethCare.service.modules.users.impl;

import ma.TeethCare.entities.staff.staff;
import ma.TeethCare.mvc.dto.staff.StaffDTO;
import ma.TeethCare.service.modules.users.api.staffService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ma.TeethCare.repository.api.StaffRepository;
import ma.TeethCare.repository.mySQLImpl.StaffRepositoryImpl;
import ma.TeethCare.service.modules.users.mapper.StaffMapper;

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
    public StaffDTO create(StaffDTO dto) throws Exception {
        staff entity = StaffMapper.toEntity(dto);
        staffRepository.create(entity);
        if(entity.getId() != null) {
            return StaffMapper.toDTO(staffRepository.findById(entity.getId()));
        }
        return StaffMapper.toDTO(entity);
    }

    @Override
    public Optional<StaffDTO> findById(Long id) throws Exception {
        return Optional.ofNullable(StaffMapper.toDTO(staffRepository.findById(id)));
    }

    @Override
    public List<StaffDTO> findAll() throws Exception {
        return staffRepository.findAll().stream()
                .map(StaffMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StaffDTO update(StaffDTO dto) throws Exception {
        staff entity = StaffMapper.toEntity(dto);
        staffRepository.update(entity);
        return StaffMapper.toDTO(staffRepository.findById(entity.getId()));
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
    public Optional<StaffDTO> findByEmail(String email) throws Exception {
        return staffRepository.findByEmail(email).map(StaffMapper::toDTO);
    }

    @Override
    public Optional<StaffDTO> findByCin(String cin) throws Exception {
        return staffRepository.findByCin(cin).map(StaffMapper::toDTO);
    }
}
