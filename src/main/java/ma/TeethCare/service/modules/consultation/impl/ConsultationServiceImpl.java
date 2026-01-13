package ma.TeethCare.service.modules.consultation.impl;

import ma.TeethCare.common.enums.Statut;
import ma.TeethCare.entities.consultation.consultation;
import ma.TeethCare.entities.patient.Patient;
import ma.TeethCare.mvc.dto.consultation.ConsultationDTO;
import ma.TeethCare.repository.api.ConsultationRepository;
import ma.TeethCare.repository.api.PatientRepository;
import ma.TeethCare.repository.mySQLImpl.PatientRepositoryImpl;
import ma.TeethCare.service.modules.consultation.api.ConsultationService;
import ma.TeethCare.service.modules.dossierMedical.mapper.ConsultationMapper;
import ma.TeethCare.common.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final PatientRepository patientRepository;

    public ConsultationServiceImpl(ConsultationRepository consultationRepository) {
        this.consultationRepository = consultationRepository;
        this.patientRepository = new PatientRepositoryImpl();
    }

    @Override
    public ConsultationDTO create(ConsultationDTO dto) throws Exception {
        try {
            if (dto == null) throw new ServiceException("DTO null");
            consultation entity = ConsultationMapper.toEntity(dto);
            // Default status if null
            if (entity.getStatut() == null) entity.setStatut(Statut.Planifiee);
            
            consultationRepository.create(entity);
            return ConsultationMapper.toDTO(entity);
        } catch (Exception e) {
            throw new ServiceException("Erreur création consultation", e);
        }
    }

    @Override
    public Optional<ConsultationDTO> findById(Long id) throws Exception {
        try {
            if (id == null) return Optional.empty();
            consultation entity = consultationRepository.findById(id);
            if (entity == null) return Optional.empty();
            
            ConsultationDTO dto = ConsultationMapper.toDTO(entity);
            populatePatient(dto);
            return Optional.of(dto);
        } catch (Exception e) {
            throw new ServiceException("Erreur findById consultation", e);
        }
    }

    @Override
    public List<ConsultationDTO> findAll() throws Exception {
        try {
            return consultationRepository.findAll().stream()
                    .map(ConsultationMapper::toDTO)
                    .map(this::populatePatient)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Erreur findAll consultation", e);
        }
    }

    private ConsultationDTO populatePatient(ConsultationDTO dto) {
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
    public ConsultationDTO update(ConsultationDTO dto) throws Exception {
        try {
            consultation entity = ConsultationMapper.toEntity(dto);
            consultationRepository.update(entity);
            return dto;
        } catch (Exception e) {
            throw new ServiceException("Erreur update consultation", e);
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        try {
            if (!exists(id)) return false;
            consultationRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new ServiceException("Erreur delete consultation", e);
        }
    }

    @Override
    public boolean exists(Long id) throws Exception {
        try {
            return id != null && consultationRepository.findById(id) != null;
        } catch (Exception e) {
            throw new ServiceException("Erreur exists consultation", e);
        }
    }

    @Override
    public long count() throws Exception {
        try {
            return consultationRepository.findAll().size();
        } catch (Exception e) {
            throw new ServiceException("Erreur count consultation", e);
        }
    }
}
