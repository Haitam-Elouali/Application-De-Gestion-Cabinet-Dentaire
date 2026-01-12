package ma.TeethCare.service.modules.caisse.impl;

import ma.TeethCare.entities.charges.charges;
import ma.TeethCare.mvc.dto.charges.ChargesDTO;
import ma.TeethCare.repository.api.ChargesRepository;
import ma.TeethCare.service.modules.caisse.api.chargesService;
import ma.TeethCare.service.modules.caisse.mapper.ChargesMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class chargesServiceImpl implements chargesService {

    private final ChargesRepository repository;

    public chargesServiceImpl(ChargesRepository repository) {
        this.repository = repository;
    }

    @Override
    public ChargesDTO create(ChargesDTO dto) throws Exception {
        try {
            charges entity = ChargesMapper.toEntity(dto);
            repository.create(entity);
            if(entity.getId() != null) {
                return ChargesMapper.toDTO(repository.findById(entity.getId()));
            }
            return ChargesMapper.toDTO(entity);
        } catch (Exception e) {
            throw new Exception("Error creating charges", e);
        }
    }

    @Override
    public ChargesDTO update(ChargesDTO dto) throws Exception {
        try {
            charges entity = ChargesMapper.toEntity(dto);
            repository.update(entity);
            return ChargesMapper.toDTO(repository.findById(entity.getId()));
        } catch (Exception e) {
            throw new Exception("Error updating charges", e);
        }
    }

    @Override
    public Optional<ChargesDTO> findById(Long id) throws Exception {
        try {
            return Optional.ofNullable(ChargesMapper.toDTO(repository.findById(id)));
        } catch (Exception e) {
            throw new Exception("Error finding charges", e);
        }
    }

    @Override
    public List<ChargesDTO> findAll() throws Exception {
        try {
            return repository.findAll().stream()
                    .map(ChargesMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception("Error finding all charges", e);
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
