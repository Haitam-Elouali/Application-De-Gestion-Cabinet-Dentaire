package ma.TeethCare.entities.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.common.enums.Libeller;
import ma.TeethCare.entities.utilisateur.utilisateur;

import java.util.List;
  
@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class role extends baseEntity {
    private Long id; // Was idRole
    private String libelle; // Was libeller (Enum)
    private Long utilisateurId; // Schema says utilisateur_id
    
    // Removed description, utilisateurs list
    
    private utilisateur utilisateur;
    public static ma.TeethCare.entities.role.role createTestInstance() {
        return ma.TeethCare.entities.role.role.builder()
                .libelle("ADMIN")
                .build();
    }
}
