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
    private Long id; // Was idActe, but schema says id. Keeping consistency with repository generic ID might be tricky if CrudRepository expects specific name, but standard is often 'id'. Actually schema says 'id'.
    // However, typical JPA uses @Id private Long id; inheriting from baseEntity? 
    // baseEntity has idEntite. Schema has id for every table. 
    // User wants attributes to match schema.
    // I will rename fields.
    
    private String nom; // Was libeller
    private String categorie;
    private String description; // New
    private Double prix; // Was prixDeBase
    private String code; // Was codeSECU
    private Long interventionId; // Schema has intervention_id

    public static actes createTestInstance() {
        return actes.builder()
                .nom("Detartrage")
                .description("Nettoyage des dents")
                .categorie("Prevention")
                .prix(300.0)
                .code("D123")
                .build();
    }
}
