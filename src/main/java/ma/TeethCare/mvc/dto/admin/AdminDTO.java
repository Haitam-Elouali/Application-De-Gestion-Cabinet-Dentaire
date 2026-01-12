package ma.TeethCare.mvc.dto.admin;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object pour l'entité Admin.
 * Utilisé pour le transfert de données entre couches.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private LocalDate dateEmbauche;
    private String numSecu;
    private String statut;
    private String domaine;
    private LocalDateTime dateCreation;
}
