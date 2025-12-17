package ma.TeethCare.service.modules.impl;

import ma.TeethCare.entities.consultation.consultation;
import ma.TeethCare.repository.api.ConsultationRepository;
import ma.TeethCare.service.modules.api.consultationService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * @author Hamza ALAOUI
 * @date 2025-12-17
 */

public class consultationServiceImpl implements consultationService {

    private final ConsultationRepository consultationRepository;

    public consultationServiceImpl(ConsultationRepository consultationRepository) {
        this.consultationRepository = consultationRepository;
    }

    @Override
    public consultation create(consultation entity) throws Exception {
        if (entity == null) throw new IllegalArgumentException("Consultation is null");

        try {
            consultationRepository.create(entity);
            if (entity.getId() != null) {
                return consultationRepository.findById(entity.getId());
            }
            return entity;

        } catch (Exception e) {
            throw new Exception("Erreur lors de la création de la consultation", e);
        }
    }

    @Override
    public Optional<consultation> findById(Long id) throws Exception {
        if (id == null) return Optional.empty();
        try {
            return Optional.ofNullable(consultationRepository.findById(id));
        } catch (Exception e) {
            throw new Exception("Erreur lors de la recherche de la consultation id=" + id, e);
        }
    }

    @Override
    public List<consultation> findAll() throws Exception {
        try {
            return consultationRepository.findAll();
        } catch (SQLException e) {
            throw new Exception("Erreur SQL lors de la récupération de toutes les consultations", e);
        }
    }

    @Override
    public consultation update(consultation entity) throws Exception {
        if (entity == null) throw new IllegalArgumentException("Consultation is null");
        if (entity.getId() == null && entity.getIdEntite() == null)
            throw new IllegalArgumentException("Consultation id/idEntite is required for update");

        if (entity.getIdEntite() == null && entity.getId() != null) {
            entity.setIdEntite(entity.getId());
        }
        if (entity.getId() == null && entity.getIdEntite() != null) {
            entity.setId(entity.getIdEntite());
        }

        try {
            consultationRepository.update(entity);
            return consultationRepository.findById(entity.getId());
        } catch (Exception e) {
            throw new Exception("Erreur lors de la mise à jour de la consultation id=" + entity.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        if (id == null) return false;

        try {
            if (!exists(id)) return false;
            consultationRepository.deleteById(id);
            return true;

        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression de la consultation id=" + id, e);
        }
    }

    @Override
    public boolean exists(Long id) throws Exception {
        if (id == null) return false;
        try {
            return consultationRepository.findById(id) != null;
        } catch (Exception e) {
            throw new Exception("Erreur lors de la vérification d'existence consultation id=" + id, e);
        }
    }

    @Override
    public long count() throws Exception {
        return findAll().size();
    }
}