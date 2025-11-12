package ma.TeethCare.entities.dossierMedicale;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class dossierMedicale extends baseEntity {
    private Long idDM;
    private String dateDeCreation;
}
