package ma.TeethCare.service.modules.impl;
import ma.TeethCare.entities.certificat.certificat;
import ma.TeethCare.service.modules.api.certificatService;
import ma.TeethCare.repository.api.CertificatRepository;
import java.util.List;
import java.util.Optional;

/**
 * @author CHOUKHAIRI Noureddine
 * @date 2025-12-14
 */

public class certificatServiceImpl implements certificatService {

    private final CertificatRepository repository;

    public certificatServiceImpl(CertificatRepository repository) {
        this.repository = repository;
    }

    @Override
    public certificat create(certificat entity) throws Exception {
        repository.create(entity);
        return entity;
    }

    @Override
    public Optional<certificat> findById(Long id) throws Exception {
        return Optional.ofNullable(repository.findById(id));
    }

    @Override
    public List<certificat> findAll() throws Exception {
        return repository.findAll();
    }

    @Override
    public certificat update(certificat entity) throws Exception {
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
