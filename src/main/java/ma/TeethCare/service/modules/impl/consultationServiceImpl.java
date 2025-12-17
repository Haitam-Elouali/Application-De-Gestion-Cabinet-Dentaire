package ma.TeethCare.service.modules.impl;
import ma.TeethCare.entities.consultation.consultation;
import ma.TeethCare.service.modules.api.consultationService;
import java.util.List;
import java.util.Optional;

import ma.TeethCare.repository.api.ConsultationRepository;

public class consultationServiceImpl implements consultationService {

    private ConsultationRepository consultationRepository;

    public consultationServiceImpl(ConsultationRepository consultationRepository) {
        this.consultationRepository = consultationRepository;
    }

    @Override
    public consultation create(consultation entity) throws Exception {
        // TODO: Implement method
        return null;
    }

    @Override
    public Optional<consultation> findById(Long id) throws Exception {
        // TODO: Implement method
        return Optional.empty();
    }

    @Override
    public List<consultation> findAll() throws Exception {
        // TODO: Implement method
        return null;
    }

    @Override
    public consultation update(consultation entity) throws Exception {
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
