package ma.TeethCare.entities.antecedent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.entities.enums.niveauDeRisque;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class antecedent extends baseEntity {
    private Long idAntecedent;
    private Long dossierMedicaleId;
    private String nom;
    private String categorie;
    private niveauDeRisque niveauRisque;
}
