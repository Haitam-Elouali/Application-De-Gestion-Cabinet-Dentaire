package ma.TeethCare.service.modules.caisse.impl;

import ma.TeethCare.entities.caisse.caisse;
import ma.TeethCare.repository.api.CaisseRepository;
import ma.TeethCare.service.modules.caisse.api.caisseService;
import ma.TeethCare.service.modules.caisse.dto.CaisseDto;
import ma.TeethCare.service.modules.caisse.mapper.CaisseMapper;

import java.util.List;
import java.util.stream.Collectors;

public class caisseServiceImpl implements caisseService {

    private final CaisseRepository repository;

    public caisseServiceImpl(CaisseRepository repository) {
        this.repository = repository;
    }

    @Override
    public CaisseDto create(CaisseDto dto) {
        try {
            caisse entity = CaisseMapper.toEntity(dto);
            repository.create(entity);
            return CaisseMapper.toDto(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error creating caisse", e);
        }
    }

    @Override
    public CaisseDto update(Long id, CaisseDto dto) {
        try {
            caisse entity = CaisseMapper.toEntity(dto);
            entity.setIdCaisse(id);
            repository.update(entity);
            return CaisseMapper.toDto(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error updating caisse", e);
        }
    }

    @Override
    public CaisseDto findById(Long id) {
        try {
            caisse entity = repository.findById(id);
            return CaisseMapper.toDto(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error finding caisse", e);
        }
    }

    @Override
    public List<CaisseDto> findAll() {
        try {
            return repository.findAll().stream()
                    .map(CaisseMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error finding all caisses", e);
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
