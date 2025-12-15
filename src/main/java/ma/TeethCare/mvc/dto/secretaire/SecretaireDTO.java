package ma.TeethCare.mvc.dto.secretaire;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object pour l'entité Secretaire.
 * Utilisé pour le transfert de données entre couches.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecretaireDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private LocalDate dateEmbauche;
    private String numSecu;
    private String statut;
    private LocalDateTime dateCreation;
}
