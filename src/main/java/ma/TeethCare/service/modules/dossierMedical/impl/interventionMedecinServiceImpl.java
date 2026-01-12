package ma.TeethCare.service.modules.dossierMedical.impl;

import ma.TeethCare.entities.interventionMedecin.interventionMedecin;
import ma.TeethCare.mvc.dto.interventionMedecin.InterventionMedecinDTO;
import ma.TeethCare.repository.api.InterventionMedecinRepository;
import ma.TeethCare.service.modules.dossierMedical.api.interventionMedecinService;
import ma.TeethCare.service.modules.dossierMedical.mapper.InterventionMedecinMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class interventionMedecinServiceImpl implements interventionMedecinService {

    private final InterventionMedecinRepository repository;

    public interventionMedecinServiceImpl(InterventionMedecinRepository repository) {
        this.repository = repository;
    }

    @Override
    public InterventionMedecinDTO create(InterventionMedecinDTO dto) throws Exception {
        try {
            interventionMedecin entity = InterventionMedecinMapper.toEntity(dto);
            repository.create(entity);
            if(entity.getId() != null) {
                return InterventionMedecinMapper.toDTO(repository.findById(entity.getId()));
            }
            return InterventionMedecinMapper.toDTO(entity);
        } catch (Exception e) {
            throw new Exception("Error creating interventionMedecin", e);
        }
    }

    @Override
    public InterventionMedecinDTO update(InterventionMedecinDTO dto) throws Exception {
        try {
            interventionMedecin entity = InterventionMedecinMapper.toEntity(dto);
            repository.update(entity);
            return InterventionMedecinMapper.toDTO(repository.findById(entity.getId()));
        } catch (Exception e) {
            throw new Exception("Error updating interventionMedecin", e);
        }
    }

    @Override
    public Optional<InterventionMedecinDTO> findById(Long id) throws Exception {
        try {
            return Optional.ofNullable(InterventionMedecinMapper.toDTO(repository.findById(id)));
        } catch (Exception e) {
            throw new Exception("Error finding interventionMedecin", e);
        }
    }

    @Override
    public List<InterventionMedecinDTO> findAll() throws Exception {
        try {
            return repository.findAll().stream()
                    .map(InterventionMedecinMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception("Error finding all interventionMedecin", e);
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
}
