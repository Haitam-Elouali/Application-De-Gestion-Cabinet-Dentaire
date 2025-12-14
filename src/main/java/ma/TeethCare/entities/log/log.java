package ma.TeethCare.entities.log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.entities.utilisateur.utilisateur;
import ma.TeethCare.entities.log.log;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class log extends baseEntity {
    private Long idLog;
    private String action;
    private String utilisateur;
    private LocalDateTime dateAction;
    private String description;
    private String adresseIP;
    
    private utilisateur utilisateurEntity;
    public static log createTestInstance() {
        return log.builder()
                .action("System started")
                .dateAction(LocalDateTime.now())
                .build();
    }
}
