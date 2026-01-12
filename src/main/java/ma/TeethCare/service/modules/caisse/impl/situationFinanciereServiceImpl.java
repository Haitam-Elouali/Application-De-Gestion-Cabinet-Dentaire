package ma.TeethCare.service.modules.caisse.impl;

import ma.TeethCare.entities.situationFinanciere.situationFinanciere;
import ma.TeethCare.mvc.dto.situationFinanciere.SituationFinanciereDTO;
import ma.TeethCare.repository.api.SituationFinanciereRepository;
import ma.TeethCare.service.modules.caisse.api.situationFinanciereService;
import ma.TeethCare.service.modules.caisse.mapper.SituationFinanciereMapper;
import ma.TeethCare.common.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class situationFinanciereServiceImpl implements situationFinanciereService {

    private final SituationFinanciereRepository repository;

    public situationFinanciereServiceImpl(SituationFinanciereRepository repository) {
        this.repository = repository;
    }

    @Override
    public SituationFinanciereDTO create(SituationFinanciereDTO dto) throws Exception {
        try {
            situationFinanciere entity = SituationFinanciereMapper.toEntity(dto);
            repository.create(entity);
            if(entity.getId() != null) {
                return SituationFinanciereMapper.toDto(repository.findById(entity.getId()));
            }
            return SituationFinanciereMapper.toDto(entity);
        } catch (Exception e) {
            throw new ServiceException("Error creating situationFinanciere", e);
        }
    }

    @Override
    public SituationFinanciereDTO update(SituationFinanciereDTO dto) throws Exception {
        try {
            situationFinanciere entity = SituationFinanciereMapper.toEntity(dto);
            repository.update(entity);
            return SituationFinanciereMapper.toDto(repository.findById(entity.getId()));
        } catch (Exception e) {
            throw new ServiceException("Error updating situationFinanciere", e);
        }
    }

    @Override
    public Optional<SituationFinanciereDTO> findById(Long id) throws Exception {
        try {
            return Optional.ofNullable(SituationFinanciereMapper.toDto(repository.findById(id)));
        } catch (Exception e) {
            throw new ServiceException("Error finding situationFinanciere", e);
        }
    }

    @Override
    public List<SituationFinanciereDTO> findAll() throws Exception {
        try {
            return repository.findAll().stream()
                    .map(SituationFinanciereMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Error finding all situationFinancieres", e);
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
