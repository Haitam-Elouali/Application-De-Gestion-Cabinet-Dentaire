package ma.TeethCare.entities.antecedent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.common.enums.niveauDeRisque;
import ma.TeethCare.entities.patient.Patient;

@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class antecedent extends baseEntity {
    private Long id; // Was idAntecedent
    private Long dossierMedicaleId; // Correct
    private String nom;
    private String categorie;
    private niveauDeRisque niveauDeRisque; // Was niveauRisque
    
    private ma.TeethCare.entities.dossierMedicale.dossierMedicale dossierMedicale;
    // Removed patient, schema links to dossierMedicale
    
    public static antecedent createTestInstance(ma.TeethCare.entities.dossierMedicale.dossierMedicale dossierMedicale) {
        return antecedent.builder()
                .nom("Allergy to Penicillin")
                .categorie("Allergie")
                .dossierMedicale(dossierMedicale)
                .build();
    }
}
