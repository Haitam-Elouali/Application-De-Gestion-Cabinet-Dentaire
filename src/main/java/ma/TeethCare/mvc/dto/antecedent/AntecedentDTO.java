package ma.TeethCare.mvc.dto.antecedent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object pour l'entité Antecedent.
 * Utilisé pour le transfert de données entre couches.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AntecedentDTO {
    private Long id;
    private Long dossierMedicaleId;
    private String description;
    private String type;
    private LocalDate dateAntecedent;
    private LocalDateTime dateCreation;
}
