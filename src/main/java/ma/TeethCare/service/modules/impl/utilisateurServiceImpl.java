package ma.TeethCare.service.modules.impl;

import ma.TeethCare.entities.utilisateur.utilisateur;
import ma.TeethCare.repository.api.UtilisateurRepository;
import ma.TeethCare.service.modules.api.utilisateurService;
import ma.TeethCare.common.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * @author Zouhair MOKADAMI
 * @date 2025-12-10
 */

public class utilisateurServiceImpl implements utilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    // Injection du repository (nécessaire pour fonctionner)
    public utilisateurServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public utilisateur create(utilisateur entity) throws Exception {
        try {
            if (entity == null) {
                throw new ServiceException("Utilisateur ne peut pas être null");
            }

            utilisateurRepository.create(entity);
            return entity;

        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création de l'utilisateur", e);
        }
    }

    @Override
    public Optional<utilisateur> findById(Long id) throws Exception {
        try {
            if (id == null) {
                throw new ServiceException("ID ne peut pas être null");
            }

            return Optional.ofNullable(utilisateurRepository.findById(id));

        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de l'utilisateur", e);
        }
    }

    @Override
    public List<utilisateur> findAll() throws Exception {
        try {
            return utilisateurRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de la liste des utilisateurs", e);
        }
    }

    @Override
    public utilisateur update(utilisateur entity) throws Exception {
        try {
            if (entity == null) {
                throw new ServiceException("Utilisateur ne peut pas être null");
            }

            utilisateurRepository.update(entity);
            return entity;

        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour de l'utilisateur", e);
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        try {
            if (!exists(id)) {
                return false;
            }

            utilisateurRepository.deleteById(id);
            return true;

        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression de l'utilisateur", e);
        }
    }

    @Override
    public boolean exists(Long id) throws Exception {
        try {
            if (id == null) {
                return false;
            }

            return utilisateurRepository.findById(id) != null;

        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la vérification de l'existence de l'utilisateur", e);
        }
    }

    @Override
    public long count() throws Exception {
        try {
            return utilisateurRepository.findAll().size();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des utilisateurs", e);
        }
    }
}
