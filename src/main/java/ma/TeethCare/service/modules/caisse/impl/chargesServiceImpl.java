package ma.TeethCare.service.modules.caisse.impl;

import ma.TeethCare.entities.charges.charges;
import ma.TeethCare.repository.api.ChargesRepository;
import ma.TeethCare.service.modules.caisse.api.chargesService;
import ma.TeethCare.service.modules.caisse.dto.ChargesDto;
import ma.TeethCare.service.modules.caisse.mapper.ChargesMapper;

import java.util.List;
import java.util.stream.Collectors;

public class chargesServiceImpl implements chargesService {

    private final ChargesRepository repository;

    public chargesServiceImpl(ChargesRepository repository) {
        this.repository = repository;
    }

    @Override
    public ChargesDto create(ChargesDto dto) {
        try {
            charges entity = ChargesMapper.toEntity(dto);
            repository.create(entity);
            return ChargesMapper.toDto(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error creating charges", e);
        }
    }

    @Override
    public ChargesDto update(Long id, ChargesDto dto) {
        try {
            charges entity = ChargesMapper.toEntity(dto);
            entity.setId(id);
            repository.update(entity);
            return ChargesMapper.toDto(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error updating charges", e);
        }
    }

    @Override
    public ChargesDto findById(Long id) {
        try {
            charges entity = repository.findById(id);
            return ChargesMapper.toDto(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error finding charges", e);
        }
    }

    @Override
    public List<ChargesDto> findAll() {
        try {
            return repository.findAll().stream()
                    .map(ChargesMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error finding all charges", e);
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
