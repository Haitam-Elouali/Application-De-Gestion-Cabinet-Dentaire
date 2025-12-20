package ma.TeethCare.service.modules.users.impl;

import ma.TeethCare.entities.secretaire.secretaire;
import ma.TeethCare.repository.api.SecretaireRepository;
import ma.TeethCare.repository.mySQLImpl.SecretaireRepositoryImpl;
import ma.TeethCare.service.modules.users.api.secretaireService;
import ma.TeethCare.service.modules.users.dto.CreateSecretaireRequest;
import ma.TeethCare.service.modules.users.dto.UserAccountDto;
import ma.TeethCare.service.modules.users.mapper.SecretaireMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class secretaireServiceImpl implements secretaireService {

    private final SecretaireRepository secretaireRepository;

    public secretaireServiceImpl() {
        this.secretaireRepository = new SecretaireRepositoryImpl();
    }

    public secretaireServiceImpl(SecretaireRepository secretaireRepository) {
        this.secretaireRepository = secretaireRepository;
    }

    @Override
    public UserAccountDto create(CreateSecretaireRequest request) {
        try {
            secretaire entity = SecretaireMapper.toEntity(request);
            secretaireRepository.create(entity);
            return SecretaireMapper.toUserAccountDto(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error creating secretaire", e);
        }
    }

    @Override
    public UserAccountDto findById(Long id) {
        try {
            secretaire entity = secretaireRepository.findById(id);
            return SecretaireMapper.toUserAccountDto(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error finding secretaire", e);
        }
    }

    @Override
    public List<UserAccountDto> findAll() {
        try {
            return secretaireRepository.findAll().stream()
                    .map(SecretaireMapper::toUserAccountDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error finding all secretaires", e);
        }
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
    public Optional<UserAccountDto> findByNumCNSS(String numCNSS) throws Exception {
        Optional<secretaire> entity = secretaireRepository.findByNumCNSS(numCNSS);
        return entity.map(SecretaireMapper::toUserAccountDto);
    }

    @Override
    public Optional<UserAccountDto> findByCin(String cin) throws Exception {
        Optional<secretaire> entity = secretaireRepository.findByCin(cin);
        return entity.map(SecretaireMapper::toUserAccountDto);
    }
}
