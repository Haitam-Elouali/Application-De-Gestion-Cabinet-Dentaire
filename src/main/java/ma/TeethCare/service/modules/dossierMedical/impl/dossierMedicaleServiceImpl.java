package ma.TeethCare.service.modules.dossierMedical.impl;

import ma.TeethCare.entities.dossierMedicale.dossierMedicale;
import ma.TeethCare.entities.patient.Patient;
import ma.TeethCare.mvc.dto.dossierMedicale.DossierMedicaleDTO;
import ma.TeethCare.repository.api.DossierMedicaleRepository;
import ma.TeethCare.repository.api.PatientRepository;
import ma.TeethCare.repository.mySQLImpl.PatientRepositoryImpl;
import ma.TeethCare.service.modules.dossierMedical.api.dossierMedicaleService;
import ma.TeethCare.service.modules.dossierMedical.mapper.DossierMedicalMapper;
import ma.TeethCare.common.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class dossierMedicaleServiceImpl implements dossierMedicaleService {

    private final DossierMedicaleRepository dmRepository;
    private final PatientRepository patientRepository;

    public dossierMedicaleServiceImpl(DossierMedicaleRepository dmRepository) {
        this.dmRepository = dmRepository;
        this.patientRepository = new PatientRepositoryImpl(); // Direct instantiation for now
    }

    @Override
    public DossierMedicaleDTO create(DossierMedicaleDTO dto) throws Exception {
        try {
            if (dto == null) {
                throw new ServiceException("DTO ne peut pas être null");
            }
            dossierMedicale entity = DossierMedicalMapper.toEntity(dto);
            dmRepository.create(entity);
            return DossierMedicalMapper.toDTO(entity);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création du dossier médical", e);
        }
    }

    @Override
    public Optional<DossierMedicaleDTO> findById(Long id) throws Exception {
        try {
            if (id == null) {
                throw new ServiceException("ID cannot be null");
            }
            dossierMedicale entity = dmRepository.findById(id);
            if (entity == null) return Optional.empty();

            DossierMedicaleDTO dto = DossierMedicalMapper.toDTO(entity);
            populatePatientInfo(dto);
            return Optional.of(dto);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération du dossier médical", e);
        }
    }

    @Override
    public List<DossierMedicaleDTO> findAll() throws Exception {
        try {
            return dmRepository.findAll().stream()
                    .map(DossierMedicalMapper::toDTO)
                    .map(this::populatePatientInfo)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des dossiers médicaux", e);
        }
    }

    private DossierMedicaleDTO populatePatientInfo(DossierMedicaleDTO dto) {
        if (dto.getPatientId() != null) {
            Patient p = patientRepository.findById(dto.getPatientId());
            if (p != null) {
                dto.setPatientNom(p.getNom());
                dto.setPatientPrenom(p.getPrenom());
            }
        }
        return dto;
    }

    @Override
    public DossierMedicaleDTO update(DossierMedicaleDTO dto) throws Exception {
        try {
            if (dto == null) throw new ServiceException("DTO null");
            dossierMedicale entity = DossierMedicalMapper.toEntity(dto);
            dmRepository.update(entity);
            return dto;
        } catch (Exception e) {
            throw new ServiceException("Erreur mise à jour dossier médical", e);
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        try {
            if (!exists(id)) return false;
            dmRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new ServiceException("Erreur suppression dossier médical", e);
        }
    }

    @Override
    public boolean exists(Long id) throws Exception {
        try {
            if (id == null) return false;
            return dmRepository.findById(id) != null;
        } catch (Exception e) {
            throw new ServiceException("Erreur vérification existence", e);
        }
    }

    @Override
    public long count() throws Exception {
        try {
            return dmRepository.findAll().size();
        } catch (Exception e) {
            throw new ServiceException("Erreur comptage", e);
        }
    }
}
