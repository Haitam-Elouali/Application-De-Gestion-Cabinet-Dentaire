package ma.TeethCare.entities.dossierMedicale;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class dossierMedicale extends baseEntity {
    private Long idDM;
    private Long patientId;
    private LocalDateTime dateDeCreation;
}
