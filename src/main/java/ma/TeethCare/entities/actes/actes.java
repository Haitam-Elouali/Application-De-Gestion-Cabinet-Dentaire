package ma.TeethCare.entities.actes;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class actes extends baseEntity {
    private Long idIm;
    private String libeller;
    private String categorie;
    private double prixDeBase;
}
