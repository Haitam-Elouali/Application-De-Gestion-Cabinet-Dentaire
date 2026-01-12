package ma.TeethCare.mvc.dto.consultation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object pour Consultation
 * Utilisé pour les échanges HTTP
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultationDTO {
    private Long id; // Standardized from idConsultation
    private Long rdvId;
    private Long patientId;
    private Long medecinId;
    private LocalDate date;
    private String diagnostique;
    private String notes;
    private LocalDateTime dateCreation;
    private LocalDateTime dateDerniereModification;
}
