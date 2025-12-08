package ma.TeethCare.entities.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.entities.utilisateur.utilisateur;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class notification extends baseEntity {
    private Long idNotif;
    private String titre;
    private String message;
    private LocalDateTime dateEnvoi;
    private String type;
    private boolean lue;
    
    private utilisateur utilisateur;
}
