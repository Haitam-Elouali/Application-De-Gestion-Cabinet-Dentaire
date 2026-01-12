package ma.TeethCare.service.modules.dossierMedical.impl;

import ma.TeethCare.entities.actes.actes;
import ma.TeethCare.mvc.dto.actes.ActesDTO;
import ma.TeethCare.repository.api.ActesRepository;
import ma.TeethCare.service.modules.dossierMedical.api.actesService;
import ma.TeethCare.service.modules.dossierMedical.mapper.ActesMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class actesServiceImpl implements actesService {

    private final ActesRepository repository;

    public actesServiceImpl(ActesRepository repository) {
        this.repository = repository;
    }

    @Override
    public ActesDTO create(ActesDTO dto) throws Exception {
        try {
            actes entity = ActesMapper.toEntity(dto);
            repository.create(entity);
            if(entity.getId() != null) {
                return ActesMapper.toDTO(repository.findById(entity.getId()));
            }
            return ActesMapper.toDTO(entity);
        } catch (Exception e) {
            throw new Exception("Error creating actes", e);
        }
    }

    @Override
    public ActesDTO update(ActesDTO dto) throws Exception {
        try {
            actes entity = ActesMapper.toEntity(dto);
            repository.update(entity);
            return ActesMapper.toDTO(repository.findById(entity.getId()));
        } catch (Exception e) {
            throw new Exception("Error updating actes", e);
        }
    }

    @Override
    public Optional<ActesDTO> findById(Long id) throws Exception {
        try {
            return Optional.ofNullable(ActesMapper.toDTO(repository.findById(id)));
        } catch (Exception e) {
            throw new Exception("Error finding actes", e);
        }
    }

    @Override
    public List<ActesDTO> findAll() throws Exception {
        try {
            return repository.findAll().stream()
                    .map(ActesMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception("Error finding all actes", e);
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        repository.deleteById(id);
        return true;
    }

    @Override
    public boolean exists(Long id) throws Exception {
        return repository.findById(id) != null;
    }

    @Override
    public long count() throws Exception {
        return repository.findAll().size();
    }

    @Override
    public List<ActesDTO> findByCategorie(String categorie) throws Exception {
        return repository.findByCategorie(categorie).stream()
                .map(ActesMapper::toDTO)
                .collect(Collectors.toList());
    }
}
