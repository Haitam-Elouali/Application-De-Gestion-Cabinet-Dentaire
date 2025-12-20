package ma.TeethCare.service.modules.dossierMedical.impl;

import ma.TeethCare.entities.antecedent.antecedent;
import ma.TeethCare.repository.api.AntecedentRepository;
import ma.TeethCare.service.modules.dossierMedical.api.antecedentService;

import java.util.List;
import java.util.Optional;

/**
 * @author MOKADAMI Zouhair
 * @date 2025-12-17
 */

public class antecedentServiceImpl implements antecedentService {

    private final AntecedentRepository antecedentRepository;

    public antecedentServiceImpl(AntecedentRepository antecedentRepository) {
        this.antecedentRepository = antecedentRepository;
    }

    @Override
    public antecedent create(antecedent entity) throws Exception {
        if (entity == null) {
            throw new IllegalArgumentException("Antecedent ne peut pas être null");
        }
        antecedentRepository.create(entity);
        return entity;
    }

    @Override
    public Optional<antecedent> findById(Long id) throws Exception {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(antecedentRepository.findById(id));
    }

    @Override
    public List<antecedent> findAll() throws Exception {
        return antecedentRepository.findAll();
    }

    @Override
    public antecedent update(antecedent entity) throws Exception {
        if (entity == null || entity.getId() == null) {
            throw new IllegalArgumentException("Antecedent ou ID invalide");
        }
        antecedentRepository.update(entity);
        return entity;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        if (id == null) {
            return false;
        }
        antecedentRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean exists(Long id) throws Exception {
        if (id == null) {
            return false;
        }
        return antecedentRepository.findById(id) != null;
    }

    @Override
    public long count() throws Exception {
        return antecedentRepository.findAll().size();
    }
}
