package ma.TeethCare.mvc.dto.notification;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object pour l'entité Notification.
 * Utilisé pour le transfert de données entre couches.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {
    private Long id;
    private Long utilisateurId;
    private String titre;
    private String message;
    private String type;
    private Boolean lue;
    private LocalDateTime dateCreation;
    private LocalDateTime dateNotification;
}
