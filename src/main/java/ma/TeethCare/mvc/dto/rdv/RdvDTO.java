package ma.TeethCare.mvc.dto.rdv;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * Data Transfer Object pour RDV
 * Utilisé pour les échanges HTTP
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RdvDTO {
    private Long idRDV;
    private Long patientId;
    private Long medecinId;
    private LocalDate date;
    private LocalTime heure;
    private String motif;
    private String statut;
    private LocalDateTime dateCreation;
    private LocalDateTime dateDerniereModification;
}
