package ma.TeethCare.service.modules.impl;
import ma.TeethCare.entities.certificat.certificat;
import ma.TeethCare.service.api.certificatService;
import java.util.List;
import java.util.Optional;

public class certificatServiceImpl implements certificatService {

    @Override
    public certificat create(certificat entity) throws Exception {
        // TODO: Implement method
        return null;
    }

    @Override
    public Optional<certificat> findById(Long id) throws Exception {
        // TODO: Implement method
        return Optional.empty();
    }

    @Override
    public List<certificat> findAll() throws Exception {
        // TODO: Implement method
        return null;
    }

    @Override
    public certificat update(certificat entity) throws Exception {
        // TODO: Implement method
        return null;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        // TODO: Implement method
        return false;
    }

    @Override
    public boolean exists(Long id) throws Exception {
        // TODO: Implement method
        return false;
    }

    @Override
    public long count() throws Exception {
        // TODO: Implement method
        return 0;
    }
}
