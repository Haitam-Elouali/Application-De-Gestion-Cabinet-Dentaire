package ma.TeethCare.entities.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.entities.patient.Patient;
import ma.TeethCare.entities.utilisateur.utilisateur;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class notification extends baseEntity {
    private Long id; // Was idNotif
    private String titre;
    private String message;
    private LocalDate date; // Schema date
    private LocalTime time; // Schema time
    private String statut; // Was lue (boolean), schema is varchar
    private String type;
    private String priorite; // New
    private Long utilisateurId; // Schema link
    
    private utilisateur utilisateur;
    public static ma.TeethCare.entities.notification.notification createTestInstance(utilisateur utilisateur) {
        return ma.TeethCare.entities.notification.notification.builder()
                .titre("Rappel")
                .message("Rdv confirmed")
                .date(LocalDate.now())
                .time(LocalTime.now())
                .statut("NON_LUE")
                .type("Rappel")
                .priorite("Haute")
                .utilisateur(utilisateur)
                .build();
    }
}
