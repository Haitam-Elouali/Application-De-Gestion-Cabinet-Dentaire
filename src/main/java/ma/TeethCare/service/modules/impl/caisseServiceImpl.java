package ma.TeethCare.service.modules.impl;
import ma.TeethCare.entities.caisse.caisse;
import ma.TeethCare.service.modules.api.caisseService;
import ma.TeethCare.repository.api.CaisseRepository;
import java.util.List;
import java.util.Optional;

/**
 * @author ELOUALI Haitam
 * @date 2025-12-09
 */

public class caisseServiceImpl implements caisseService {

    private final CaisseRepository repository;

    public caisseServiceImpl(CaisseRepository repository) {
        this.repository = repository;
    }

    @Override
    public caisse create(caisse entity) throws Exception {
        repository.create(entity);
        return entity;
    }

    @Override
    public Optional<caisse> findById(Long id) throws Exception {
        return Optional.ofNullable(repository.findById(id));
    }

    @Override
    public List<caisse> findAll() throws Exception {
        return repository.findAll();
    }

    @Override
    public caisse update(caisse entity) throws Exception {
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
