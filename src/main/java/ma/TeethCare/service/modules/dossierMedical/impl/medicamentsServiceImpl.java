package ma.TeethCare.service.modules.dossierMedical.impl;
import ma.TeethCare.entities.medicaments.medicaments;
import ma.TeethCare.service.modules.dossierMedical.api.medicamentsService;
import ma.TeethCare.repository.api.MedicamentRepository;
import java.util.List;
import java.util.Optional;

/**
 * @author CHOUKHAIRI Noureddine
 * @date 2025-12-14
 */

public class medicamentsServiceImpl implements medicamentsService {

    private final MedicamentRepository repository;

    public medicamentsServiceImpl(MedicamentRepository repository) {
        this.repository = repository;
    }

    @Override
    public medicaments create(medicaments entity) throws Exception {
        repository.create(entity);
        return entity;
    }

    @Override
    public Optional<medicaments> findById(Long id) throws Exception {
        return Optional.ofNullable(repository.findById(id));
    }

    @Override
    public List<medicaments> findAll() throws Exception {
        return repository.findAll();
    }

    @Override
    public medicaments update(medicaments entity) throws Exception {
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
