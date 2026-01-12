package ma.TeethCare.service.modules.notifications.impl;

import ma.TeethCare.entities.notification.notification;
import ma.TeethCare.mvc.dto.notification.NotificationDTO;
import ma.TeethCare.repository.api.NotificationRepository;
import ma.TeethCare.service.modules.notifications.api.notificationService;
import ma.TeethCare.service.modules.notifications.mapper.NotificationMapper;
import ma.TeethCare.common.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class notificationServiceImpl implements notificationService {

    private final NotificationRepository notificationRepository;

    public notificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public NotificationDTO create(NotificationDTO dto) throws Exception {
        try {
            if (dto == null) throw new ServiceException("DTO null");
            notification entity = NotificationMapper.toEntity(dto);
            notificationRepository.create(entity);
            return NotificationMapper.toDTO(entity);
        } catch (Exception e) {
            throw new ServiceException("Erreur création notification", e);
        }
    }

    @Override
    public Optional<NotificationDTO> findById(Long id) throws Exception {
        try {
            if (id == null) throw new ServiceException("ID null");
            notification entity = notificationRepository.findById(id);
            return Optional.ofNullable(entity).map(NotificationMapper::toDTO);
        } catch (Exception e) {
            throw new ServiceException("Erreur recherche notification", e);
        }
    }

    @Override
    public List<NotificationDTO> findAll() throws Exception {
        try {
            return notificationRepository.findAll().stream()
                    .map(NotificationMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Erreur listing notifications", e);
        }
    }

    @Override
    public NotificationDTO update(NotificationDTO dto) throws Exception {
        try {
            if (dto == null) throw new ServiceException("DTO null");
            notification entity = NotificationMapper.toEntity(dto);
            notificationRepository.update(entity);
            return dto;
        } catch (Exception e) {
            throw new ServiceException("Erreur mise à jour notification", e);
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        try {
            if (!exists(id)) return false;
            notificationRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new ServiceException("Erreur suppression notification", e);
        }
    }

    @Override
    public boolean exists(Long id) throws Exception {
        try {
            if (id == null) return false;
            return notificationRepository.findById(id) != null;
        } catch (Exception e) {
            throw new ServiceException("Erreur existence notification", e);
        }
    }

    @Override
    public long count() throws Exception {
        try {
            return notificationRepository.findAll().size();
        } catch (Exception e) {
            throw new ServiceException("Erreur comptage notifications", e);
        }
    }

    @Override
    public List<NotificationDTO> findByNonLues() throws Exception {
        return List.of();
    }

    @Override
    public List<NotificationDTO> findByType(String type) throws Exception {
        return List.of();
    }
}
