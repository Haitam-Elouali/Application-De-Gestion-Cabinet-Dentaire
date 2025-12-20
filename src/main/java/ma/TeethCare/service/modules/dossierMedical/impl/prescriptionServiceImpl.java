package ma.TeethCare.service.modules.dossierMedical.impl;
import ma.TeethCare.entities.prescription.prescription;
import ma.TeethCare.service.modules.dossierMedical.api.prescriptionService;
import ma.TeethCare.repository.api.PrescriptionRepository;
import java.util.List;
import java.util.Optional;

/**
 * @author CHOUKHAIRI Noureddine
 * @date 2025-12-09
 */

public class prescriptionServiceImpl implements prescriptionService {

    private final PrescriptionRepository repository;

    public prescriptionServiceImpl(PrescriptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public prescription create(prescription entity) throws Exception {
        repository.create(entity);
        return entity;
    }

    @Override
    public Optional<prescription> findById(Long id) throws Exception {
        return Optional.ofNullable(repository.findById(id));
    }

    @Override
    public List<prescription> findAll() throws Exception {
        return repository.findAll();
    }

    @Override
    public prescription update(prescription entity) throws Exception {
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
