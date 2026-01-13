package ma.TeethCare.mvc.dto.ordonnance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object pour Ordonnance
 * Utilisé pour les échanges HTTP
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdonnanceDTO {
    private Long idOrd;
    private Long consultationId;
    private Long medecinId;
    private Long patientId;
    private LocalDate date;
    private Integer duree;
    private String frequence;
    private String notes;
    private LocalDateTime dateCreation;
    private LocalDateTime dateDerniereModification;
    
    private String patientNom;
    private String patientPrenom;
}
