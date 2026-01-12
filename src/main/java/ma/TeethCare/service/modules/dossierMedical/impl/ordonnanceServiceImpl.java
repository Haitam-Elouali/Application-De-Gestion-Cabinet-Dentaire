package ma.TeethCare.service.modules.dossierMedical.impl;

import ma.TeethCare.entities.ordonnance.ordonnance;
import ma.TeethCare.mvc.dto.ordonnance.OrdonnanceDTO;
import ma.TeethCare.repository.api.OrdonnanceRepository;
import ma.TeethCare.service.modules.dossierMedical.api.ordonnanceService;
import ma.TeethCare.service.modules.dossierMedical.mapper.OrdonnanceMapper;
import ma.TeethCare.common.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ordonnanceServiceImpl implements ordonnanceService {

    private final OrdonnanceRepository ordonnanceRepository;

    public ordonnanceServiceImpl(OrdonnanceRepository ordonnanceRepository) {
        this.ordonnanceRepository = ordonnanceRepository;
    }

    @Override
    public OrdonnanceDTO create(OrdonnanceDTO dto) throws Exception {
        try {
            if (dto == null) throw new ServiceException("DTO null");
            ordonnance entity = OrdonnanceMapper.toEntity(dto);
            ordonnanceRepository.create(entity);
            return OrdonnanceMapper.toDTO(entity);
        } catch (Exception e) {
            throw new ServiceException("Erreur création ordonnance", e);
        }
    }

    @Override
    public Optional<OrdonnanceDTO> findById(Long id) throws Exception {
        try {
            if (id == null) throw new ServiceException("ID null");
            ordonnance entity = ordonnanceRepository.findById(id);
            return Optional.ofNullable(entity).map(OrdonnanceMapper::toDTO);
        } catch (Exception e) {
            throw new ServiceException("Erreur recherche ordonnance", e);
        }
    }

    @Override
    public List<OrdonnanceDTO> findAll() throws Exception {
        try {
            return ordonnanceRepository.findAll().stream()
                    .map(OrdonnanceMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Erreur listing ordonnances", e);
        }
    }

    @Override
    public OrdonnanceDTO update(OrdonnanceDTO dto) throws Exception {
        try {
            if (dto == null) throw new ServiceException("DTO null");
            ordonnance entity = OrdonnanceMapper.toEntity(dto);
            ordonnanceRepository.update(entity);
            return dto;
        } catch (Exception e) {
            throw new ServiceException("Erreur mise à jour ordonnance", e);
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        try {
            if (!exists(id)) return false;
            ordonnanceRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new ServiceException("Erreur suppression ordonnance", e);
        }
    }

    @Override
    public boolean exists(Long id) throws Exception {
        try {
            if (id == null) return false;
            return ordonnanceRepository.findById(id) != null;
        } catch (Exception e) {
            throw new ServiceException("Erreur existence ordonnance", e);
        }
    }

    @Override
    public long count() throws Exception {
        try {
            return ordonnanceRepository.findAll().size();
        } catch (Exception e) {
            throw new ServiceException("Erreur comptage ordonnances", e);
        }
    }
}
