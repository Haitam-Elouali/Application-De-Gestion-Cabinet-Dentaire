package ma.TeethCare.mvc.dto.staff;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object pour l'entité Staff.
 * Utilisé pour le transfert de données entre couches.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffDTO {
    private Long id;
    private String nom;
    private String prenom;
    private Long roleId;
    private String telephone;
    private String email;
    private LocalDate dateEmbauche;
    private String cin;
    private String statut;
    private LocalDateTime dateCreation;
}
