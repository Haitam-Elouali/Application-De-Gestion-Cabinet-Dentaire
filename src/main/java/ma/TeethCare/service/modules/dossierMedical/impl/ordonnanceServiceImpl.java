package ma.TeethCare.service.modules.dossierMedical.impl;
import ma.TeethCare.entities.ordonnance.ordonnance;
import ma.TeethCare.service.modules.dossierMedical.api.ordonnanceService;
import ma.TeethCare.repository.api.OrdonnanceRepository;
import java.util.List;
import java.util.Optional;

/**
 * @author CHOUKHAIRI Noureddine
 * @date 2025-12-09
 */

public class ordonnanceServiceImpl implements ordonnanceService {

    private final OrdonnanceRepository repository;

    public ordonnanceServiceImpl(OrdonnanceRepository repository) {
        this.repository = repository;
    }

    @Override
    public ordonnance create(ordonnance entity) throws Exception {
        repository.create(entity);
        return entity;
    }

    @Override
    public Optional<ordonnance> findById(Long id) throws Exception {
        return Optional.ofNullable(repository.findById(id));
    }

    @Override
    public List<ordonnance> findAll() throws Exception {
        return repository.findAll();
    }

    @Override
    public ordonnance update(ordonnance entity) throws Exception {
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
