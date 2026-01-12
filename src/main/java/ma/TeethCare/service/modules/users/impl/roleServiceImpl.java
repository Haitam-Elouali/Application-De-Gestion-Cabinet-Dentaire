package ma.TeethCare.service.modules.users.impl;

import ma.TeethCare.common.exceptions.ServiceException;
import ma.TeethCare.entities.role.role;
import ma.TeethCare.mvc.dto.role.RoleDTO;
import ma.TeethCare.repository.api.RoleRepository;
import ma.TeethCare.service.modules.users.api.roleService;
import ma.TeethCare.service.modules.users.mapper.RoleMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author MOKADAMI Zouhair
 * @date 2025-12-17
 */

public class roleServiceImpl implements roleService {

    private final RoleRepository roleRepository;

    public roleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleDTO create(RoleDTO dto) throws Exception {
        try {
            if (dto == null) {
                throw new ServiceException("Role ne peut pas être null");
            }
            role entity = RoleMapper.toEntity(dto);
            roleRepository.create(entity);
            if(entity.getId() != null) {
                return RoleMapper.toDTO(roleRepository.findById(entity.getId()));
            }
            return RoleMapper.toDTO(entity);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création du role", e);
        }
    }

    @Override
    public Optional<RoleDTO> findById(Long id) throws Exception {
        try {
            if (id == null) {
                throw new ServiceException("ID role ne peut pas être null");
            }
            return Optional.ofNullable(RoleMapper.toDTO(roleRepository.findById(id)));
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération du role", e);
        }
    }

    @Override
    public List<RoleDTO> findAll() throws Exception {
        try {
            return roleRepository.findAll().stream()
                    .map(RoleMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de la liste des roles", e);
        }
    }

    @Override
    public RoleDTO update(RoleDTO dto) throws Exception {
        try {
            if (dto == null) {
                throw new ServiceException("Role ne peut pas être null");
            }
            role entity = RoleMapper.toEntity(dto);
            roleRepository.update(entity);
            return RoleMapper.toDTO(roleRepository.findById(entity.getId()));
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour du role", e);
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        try {
            if (!exists(id)) {
                return false;
            }
            roleRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression du role", e);
        }
    }

    @Override
    public boolean exists(Long id) throws Exception {
        try {
            if (id == null) {
                return false;
            }
            return roleRepository.findById(id) != null;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la vérification d'existence du role", e);
        }
    }

    @Override
    public long count() throws Exception {
        try {
            return roleRepository.findAll().size();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des roles", e);
        }
    }
}
