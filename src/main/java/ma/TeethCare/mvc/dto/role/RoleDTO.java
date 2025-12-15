package ma.TeethCare.mvc.dto.role;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object pour l'entité Role.
 * Utilisé pour le transfert de données entre couches.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDTO {
    private Long id;
    private String nom;
    private String description;
    private String permissions;
    private LocalDateTime dateCreation;
}
