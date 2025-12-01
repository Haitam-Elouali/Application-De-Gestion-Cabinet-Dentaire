package ma.TeethCare.mvc.dto.charges;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object pour l'entité Charges.
 * Utilisé pour le transfert de données entre couches.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargesDTO {
    private Long id;
    private String description;
    private Double montant;
    private LocalDate dateCharge;
    private String categorie;
    private String statut;
    private LocalDateTime dateCreation;
}
