package ma.TeethCare.service.modules.caisse.impl;

import ma.TeethCare.entities.revenues.revenues;
import ma.TeethCare.mvc.dto.revenues.RevenuesDTO;
import ma.TeethCare.repository.api.RevenuesRepository;
import ma.TeethCare.service.modules.caisse.api.revenuesService;
import ma.TeethCare.service.modules.caisse.mapper.RevenuesMapper;
import ma.TeethCare.common.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class revenuesServiceImpl implements revenuesService {

    private final RevenuesRepository repository;

    public revenuesServiceImpl(RevenuesRepository repository) {
        this.repository = repository;
    }

    @Override
    public RevenuesDTO create(RevenuesDTO dto) throws Exception {
        try {
            revenues entity = RevenuesMapper.toEntity(dto);
            repository.create(entity);
            if(entity.getId() != null) {
                return RevenuesMapper.toDto(repository.findById(entity.getId()));
            }
            return RevenuesMapper.toDto(entity);
        } catch (Exception e) {
            throw new ServiceException("Error creating revenues", e);
        }
    }

    @Override
    public RevenuesDTO update(RevenuesDTO dto) throws Exception {
        try {
            revenues entity = RevenuesMapper.toEntity(dto);
            repository.update(entity);
            return RevenuesMapper.toDto(repository.findById(entity.getId()));
        } catch (Exception e) {
            throw new ServiceException("Error updating revenues", e);
        }
    }

    @Override
    public Optional<RevenuesDTO> findById(Long id) throws Exception {
        try {
            return Optional.ofNullable(RevenuesMapper.toDto(repository.findById(id)));
        } catch (Exception e) {
            throw new ServiceException("Error finding revenues", e);
        }
    }

    @Override
    public List<RevenuesDTO> findAll() throws Exception {
        try {
            return repository.findAll().stream()
                    .map(RevenuesMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Error finding all revenues", e);
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
