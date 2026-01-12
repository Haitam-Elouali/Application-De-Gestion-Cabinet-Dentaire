package ma.TeethCare.service.modules.cabinet.impl;

import ma.TeethCare.entities.cabinetMedicale.cabinetMedicale;
import ma.TeethCare.mvc.dto.cabinetMedicale.CabinetMedicaleDTO;
import ma.TeethCare.service.modules.cabinet.api.cabinetMedicaleService;
import ma.TeethCare.service.modules.cabinet.mapper.CabinetMedicaleMapper;
import ma.TeethCare.repository.api.CabinetMedicaleRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Haitam ELOUALI
 * @date 2025-12-14
 */

public class cabinetMedicaleServiceImpl implements cabinetMedicaleService {

    private final CabinetMedicaleRepository repository;

    public cabinetMedicaleServiceImpl(CabinetMedicaleRepository repository) {
        this.repository = repository;
    }

    @Override
    public CabinetMedicaleDTO create(CabinetMedicaleDTO dto) throws Exception {
        cabinetMedicale entity = CabinetMedicaleMapper.toEntity(dto);
        repository.create(entity);
        if(entity.getId() != null) {
            return CabinetMedicaleMapper.toDTO(repository.findById(entity.getId()));
        }
        return CabinetMedicaleMapper.toDTO(entity);
    }

    @Override
    public Optional<CabinetMedicaleDTO> findById(Long id) throws Exception {
        return Optional.ofNullable(CabinetMedicaleMapper.toDTO(repository.findById(id)));
    }

    @Override
    public List<CabinetMedicaleDTO> findAll() throws Exception {
        return repository.findAll().stream()
                .map(CabinetMedicaleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CabinetMedicaleDTO update(CabinetMedicaleDTO dto) throws Exception {
        cabinetMedicale entity = CabinetMedicaleMapper.toEntity(dto);
        repository.update(entity);
        // Assuming update relies on ID being set
        return CabinetMedicaleMapper.toDTO(repository.findById(entity.getId()));
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
    public Optional<CabinetMedicaleDTO> findByEmail(String email) throws Exception {
        return repository.findByEmail(email).map(CabinetMedicaleMapper::toDTO);
    }
}
