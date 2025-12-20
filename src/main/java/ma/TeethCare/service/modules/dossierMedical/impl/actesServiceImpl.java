package ma.TeethCare.service.modules.dossierMedical.impl;

import ma.TeethCare.entities.actes.actes;
import ma.TeethCare.repository.api.ActesRepository;
import ma.TeethCare.service.modules.dossierMedical.api.actesService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * @author Hamza ALAOUI
 * @date 2025-12-17
 */

public class actesServiceImpl implements actesService {

    private final ActesRepository actesRepository;

    public actesServiceImpl(ActesRepository actesRepository) {
        this.actesRepository = actesRepository;
    }

    @Override
    public actes create(actes entity) throws Exception {
        if (entity == null) throw new IllegalArgumentException("Acte is null");

        try {
            actesRepository.create(entity);
            if (entity.getIdEntite() != null) {
                return actesRepository.findById(entity.getIdEntite());
            }
            return entity;

        } catch (Exception e) {
            throw new Exception("Erreur lors de la création de l'acte", e);
        }
    }

    @Override
    public Optional<actes> findById(Long id) throws Exception {
        if (id == null) return Optional.empty();
        try {
            return Optional.ofNullable(actesRepository.findById(id));
        } catch (Exception e) {
            throw new Exception("Erreur lors de la recherche de l'acte id=" + id, e);
        }
    }

    @Override
    public List<actes> findAll() throws Exception {
        try {
            return actesRepository.findAll();
        } catch (SQLException e) {
            throw new Exception("Erreur SQL lors de la récupération de tous les actes", e);
        }
    }

    @Override
    public actes update(actes entity) throws Exception {
        if (entity == null) throw new IllegalArgumentException("Acte is null");
        if (entity.getIdEntite() == null)
            throw new IllegalArgumentException("Acte idEntite is required for update");

        try {
            actesRepository.update(entity);
            return actesRepository.findById(entity.getIdEntite());
        } catch (Exception e) {
            throw new Exception("Erreur lors de la mise à jour de l'acte id=" + entity.getIdEntite(), e);
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        if (id == null) return false;

        try {
            if (!exists(id)) return false;
            actesRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression de l'acte id=" + id, e);
        }
    }

    @Override
    public boolean exists(Long id) throws Exception {
        if (id == null) return false;
        try {
            return actesRepository.findById(id) != null;
        } catch (Exception e) {
            throw new Exception("Erreur lors de la vérification d'existence de l'acte id=" + id, e);
        }
    }

    @Override
    public long count() throws Exception {
        return findAll().size();
    }
}
