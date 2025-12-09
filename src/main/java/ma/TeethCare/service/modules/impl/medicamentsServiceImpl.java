package ma.TeethCare.service.modules.impl;
import ma.TeethCare.entities.medicaments.medicaments;
import ma.TeethCare.service.modules.api.medicamentsService;
import java.util.List;
import java.util.Optional;

public class medicamentsServiceImpl implements medicamentsService {

    @Override
    public medicaments create(medicaments entity) throws Exception {
        // TODO: Implement method
        return null;
    }

    @Override
    public Optional<medicaments> findById(Long id) throws Exception {
        // TODO: Implement method
        return Optional.empty();
    }

    @Override
    public List<medicaments> findAll() throws Exception {
        // TODO: Implement method
        return null;
    }

    @Override
    public medicaments update(medicaments entity) throws Exception {
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
