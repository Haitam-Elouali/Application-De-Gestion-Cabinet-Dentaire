package ma.TeethCare.service.modules.impl;

import ma.TeethCare.entities.medecin.medecin;
import ma.TeethCare.service.modules.api.medecinService;
import java.util.List;
import java.util.Optional;

import ma.TeethCare.repository.api.MedecinRepository;

public class medecinServiceImpl implements medecinService {

    private MedecinRepository medecinRepository;

    public medecinServiceImpl(MedecinRepository medecinRepository) {
        this.medecinRepository = medecinRepository;
    }

    @Override
    public medecin create(medecin entity) throws Exception {
        // TODO: Implement method
        return null;
    }

    @Override
    public Optional<medecin> findById(Long id) throws Exception {
        // TODO: Implement method
        return Optional.empty();
    }

    @Override
    public List<medecin> findAll() throws Exception {
        // TODO: Implement method
        return null;
    }

    @Override
    public medecin update(medecin entity) throws Exception {
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
