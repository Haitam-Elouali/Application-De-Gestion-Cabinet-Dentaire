package ma.TeethCare.entities.prescription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class prescription extends baseEntity {
    private Long idPr;
    private Long ordonnanceId;
    private Long medicamentId;
    private int quantite;
    private String frequence;
    private int dureeEnjours;
}
