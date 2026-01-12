package ma.TeethCare.service.modules.dossierMedical.impl;

import ma.TeethCare.entities.medicaments.medicaments;
import ma.TeethCare.mvc.dto.medicament.MedicamentDTO;
import ma.TeethCare.repository.api.MedicamentRepository;
import ma.TeethCare.service.modules.dossierMedical.api.medicamentsService;
import ma.TeethCare.service.modules.dossierMedical.mapper.MedicamentMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class medicamentsServiceImpl implements medicamentsService {

    private final MedicamentRepository repository;

    public medicamentsServiceImpl(MedicamentRepository repository) {
        this.repository = repository;
    }

    @Override
    public MedicamentDTO create(MedicamentDTO dto) throws Exception {
        try {
            medicaments entity = MedicamentMapper.toEntity(dto);
            repository.create(entity);
            if(entity.getId() != null) {
                return MedicamentMapper.toDTO(repository.findById(entity.getId()));
            }
            return MedicamentMapper.toDTO(entity);
        } catch (Exception e) {
            throw new Exception("Error creating medicament", e);
        }
    }

    @Override
    public MedicamentDTO update(MedicamentDTO dto) throws Exception {
        try {
            medicaments entity = MedicamentMapper.toEntity(dto);
            repository.update(entity);
            return MedicamentMapper.toDTO(repository.findById(entity.getId()));
        } catch (Exception e) {
            throw new Exception("Error updating medicament", e);
        }
    }

    @Override
    public Optional<MedicamentDTO> findById(Long id) throws Exception {
        try {
            return Optional.ofNullable(MedicamentMapper.toDTO(repository.findById(id)));
        } catch (Exception e) {
            throw new Exception("Error finding medicament", e);
        }
    }

    @Override
    public List<MedicamentDTO> findAll() throws Exception {
        try {
            return repository.findAll().stream()
                    .map(MedicamentMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception("Error finding all medicaments", e);
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
    public Optional<MedicamentDTO> findByNomCommercial(String nomCommercial) throws Exception {
        return repository.findByNom(nomCommercial).map(MedicamentMapper::toDTO);
    }
}
