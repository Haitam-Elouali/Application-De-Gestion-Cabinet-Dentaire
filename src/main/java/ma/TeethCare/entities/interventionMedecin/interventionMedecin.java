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
    private Long id; // Was idIM
    private Long consultationId;
    private int duree; // New
    private String note; // New
    private String resultatImagerie; // New
    // Removed medecinId, acteId, prixDePatient, numDent as not in Schema
    
    private consultation consultation;
    // Removed medecin, acte references as fields removed

    public static interventionMedecin createTestInstance(ma.TeethCare.entities.consultation.consultation consultation) {
        return interventionMedecin.builder()
                .consultation(consultation)
                .duree(30)
                .note("Intervention successful")
                .resultatImagerie("img_123.jpg")
                .build();
    }
}
