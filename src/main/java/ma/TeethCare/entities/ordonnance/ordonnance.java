package ma.TeethCare.entities.ordonnance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.entities.consultation.consultation;
import ma.TeethCare.entities.prescription.prescription;
import java.util.List;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class ordonnance extends baseEntity {
    private Long id; // Was idOrd
    private Long consultationId;
    // Removed medecinId, patientId, duree, frequence not in schema
    private LocalDate dateOrdonnance; // Was date
    
    private consultation consultation;
    private List<prescription> prescriptions;
    public static ordonnance createTestInstance(ma.TeethCare.entities.consultation.consultation consultation) {
        return ordonnance.builder()
                .dateOrdonnance(LocalDate.now())
                .consultation(consultation)
                .build();
    }
}
