package ma.TeethCare.mvc.dto.prescription;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object pour l'entité Prescription.
 * Utilisé pour le transfert de données entre couches.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionDTO {
    private Long id;
    private Long ordonnanceId;
    private Long medicamentId;
    private String dosage;
    private String frequence;
    private Integer duree;
    private String instructions;
    private LocalDateTime dateCreation;
}
