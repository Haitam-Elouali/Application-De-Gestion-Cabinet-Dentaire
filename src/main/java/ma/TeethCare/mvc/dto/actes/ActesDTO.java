package ma.TeethCare.mvc.dto.actes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object pour l'entité Actes.
 * Utilisé pour le transfert de données entre couches.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActesDTO {
    private Long id;
    private String nom;
    private String categorie;
    private String description;
    private Double prix;
    private String code;
    private Long interventionId;
    private LocalDate dateActe;
    private LocalDateTime dateCreation;
}
