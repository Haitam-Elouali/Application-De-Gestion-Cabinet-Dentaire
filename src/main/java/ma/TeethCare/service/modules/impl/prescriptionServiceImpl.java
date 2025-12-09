package ma.TeethCare.service.modules.impl;
import ma.TeethCare.entities.prescription.prescription;
import ma.TeethCare.service.api.prescriptionService;
import java.util.List;
import java.util.Optional;

public class prescriptionServiceImpl implements prescriptionService {

    @Override
    public prescription create(prescription entity) throws Exception {
        // TODO: Implement method
        return null;
    }

    @Override
    public Optional<prescription> findById(Long id) throws Exception {
        // TODO: Implement method
        return Optional.empty();
    }

    @Override
    public List<prescription> findAll() throws Exception {
        // TODO: Implement method
        return null;
    }

    @Override
    public prescription update(prescription entity) throws Exception {
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
