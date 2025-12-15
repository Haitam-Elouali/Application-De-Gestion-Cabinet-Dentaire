package ma.TeethCare.mvc.dto.log;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object pour l'entité Log.
 * Utilisé pour le transfert de données entre couches.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogDTO {
    private Long id;
    private String utilisateurId;
    private String action;
    private String description;
    private String module;
    private LocalDateTime dateLog;
}
