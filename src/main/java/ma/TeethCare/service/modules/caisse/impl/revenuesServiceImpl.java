package ma.TeethCare.service.modules.caisse.impl;

import ma.TeethCare.entities.revenues.revenues;
import ma.TeethCare.repository.api.RevenuesRepository;
import ma.TeethCare.service.modules.caisse.api.revenuesService;
import ma.TeethCare.service.modules.caisse.dto.RevenuesDto;
import ma.TeethCare.service.modules.caisse.mapper.RevenuesMapper;

import java.util.List;
import java.util.stream.Collectors;

public class revenuesServiceImpl implements revenuesService {

    private final RevenuesRepository repository;

    public revenuesServiceImpl(RevenuesRepository repository) {
        this.repository = repository;
    }

    @Override
    public RevenuesDto create(RevenuesDto dto) {
        try {
            revenues entity = RevenuesMapper.toEntity(dto);
            repository.create(entity);
            return RevenuesMapper.toDto(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error creating revenues", e);
        }
    }

    @Override
    public RevenuesDto update(Long id, RevenuesDto dto) {
        try {
            revenues entity = RevenuesMapper.toEntity(dto);
            entity.setId(id);
            repository.update(entity);
            return RevenuesMapper.toDto(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error updating revenues", e);
        }
    }

    @Override
    public RevenuesDto findById(Long id) {
        try {
            revenues entity = repository.findById(id);
            return RevenuesMapper.toDto(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error finding revenues", e);
        }
    }

    @Override
    public List<RevenuesDto> findAll() {
        try {
            return repository.findAll().stream()
                    .map(RevenuesMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error finding all revenues", e);
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
