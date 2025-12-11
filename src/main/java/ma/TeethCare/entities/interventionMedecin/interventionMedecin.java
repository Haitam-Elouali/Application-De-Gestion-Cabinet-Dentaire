package ma.TeethCare.entities.interventionMedecin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.entities.medecin.medecin;
import ma.TeethCare.entities.actes.actes;
import ma.TeethCare.entities.consultation.consultation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class interventionMedecin extends baseEntity {
    private Long idIM;
    private Long medecinId;
    private Long acteId;
    private Long consultationId;
    private Double prixDePatient;
    private Integer numDent;
    
    private medecin medecin;
    private actes acte;
    private consultation consultation;
}
