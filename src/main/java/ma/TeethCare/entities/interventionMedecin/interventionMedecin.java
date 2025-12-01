package ma.TeethCare.entities.interventionMedecin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class interventionMedecin extends baseEntity {
    private Long idIM;
    private Long medecinId;
    private Long acteId;
    private Long consultationId;
    private Double prixDePatient;
    private Integer numDent;
}
