package ma.TeethCare.service.modules.agenda.impl;

import ma.TeethCare.entities.rdv.rdv;
import ma.TeethCare.repository.api.RdvRepository;
import ma.TeethCare.service.modules.agenda.api.rdvService;

import java.util.List;
import java.util.Optional;

/**
 * @author Hamza ALAOUI
 * @date 2025-12-10
 */

public class rdvServiceImpl implements rdvService {

    private final RdvRepository repository;

    public rdvServiceImpl(RdvRepository repository) {
        this.repository = repository;
    }

    @Override
    public rdv create(rdv entity) throws Exception {
        repository.create(entity);
        return entity;
    }

    @Override
    public Optional<rdv> findById(Long id) throws Exception {
        return Optional.ofNullable(repository.findById(id));
    }

    @Override
    public List<rdv> findAll() throws Exception {
        return repository.findAll();
    }

    @Override
    public rdv update(rdv entity) throws Exception {
        repository.update(entity);
        return entity;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        repository.deleteById(id);
        return true;
    }

    @Override
    public boolean exists(Long id) throws Exception {
        return repository.findById(id) != null;
    }

    @Override
    public long count() throws Exception {
        return repository.findAll().size();
    }
}
