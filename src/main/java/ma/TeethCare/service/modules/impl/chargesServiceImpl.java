package ma.TeethCare.service.modules.impl;
import ma.TeethCare.entities.charges.charges;
import ma.TeethCare.service.modules.api.chargesService;
import ma.TeethCare.repository.api.ChargesRepository;
import java.util.List;
import java.util.Optional;

/**
 * @author Haitam ELOUALI
 * @date 2025-12-14
 */

public class chargesServiceImpl implements chargesService {

    private final ChargesRepository repository;

    public chargesServiceImpl(ChargesRepository repository) {
        this.repository = repository;
    }

    @Override
    public charges create(charges entity) throws Exception {
        repository.create(entity);
        return entity;
    }

    @Override
    public Optional<charges> findById(Long id) throws Exception {
        return Optional.ofNullable(repository.findById(id));
    }

    @Override
    public List<charges> findAll() throws Exception {
        return repository.findAll();
    }

    @Override
    public charges update(charges entity) throws Exception {
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
