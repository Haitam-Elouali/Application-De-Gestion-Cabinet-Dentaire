package ma.TeethCare.mvc.dto.medicament;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object pour l'entité Medicament.
 * Utilisé pour le transfert de données entre couches.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicamentDTO {
    private Long id;
    private String nom;
    private String description;
    private String composition;
    private Double prix;
    private Integer quantiteStock;
    private String dosage;
    private LocalDateTime dateCreation;
}
