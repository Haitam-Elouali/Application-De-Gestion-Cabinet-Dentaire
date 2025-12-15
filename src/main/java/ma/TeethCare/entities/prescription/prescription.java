package ma.TeethCare.entities.prescription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.entities.ordonnance.ordonnance;
import ma.TeethCare.entities.medicaments.medicaments;

@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class prescription extends baseEntity {
    private Long id; // Was idPr
    private Long ordonnanceId;
    private Long medicamentId;
    private int quantite;
    private String posologie; // New
    private int dureeEnJours; // Was dureeEnjours (capital J? Schema says J)
    // Removed frequence
    
    private ordonnance ordonnance;
    private medicaments medicament;
    public static prescription createTestInstance(ma.TeethCare.entities.ordonnance.ordonnance ordonnance, ma.TeethCare.entities.medicaments.medicaments medicament) {
        return prescription.builder()
                .ordonnance(ordonnance)
                .medicament(medicament)
                .dureeEnJours(5)
                .quantite(2)
                .posologie("2 per day")
                .build();
    }
}
