package ma.TeethCare.entities.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.entities.patient.Patient;
import ma.TeethCare.entities.utilisateur.utilisateur;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class notification extends baseEntity {
    private Long idNotif;
    private String titre;
    private String message;
    private LocalDateTime dateEnvoi;
    private String type;
    private boolean lue;
    
    private utilisateur utilisateur;
    public static ma.TeethCare.entities.notification.notification createTestInstance(utilisateur utilisateur) {
        return ma.TeethCare.entities.notification.notification.builder()
                .message("Rdv confirmed")
                .dateEnvoi(LocalDateTime.now())
                .utilisateur(utilisateur)
                .build();
    }
}
