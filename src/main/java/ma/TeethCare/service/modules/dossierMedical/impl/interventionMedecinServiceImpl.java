package ma.TeethCare.service.modules.dossierMedical.impl;

import ma.TeethCare.entities.interventionMedecin.interventionMedecin;
import ma.TeethCare.repository.api.InterventionMedecinRepository;
import ma.TeethCare.service.modules.dossierMedical.api.interventionMedecinService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * @author Hamza ALAOUI
 * @date 2025-12-17
 */

public class interventionMedecinServiceImpl implements interventionMedecinService {

    private final InterventionMedecinRepository interventionMedecinRepository;

    public interventionMedecinServiceImpl(InterventionMedecinRepository interventionMedecinRepository) {
        this.interventionMedecinRepository = interventionMedecinRepository;
    }

    @Override
    public interventionMedecin create(interventionMedecin entity) throws Exception {
        if (entity == null) throw new IllegalArgumentException("InterventionMedecin is null");

        try {
            interventionMedecinRepository.create(entity);
            if (entity.getId() != null) {
                return interventionMedecinRepository.findById(entity.getId());
            }
            return entity;

        } catch (Exception e) {
            throw new Exception("Erreur lors de la création de l'intervention médecin", e);
        }
    }

    @Override
    public Optional<interventionMedecin> findById(Long id) throws Exception {
        if (id == null) return Optional.empty();
        try {
            return Optional.ofNullable(interventionMedecinRepository.findById(id));
        } catch (Exception e) {
            throw new Exception("Erreur lors de la recherche de l'intervention médecin id=" + id, e);
        }
    }

    @Override
    public List<interventionMedecin> findAll() throws Exception {
        try {
            return interventionMedecinRepository.findAll();
        } catch (SQLException e) {
            throw new Exception("Erreur SQL lors de la récupération de toutes les interventions médecin", e);
        }
    }

    @Override
    public interventionMedecin update(interventionMedecin entity) throws Exception {
        if (entity == null) throw new IllegalArgumentException("InterventionMedecin is null");
        if (entity.getId() == null && entity.getIdEntite() == null)
            throw new IllegalArgumentException("InterventionMedecin id/idEntite is required for update");

        if (entity.getIdEntite() == null && entity.getId() != null) {
            entity.setIdEntite(entity.getId());
        }
        if (entity.getId() == null && entity.getIdEntite() != null) {
            entity.setId(entity.getIdEntite());
        }

        try {
            interventionMedecinRepository.update(entity);
            return interventionMedecinRepository.findById(entity.getId());
        } catch (Exception e) {
            throw new Exception("Erreur lors de la mise à jour de l'intervention médecin id=" + entity.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        if (id == null) return false;

        try {
            if (!exists(id)) return false;
            interventionMedecinRepository.deleteById(id);
            return true;

        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression de l'intervention médecin id=" + id, e);
        }
    }

    @Override
    public boolean exists(Long id) throws Exception {
        if (id == null) return false;
        try {
            return interventionMedecinRepository.findById(id) != null;
        } catch (Exception e) {
            throw new Exception("Erreur lors de la vérification d'existence intervention médecin id=" + id, e);
        }
    }

    @Override
    public long count() throws Exception {
        return findAll().size();
    }
}
