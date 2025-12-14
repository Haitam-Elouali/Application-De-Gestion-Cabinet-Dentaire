package ma.TeethCare.service.modules.impl;
import ma.TeethCare.entities.situationFinanciere.situationFinanciere;
import ma.TeethCare.service.modules.api.situationFinanciereService;
import ma.TeethCare.repository.api.SituationFinanciereRepository;
import java.util.List;
import java.util.Optional;

/**
 * @author Haitam ELOUALI
 * @date 2025-12-14
 */

public class situationFinanciereServiceImpl implements situationFinanciereService {

    private final SituationFinanciereRepository repository;

    public situationFinanciereServiceImpl(SituationFinanciereRepository repository) {
        this.repository = repository;
    }

    @Override
    public situationFinanciere create(situationFinanciere entity) throws Exception {
        repository.create(entity);
        return entity;
    }

    @Override
    public Optional<situationFinanciere> findById(Long id) throws Exception {
        return Optional.ofNullable(repository.findById(id));
    }

    @Override
    public List<situationFinanciere> findAll() throws Exception {
        return repository.findAll();
    }

    @Override
    public situationFinanciere update(situationFinanciere entity) throws Exception {
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
