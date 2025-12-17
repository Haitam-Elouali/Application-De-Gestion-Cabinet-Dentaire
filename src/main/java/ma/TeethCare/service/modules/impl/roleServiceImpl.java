package ma.TeethCare.service.modules.impl;

import ma.TeethCare.common.exceptions.ServiceException;
import ma.TeethCare.entities.role.role;
import ma.TeethCare.repository.api.RoleRepository;
import ma.TeethCare.service.modules.api.roleService;

import java.util.List;
import java.util.Optional;

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
    public role create(role entity) throws Exception {
        try {
            if (entity == null) {
                throw new ServiceException("Role ne peut pas être null");
            }
            roleRepository.create(entity);
            return entity;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création du role", e);
        }
    }

    @Override
    public Optional<role> findById(Long id) throws Exception {
        try {
            if (id == null) {
                throw new ServiceException("ID role ne peut pas être null");
            }
            return Optional.ofNullable(roleRepository.findById(id));
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération du role", e);
        }
    }

    @Override
    public List<role> findAll() throws Exception {
        try {
            return roleRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de la liste des roles", e);
        }
    }

    @Override
    public role update(role entity) throws Exception {
        try {
            if (entity == null) {
                throw new ServiceException("Role ne peut pas être null");
            }
            roleRepository.update(entity);
            return entity;
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
