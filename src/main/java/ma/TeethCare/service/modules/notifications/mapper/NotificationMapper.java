package ma.TeethCare.service.modules.notifications.mapper;

import ma.TeethCare.entities.notification.notification;
import ma.TeethCare.mvc.dto.notification.NotificationDTO;
import java.time.LocalDateTime;

public class NotificationMapper {

    public static NotificationDTO toDTO(notification entity) {
        if (entity == null) return null;
        
        LocalDateTime notifDateTime = null;
        if (entity.getDate() != null && entity.getTime() != null) {
            notifDateTime = LocalDateTime.of(entity.getDate(), entity.getTime());
        } else if (entity.getDate() != null) {
            notifDateTime = entity.getDate().atStartOfDay();
        }

        return NotificationDTO.builder()
                .id(entity.getId())
                .utilisateurId(entity.getUtilisateurId())
                .titre(entity.getTitre())
                .message(entity.getMessage())
                .type(entity.getType())
                .lue("LUE".equalsIgnoreCase(entity.getStatut()))
                .dateCreation(entity.getDateCreation() != null ? entity.getDateCreation().atStartOfDay() : null)
                .dateNotification(notifDateTime)
                .build();
    }

    public static notification toEntity(NotificationDTO dto) {
        if (dto == null) return null;

        notification entity = new notification();
        entity.setIdEntite(dto.getId());
        entity.setId(dto.getId());
        
        entity.setTitre(dto.getTitre());
        entity.setMessage(dto.getMessage());
        
        if (dto.getDateNotification() != null) {
            entity.setDate(dto.getDateNotification().toLocalDate());
            entity.setTime(dto.getDateNotification().toLocalTime());
        }
        
        entity.setStatut(Boolean.TRUE.equals(dto.getLue()) ? "LUE" : "NON_LUE");
        entity.setType(dto.getType());
        entity.setUtilisateurId(dto.getUtilisateurId());
        
        return entity;
    }
}
