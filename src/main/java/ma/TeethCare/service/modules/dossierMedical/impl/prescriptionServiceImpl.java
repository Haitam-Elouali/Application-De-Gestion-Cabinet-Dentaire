package ma.TeethCare.service.modules.dossierMedical.impl;

import ma.TeethCare.entities.prescription.prescription;
import ma.TeethCare.mvc.dto.prescription.PrescriptionDTO;
import ma.TeethCare.repository.api.PrescriptionRepository;
import ma.TeethCare.service.modules.dossierMedical.api.prescriptionService;
import ma.TeethCare.service.modules.dossierMedical.mapper.PrescriptionMapper;
import ma.TeethCare.common.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class prescriptionServiceImpl implements prescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    public prescriptionServiceImpl(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    @Override
    public PrescriptionDTO create(PrescriptionDTO dto) throws Exception {
        try {
            if (dto == null) throw new ServiceException("DTO null");
            prescription entity = PrescriptionMapper.toEntity(dto);
            prescriptionRepository.create(entity);
            return PrescriptionMapper.toDTO(entity);
        } catch (Exception e) {
            throw new ServiceException("Erreur création prescription", e);
        }
    }

    @Override
    public Optional<PrescriptionDTO> findById(Long id) throws Exception {
        try {
            if (id == null) throw new ServiceException("ID null");
            prescription entity = prescriptionRepository.findById(id);
            return Optional.ofNullable(entity).map(PrescriptionMapper::toDTO);
        } catch (Exception e) {
            throw new ServiceException("Erreur recherche prescription", e);
        }
    }

    @Override
    public List<PrescriptionDTO> findAll() throws Exception {
        try {
            return prescriptionRepository.findAll().stream()
                    .map(PrescriptionMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Erreur listing prescriptions", e);
        }
    }

    @Override
    public PrescriptionDTO update(PrescriptionDTO dto) throws Exception {
        try {
            if (dto == null) throw new ServiceException("DTO null");
            prescription entity = PrescriptionMapper.toEntity(dto);
            prescriptionRepository.update(entity);
            return dto;
        } catch (Exception e) {
            throw new ServiceException("Erreur mise à jour prescription", e);
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        try {
            if (!exists(id)) return false;
            prescriptionRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new ServiceException("Erreur suppression prescription", e);
        }
    }

    @Override
    public boolean exists(Long id) throws Exception {
        try {
            if (id == null) return false;
            return prescriptionRepository.findById(id) != null;
        } catch (Exception e) {
            throw new ServiceException("Erreur existence prescription", e);
        }
    }

    @Override
    public long count() throws Exception {
        try {
            return prescriptionRepository.findAll().size();
        } catch (Exception e) {
            throw new ServiceException("Erreur comptage prescriptions", e);
        }
    }
}
