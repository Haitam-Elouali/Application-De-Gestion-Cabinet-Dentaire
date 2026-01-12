package ma.TeethCare.mvc.dto.revenues;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object pour l'entité Revenues.
 * Utilisé pour le transfert de données entre couches.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevenuesDTO {
    private Long id;
    private Long cabinetId;
    private String titre;
    private String description;
    private Double montant;
    private String categorie;
    private LocalDateTime date;
    private LocalDateTime dateCreation;
}
