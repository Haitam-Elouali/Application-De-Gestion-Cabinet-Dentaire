package ma.TeethCare.entities.medicaments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class medicaments extends baseEntity {
    private Long idMed;
    private String nom;
    private String laboratoire;
    private String type;
    //private Forme forme;
    private boolean remboursable;
    private Double prixUnitaire;
    private String description;
}
