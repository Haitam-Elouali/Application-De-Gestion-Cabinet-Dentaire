package ma.TeethCare.service.modules.impl;

import ma.TeethCare.entities.secretaire.secretaire;
import ma.TeethCare.service.modules.api.secretaireService;
import java.util.List;
import java.util.Optional;

import ma.TeethCare.repository.api.SecretaireRepository;

public class secretaireServiceImpl implements secretaireService {

    private SecretaireRepository secretaireRepository;

    public secretaireServiceImpl(SecretaireRepository secretaireRepository) {
        this.secretaireRepository = secretaireRepository;
    }

    @Override
    public secretaire create(secretaire entity) throws Exception {
        // TODO: Implement method
        return null;
    }

    @Override
    public Optional<secretaire> findById(Long id) throws Exception {
        // TODO: Implement method
        return Optional.empty();
    }

    @Override
    public List<secretaire> findAll() throws Exception {
        // TODO: Implement method
        return null;
    }

    @Override
    public secretaire update(secretaire entity) throws Exception {
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
