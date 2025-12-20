package ma.TeethCare.service.modules.caisse.impl;

import ma.TeethCare.entities.situationFinanciere.situationFinanciere;
import ma.TeethCare.repository.api.SituationFinanciereRepository;
import ma.TeethCare.service.modules.caisse.api.situationFinanciereService;
import ma.TeethCare.service.modules.caisse.dto.SituationFinanciereDto;
import ma.TeethCare.service.modules.caisse.mapper.SituationFinanciereMapper;

import java.util.List;
import java.util.stream.Collectors;

public class situationFinanciereServiceImpl implements situationFinanciereService {

    private final SituationFinanciereRepository repository;

    public situationFinanciereServiceImpl(SituationFinanciereRepository repository) {
        this.repository = repository;
    }

    @Override
    public SituationFinanciereDto create(SituationFinanciereDto dto) {
        try {
            situationFinanciere entity = SituationFinanciereMapper.toEntity(dto);
            repository.create(entity);
            return SituationFinanciereMapper.toDto(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error creating situationFinanciere", e);
        }
    }

    @Override
    public SituationFinanciereDto update(Long id, SituationFinanciereDto dto) {
        try {
            situationFinanciere entity = SituationFinanciereMapper.toEntity(dto);
            entity.setId(id);
            repository.update(entity);
            return SituationFinanciereMapper.toDto(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error updating situationFinanciere", e);
        }
    }

    @Override
    public SituationFinanciereDto findById(Long id) {
        try {
            situationFinanciere entity = repository.findById(id);
            return SituationFinanciereMapper.toDto(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error finding situationFinanciere", e);
        }
    }

    @Override
    public List<SituationFinanciereDto> findAll() {
        try {
            return repository.findAll().stream()
                    .map(SituationFinanciereMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error finding all situationFinancieres", e);
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
