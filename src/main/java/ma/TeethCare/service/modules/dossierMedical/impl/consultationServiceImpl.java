package ma.TeethCare.service.modules.dossierMedical.impl;

import ma.TeethCare.entities.consultation.consultation;
import ma.TeethCare.mvc.dto.consultation.ConsultationDTO;
import ma.TeethCare.repository.api.ConsultationRepository;
import ma.TeethCare.service.modules.dossierMedical.api.consultationService;
import ma.TeethCare.service.modules.dossierMedical.mapper.ConsultationMapper;
import ma.TeethCare.common.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class consultationServiceImpl implements consultationService {

    private final ConsultationRepository consultationRepository;

    public consultationServiceImpl(ConsultationRepository consultationRepository) {
        this.consultationRepository = consultationRepository;
    }

    @Override
    public ConsultationDTO create(ConsultationDTO dto) throws Exception {
        try {
            if (dto == null) {
                throw new ServiceException("ConsultationDTO ne peut pas être null");
            }
            consultation entity = ConsultationMapper.toEntity(dto);
            consultationRepository.create(entity);
            return ConsultationMapper.toDTO(entity);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création de la consultation", e);
        }
    }

    @Override
    public Optional<ConsultationDTO> findById(Long id) throws Exception {
        try {
            if (id == null) {
                throw new ServiceException("ID consultation ne peut pas être null");
            }
            consultation entity = consultationRepository.findById(id);
            return Optional.ofNullable(entity).map(ConsultationMapper::toDTO);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de la consultation", e);
        }
    }

    @Override
    public List<ConsultationDTO> findAll() throws Exception {
        try {
            return consultationRepository.findAll().stream()
                    .map(ConsultationMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des consultations", e);
        }
    }

    @Override
    public ConsultationDTO update(ConsultationDTO dto) throws Exception {
        try {
            if (dto == null) {
                throw new ServiceException("ConsultationDTO ne peut pas être null");
            }
            consultation entity = ConsultationMapper.toEntity(dto);
            consultationRepository.update(entity);
            return dto;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour de la consultation", e);
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        try {
            if (!exists(id)) return false;
            consultationRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression de la consultation", e);
        }
    }

    @Override
    public boolean exists(Long id) throws Exception {
        try {
            if (id == null) return false;
            return consultationRepository.findById(id) != null;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la vérification de la consultation", e);
        }
    }

    @Override
    public long count() throws Exception {
        try {
            return consultationRepository.findAll().size();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des consultations", e);
        }
    }
}
