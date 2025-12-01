package ma.TeethCare.mvc.dto.situationFinanciere;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object pour l'entité SituationFinanciere.
 * Utilisé pour le transfert de données entre couches.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SituationFinanciereDTO {
    private Long id;
    private LocalDate date;
    private Double totalRevenues;
    private Double totalCharges;
    private Double benefice;
    private Double tresorerie;
    private LocalDateTime dateCreation;
}
