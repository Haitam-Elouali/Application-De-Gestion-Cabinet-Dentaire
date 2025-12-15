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
    private Long consultationId;
    private String code;
    private String description;
    private Double prix;
    private LocalDate dateActe;
    private LocalDateTime dateCreation;
}
