package ma.TeethCare.mvc.dto.facture;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object pour Facture
 * Utilisé pour les échanges HTTP
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FactureDTO {
    private Long id; // Standardized from idFacture
    private Long consultationId;
    private Long patientId;
    private BigDecimal totalFacture;
    private BigDecimal totalPaye;
    private BigDecimal reste;
    private String statut;
    private String numero;
    private LocalDateTime dateCreation;
    private LocalDateTime dateDerniereModification;
}
