package ma.TeethCare.service.modules.caisse.impl;

import ma.TeethCare.entities.facture.facture;
import ma.TeethCare.repository.api.FactureRepository;
import ma.TeethCare.service.modules.caisse.api.factureService;
import ma.TeethCare.service.modules.caisse.dto.FactureDto;
import ma.TeethCare.service.modules.caisse.mapper.FactureMapper;

import java.util.List;
import java.util.stream.Collectors;

public class factureServiceImpl implements factureService {

    private final FactureRepository repository;

    public factureServiceImpl(FactureRepository repository) {
        this.repository = repository;
    }

    @Override
    public FactureDto create(FactureDto dto) {
        try {
            facture entity = FactureMapper.toEntity(dto);
            repository.create(entity);
            return FactureMapper.toDto(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error creating facture", e);
        }
    }

    @Override
    public FactureDto update(Long id, FactureDto dto) {
        try {
            facture entity = FactureMapper.toEntity(dto);
            entity.setId(id);
            repository.update(entity);
            return FactureMapper.toDto(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error updating facture", e);
        }
    }

    @Override
    public FactureDto findById(Long id) {
        try {
            facture entity = repository.findById(id);
            return FactureMapper.toDto(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error finding facture", e);
        }
    }

    @Override
    public List<FactureDto> findAll() {
        try {
            return repository.findAll().stream()
                    .map(FactureMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error finding all factures", e);
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
