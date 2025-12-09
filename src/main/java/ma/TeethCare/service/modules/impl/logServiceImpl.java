package ma.TeethCare.service.modules.impl;
import ma.TeethCare.entities.log.log;
import ma.TeethCare.service.modules.api.logService;
import java.util.List;
import java.util.Optional;

public class logServiceImpl implements logService {

    @Override
    public log create(log entity) throws Exception {
        // TODO: Implement method
        return null;
    }

    @Override
    public Optional<log> findById(Long id) throws Exception {
        // TODO: Implement method
        return Optional.empty();
    }

    @Override
    public List<log> findAll() throws Exception {
        // TODO: Implement method
        return null;
    }

    @Override
    public log update(log entity) throws Exception {
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
