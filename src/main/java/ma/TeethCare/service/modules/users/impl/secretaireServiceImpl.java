package ma.TeethCare.service.modules.users.impl;

import ma.TeethCare.entities.secretaire.secretaire;
import ma.TeethCare.mvc.dto.secretaire.SecretaireDTO;
import ma.TeethCare.repository.api.SecretaireRepository;
import ma.TeethCare.repository.mySQLImpl.SecretaireRepositoryImpl;
import ma.TeethCare.service.modules.users.api.secretaireService;
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
    public SecretaireDTO create(SecretaireDTO dto) throws Exception {
        if (dto == null) throw new IllegalArgumentException("SecretaireDTO is null");
        try {
            secretaire entity = SecretaireMapper.toEntity(dto);
            secretaireRepository.create(entity);
            if(entity.getId() != null) {
                return SecretaireMapper.toDTO(secretaireRepository.findById(entity.getId()));
            }
            return SecretaireMapper.toDTO(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error creating secretaire", e);
        }
    }

    @Override
    public Optional<SecretaireDTO> findById(Long id) throws Exception {
        try {
            secretaire entity = secretaireRepository.findById(id);
            return Optional.ofNullable(SecretaireMapper.toDTO(entity));
        } catch (Exception e) {
            throw new RuntimeException("Error finding secretaire", e);
        }
    }

    @Override
    public List<SecretaireDTO> findAll() throws Exception {
        try {
            return secretaireRepository.findAll().stream()
                    .map(SecretaireMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error finding all secretaires", e);
        }
    }

    @Override
    public SecretaireDTO update(SecretaireDTO dto) throws Exception {
        if (dto == null) throw new IllegalArgumentException("SecretaireDTO is null");
        if (dto.getId() == null) throw new IllegalArgumentException("Id required for update");
        try {
            secretaire entity = SecretaireMapper.toEntity(dto);
            // Ensure ID is set on entity if mapper didn't handle it fully or if it relies on dto.id
            // (Mapper usually sets idEntite/id)
            
            secretaireRepository.update(entity);
            return SecretaireMapper.toDTO(secretaireRepository.findById(entity.getId()));
        } catch (Exception e) {
            throw new RuntimeException("Error updating secretaire", e);
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
    public Optional<SecretaireDTO> findByNumCNSS(String numCNSS) throws Exception {
        Optional<secretaire> entity = secretaireRepository.findByNumCNSS(numCNSS);
        return entity.map(SecretaireMapper::toDTO);
    }

    @Override
    public Optional<SecretaireDTO> findByCin(String cin) throws Exception {
        Optional<secretaire> entity = secretaireRepository.findByCin(cin);
        return entity.map(SecretaireMapper::toDTO);
    }
}
