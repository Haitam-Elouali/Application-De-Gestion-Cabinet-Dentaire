package ma.TeethCare.service.modules.impl;
import ma.TeethCare.entities.facture.facture;
import ma.TeethCare.service.modules.api.factureService;
import ma.TeethCare.repository.api.FactureRepository;
import java.util.List;
import java.util.Optional;

/**
 * @author ELOUALI Haitam
 * @date 2025-12-09
 */

public class factureServiceImpl implements factureService {

    private final FactureRepository repository;

    public factureServiceImpl(FactureRepository repository) {
        this.repository = repository;
    }

    @Override
    public facture create(facture entity) throws Exception {
        repository.create(entity);
        return entity;
    }

    @Override
    public Optional<facture> findById(Long id) throws Exception {
        return Optional.ofNullable(repository.findById(id));
    }

    @Override
    public List<facture> findAll() throws Exception {
        return repository.findAll();
    }

    @Override
    public facture update(facture entity) throws Exception {
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
