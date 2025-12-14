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
    private Long idRole;
    private Libeller libeller;
    private String description;
    
    private List<utilisateur> utilisateurs;
    public static ma.TeethCare.entities.role.role createTestInstance() {
        return ma.TeethCare.entities.role.role.builder()
                .libeller(ma.TeethCare.common.enums.Libeller.Admin)
                .build();
    }
}
