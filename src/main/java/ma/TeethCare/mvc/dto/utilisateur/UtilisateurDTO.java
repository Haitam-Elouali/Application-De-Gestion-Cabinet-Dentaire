package ma.TeethCare.mvc.dto.utilisateur;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Data Transfer Object pour Utilisateur
 * Utilisé pour les échanges HTTP
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilisateurDTO {
    private Long idUser;
    private String nom;
    private String prenom;
    private String email;
    private String login;
    private String motDePasse;
    private Long roleId;
    private String statut;
    private LocalDateTime dateCreation;
    private LocalDateTime dateDerniereModification;
}
