package ma.TeethCare.entities.actes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.entities.interventionMedecin.interventionMedecin;
import java.util.List;
import ma.TeethCare.entities.actes.actes;
  
@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class actes extends baseEntity {
    private Long idActe;
    private String libeller;
    private String categorie;
    private double prixDeBase;
    private String codeSECU;
    
    private List<interventionMedecin> interventions;
    public static actes createTestInstance() {
        return actes.builder()
                .libeller("Detartrage")
                .prixDeBase(300.0)
                .build();
    }
}
