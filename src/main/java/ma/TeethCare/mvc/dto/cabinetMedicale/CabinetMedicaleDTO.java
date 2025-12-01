package ma.TeethCare.mvc.dto.cabinetMedicale;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object pour l'entité CabinetMedicale.
 * Utilisé pour le transfert de données entre couches.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CabinetMedicaleDTO {
    private Long id;
    private String nom;
    private String adresse;
    private String telephone;
    private String email;
    private String siteWeb;
    private String numeroLicence;
    private String directeur;
    private LocalDateTime dateCreation;
}
