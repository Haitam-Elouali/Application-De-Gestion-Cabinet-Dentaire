package ma.TeethCare.mvc.dto.certificat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object pour l'entité Certificat.
 * Utilisé pour le transfert de données entre couches.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificatDTO {
    private Long id;
    private Long consultationId;
    private Long medecinId;
    private String numero;
    private LocalDate dateEmission;
    private LocalDate dateExpiration;
    private String motif;
    private LocalDateTime dateCreation;
}
