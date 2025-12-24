package ma.TeethCare.service.modules.dossierMedical.impl;

import ma.TeethCare.entities.dossierMedicale.dossierMedicale;
import ma.TeethCare.repository.api.DossierMedicaleRepository;
import ma.TeethCare.repository.mySQLImpl.DossierMedicaleRepositoryImpl;
import ma.TeethCare.service.modules.dossierMedical.api.dossierMedicaleService;
import ma.TeethCare.service.modules.dossierMedical.dto.DossierMedicalDto;
import ma.TeethCare.service.modules.dossierMedical.mapper.DossierMedicalMapper;

import java.util.List;
import java.util.stream.Collectors;

public class dossierMedicaleServiceImpl implements dossierMedicaleService {

    private final DossierMedicaleRepository dossierMedicaleRepository;

    public dossierMedicaleServiceImpl() {
        this.dossierMedicaleRepository = new DossierMedicaleRepositoryImpl();
    }

    public dossierMedicaleServiceImpl(DossierMedicaleRepository dossierMedicaleRepository) {
        this.dossierMedicaleRepository = dossierMedicaleRepository;
    }

    @Override
    public DossierMedicalDto create(DossierMedicalDto dto) {
        try {
            dossierMedicale entity = DossierMedicalMapper.toEntity(dto);
            dossierMedicaleRepository.create(entity);
            return DossierMedicalMapper.toDto(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error creating dossier medical", e);
        }
    }

    @Override
    public DossierMedicalDto update(Long id, DossierMedicalDto dto) {
        try {
            dossierMedicale entity = DossierMedicalMapper.toEntity(dto);
            entity.setId(id); // Ensure ID is set for update
            dossierMedicaleRepository.update(entity);
            return DossierMedicalMapper.toDto(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error updating dossier medical", e);
        }
    }

    @Override
    public DossierMedicalDto findById(Long id) {
        try {
            dossierMedicale entity = dossierMedicaleRepository.findById(id);
            return DossierMedicalMapper.toDto(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error finding dossier medical by id", e);
        }
    }

    @Override
    public List<DossierMedicalDto> findAll() {
        try {
            return dossierMedicaleRepository.findAll().stream()
                    .map(DossierMedicalMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error finding all dossier medicals", e);
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        if (id == null) {
            return false;
        }
        dossierMedicaleRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean exists(Long id) throws Exception {
        if (id == null) {
            return false;
        }
        return dossierMedicaleRepository.findById(id) != null;
    }

    @Override
    public long count() throws Exception {
        return dossierMedicaleRepository.findAll().size();
    }
}
