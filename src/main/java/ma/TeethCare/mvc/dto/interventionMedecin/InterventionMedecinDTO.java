package ma.TeethCare.mvc.dto.interventionMedecin;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object pour l'entité InterventionMedecin.
 * Utilisé pour le transfert de données entre couches.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterventionMedecinDTO {
    private Long id;
    private Long consultationId;
    private Integer duree;
    private String note;
    private String resultatImagerie;
    private LocalDateTime dateCreation;
}
