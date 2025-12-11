package ma.TeethCare.service.modules.impl;

import ma.TeethCare.entities.agenda.agenda;
import ma.TeethCare.repository.api.AgendaRepository;
import ma.TeethCare.service.modules.api.agendaService;

import java.util.List;
import java.util.Optional;

/**
 * @author Hamza ALAOUI
 * @date 2025-12-10
 */

public class agendaServiceImpl implements agendaService {

    private final AgendaRepository repository;

    public agendaServiceImpl(AgendaRepository repository) {
        this.repository = repository;
    }

    @Override
    public agenda create(agenda entity) throws Exception {
        repository.create(entity);
        return entity;
    }

    @Override
    public Optional<agenda> findById(Long id) throws Exception {
        return Optional.ofNullable(repository.findById(id));
    }

    @Override
    public List<agenda> findAll() throws Exception {
        return repository.findAll();
    }

    @Override
    public agenda update(agenda entity) throws Exception {
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