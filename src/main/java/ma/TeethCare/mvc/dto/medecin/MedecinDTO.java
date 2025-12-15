package ma.TeethCare.mvc.dto.medecin;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object pour l'entité Medecin.
 * Utilisé pour le transfert de données entre couches.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedecinDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String specialite;
    private String telephone;
    private String email;
    private String numeroLicence;
    private LocalDateTime dateCreation;
}
