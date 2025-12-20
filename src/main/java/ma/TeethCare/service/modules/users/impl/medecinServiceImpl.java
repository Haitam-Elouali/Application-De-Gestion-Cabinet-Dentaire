package ma.TeethCare.service.modules.users.impl;

import ma.TeethCare.entities.medecin.medecin;
import ma.TeethCare.repository.api.MedecinRepository;
import ma.TeethCare.service.modules.users.api.medecinService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * @author Hamza ALAOUI
 * @date 2025-12-17
 */

public class medecinServiceImpl implements medecinService {

    private final MedecinRepository medecinRepository;

    public medecinServiceImpl(MedecinRepository medecinRepository) {
        this.medecinRepository = medecinRepository;
    }

    @Override
    public medecin create(medecin entity) throws Exception {
        if (entity == null) throw new IllegalArgumentException("Medecin is null");

        medecinRepository.create(entity);

        if (entity.getId() != null) {
            return medecinRepository.findById(entity.getId());
        }
        return entity;

    }

    @Override
    public Optional<medecin> findById(Long id) throws Exception {
        if (id == null) return Optional.empty();
        try {
            return Optional.ofNullable(medecinRepository.findById(id));
        } catch (Exception e) {
            throw new Exception("Erreur lors de la recherche du médecin par id=" + id, e);
        }
    }

    @Override
    public List<medecin> findAll() throws Exception {
        try {
            return medecinRepository.findAll();
        } catch (SQLException e) {
            throw new Exception("Erreur SQL lors de la récupération de tous les médecins", e);
        }
    }

    @Override
    public medecin update(medecin entity) throws Exception {
        if (entity == null) throw new IllegalArgumentException("Medecin is null");
        if (entity.getId() == null && entity.getIdEntite() == null)
            throw new IllegalArgumentException("Medecin id/idEntite is required for update");

        if (entity.getIdEntite() == null && entity.getId() != null) {
            entity.setIdEntite(entity.getId());
        }
        if (entity.getId() == null && entity.getIdEntite() != null) {
            entity.setId(entity.getIdEntite());
        }

        try {
            medecinRepository.update(entity);
            return medecinRepository.findById(entity.getId());
        } catch (Exception e) {
            throw new Exception("Erreur lors de la mise à jour du médecin id=" + entity.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        if (id == null) return false;

        try {
            if (!exists(id)) return false;

            medecinRepository.deleteById(id); // repo supprime via entite -> cascade attendu
            return true;

        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression du médecin id=" + id, e);
        }
    }

    @Override
    public boolean exists(Long id) throws Exception {
        if (id == null) return false;
        try {
            return medecinRepository.findById(id) != null;
        } catch (Exception e) {
            throw new Exception("Erreur lors de la vérification d'existence du médecin id=" + id, e);
        }
    }

    @Override
    public long count() throws Exception {
        return findAll().size();
    }
}
