package ma.TeethCare.mvc.dto.situationFinanciere;

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
    private Double totalDesActes;
    private Double totalPaye;
    private Double credit;
    private String statut;
    private String enPromo;
    private Long dossierMedicaleId;
    private LocalDateTime dateCreation;
}
