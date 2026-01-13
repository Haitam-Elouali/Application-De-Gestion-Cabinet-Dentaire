package ma.TeethCare.service.modules.agenda.impl;

import ma.TeethCare.entities.rdv.rdv;
import ma.TeethCare.mvc.dto.rdv.RdvDTO;
import ma.TeethCare.repository.api.RdvRepository;
import ma.TeethCare.service.modules.agenda.api.rdvService;
import ma.TeethCare.service.modules.agenda.mapper.RdvMapper;
import ma.TeethCare.common.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class rdvServiceImpl implements rdvService {

    private final RdvRepository rdvRepository;

    public rdvServiceImpl(RdvRepository rdvRepository) {
        this.rdvRepository = rdvRepository;
    }

    @Override
    public RdvDTO create(RdvDTO dto) throws Exception {
        try {
            if (dto == null) {
                throw new ServiceException("RdvDTO ne peut pas être null");
            }
            rdv entity = RdvMapper.toEntity(dto);
            rdvRepository.create(entity);
            return RdvMapper.toDTO(entity);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création du RDV", e);
        }
    }

    @Override
    public Optional<RdvDTO> findById(Long id) throws Exception {
        try {
            if (id == null) {
                throw new ServiceException("ID RDV ne peut pas être null");
            }
            rdv entity = rdvRepository.findById(id);
            return Optional.ofNullable(entity).map(RdvMapper::toDTO);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération du RDV", e);
        }
    }

    @Override
    public List<RdvDTO> findAll() throws Exception {
        try {
            return rdvRepository.findAll().stream()
                    .map(RdvMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de la liste des RDVs", e);
        }
    }

    @Override
    public RdvDTO update(RdvDTO dto) throws Exception {
        try {
            if (dto == null) {
                throw new ServiceException("RdvDTO ne peut pas être null");
            }
            rdv entity = RdvMapper.toEntity(dto);
            rdvRepository.update(entity);
            return dto;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour du RDV", e);
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        try {
            if (!exists(id)) {
                return false;
            }
            rdvRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression du RDV", e);
        }
    }

    @Override
    public boolean exists(Long id) throws Exception {
        try {
            if (id == null) return false;
            return rdvRepository.findById(id) != null;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la vérification d'existence du RDV", e);
        }
    }

    @Override
    public long count() throws Exception {
        try {
            return rdvRepository.findAll().size();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des RDVs", e);
        }
    }

    @Override
    public java.util.List<RdvDTO> findTodayAppointments() throws Exception {
        try {
            return rdvRepository.findByDate(java.time.LocalDate.now()).stream()
                    .map(RdvMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des RDVs d'aujourd'hui", e);
        }
    }

    @Override
    public java.util.List<RdvDTO> findWaitingQueue() throws Exception {
        try {
            return rdvRepository.findByDate(java.time.LocalDate.now()).stream()
                    .filter(r -> r.getStatut() == ma.TeethCare.common.enums.Statut.En_attente)
                    .map(RdvMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de la file d'attente", e);
        }
    }
}
