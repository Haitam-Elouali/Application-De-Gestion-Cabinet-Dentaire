package ma.TeethCare.mvc.dto.dossierMedicale;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object pour l'entité DossierMedicale.
 * Utilisé pour le transfert de données entre couches.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DossierMedicaleDTO {
    private Long id;
    private Long patientId;
    private String numero;
    private String diagnostic;
    private String historique;
    private LocalDateTime dateCreation;
    private LocalDateTime dateDerniereModification;
    
    // UI convenience fields
    private String patientNom;
    private String patientPrenom;
}
