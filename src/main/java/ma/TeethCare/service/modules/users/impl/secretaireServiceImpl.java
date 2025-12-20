package ma.TeethCare.service.modules.users.impl;

import ma.TeethCare.entities.secretaire.secretaire;
import ma.TeethCare.service.modules.users.api.secretaireService;
import java.util.List;
import java.util.Optional;

import ma.TeethCare.repository.api.SecretaireRepository;
import ma.TeethCare.repository.mySQLImpl.SecretaireRepositoryImpl;

public class secretaireServiceImpl implements secretaireService {

    private final SecretaireRepository secretaireRepository;

    public secretaireServiceImpl() {
        this.secretaireRepository = new SecretaireRepositoryImpl();
    }

    public secretaireServiceImpl(SecretaireRepository secretaireRepository) {
        this.secretaireRepository = secretaireRepository;
    }

    @Override
    public secretaire create(secretaire entity) throws Exception {
        secretaireRepository.create(entity);
        return entity;
    }

    @Override
    public Optional<secretaire> findById(Long id) throws Exception {
        return Optional.ofNullable(secretaireRepository.findById(id));
    }

    @Override
    public List<secretaire> findAll() throws Exception {
        return secretaireRepository.findAll();
    }

    @Override
    public secretaire update(secretaire entity) throws Exception {
        secretaireRepository.update(entity);
        return entity;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        secretaireRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean exists(Long id) throws Exception {
        return secretaireRepository.findById(id) != null;
    }

    @Override
    public long count() throws Exception {
        return secretaireRepository.findAll().size();
    }

    @Override
    public Optional<secretaire> findByNumCNSS(String numCNSS) throws Exception {
        return secretaireRepository.findByNumCNSS(numCNSS);
    }

    @Override
    public Optional<secretaire> findByCin(String cin) throws Exception {
        return secretaireRepository.findByCin(cin);
    }
}
