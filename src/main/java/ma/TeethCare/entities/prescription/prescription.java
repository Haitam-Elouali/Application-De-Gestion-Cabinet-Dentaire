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
@Builder
public class prescription extends baseEntity {
    private Long idPr;
    private Long ordonnanceId;
    private Long medicamentId;
    private int quantite;
    private String frequence;
    private int dureeEnjours;
    
    private ordonnance ordonnance;
    private medicaments medicament;
}
