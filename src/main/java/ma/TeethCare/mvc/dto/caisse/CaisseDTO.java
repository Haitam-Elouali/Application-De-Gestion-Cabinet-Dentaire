package ma.TeethCare.mvc.dto.caisse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object pour l'entité Caisse.
 * Utilisé pour le transfert de données entre couches.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaisseDTO {
    private Long id;
    private Double solde;
    private Double entrees;
    private Double sorties;
    private LocalDate dateOuverture;
    private LocalDate dateFermeture;
    private String statut;
    private LocalDateTime dateCreation;
}
