package ma.TeethCare.service.modules.impl;

import ma.TeethCare.entities.actes.actes;
import ma.TeethCare.service.modules.api.actesService;
import ma.TeethCare.repository.api.ActesRepository;
import java.util.List;
import java.util.Optional;

public class actesServiceImpl implements actesService {

    private ActesRepository actesRepository;

    public actesServiceImpl(ActesRepository actesRepository) {
        this.actesRepository = actesRepository;
    }

    @Override
    public actes create(actes entity) throws Exception {
        // TODO: Implement method
        return null;
    }

    @Override
    public Optional<actes> findById(Long id) throws Exception {
        // TODO: Implement method
        return Optional.empty();
    }

    @Override
    public List<actes> findAll() throws Exception {
        // TODO: Implement method
        return null;
    }

    @Override
    public actes update(actes entity) throws Exception {
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
