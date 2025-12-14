package ma.TeethCare.entities.medicaments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.entities.prescription.prescription;
import java.util.List;
  
@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class medicaments extends baseEntity {
    private Long id; // Was idMed
    private String nomCommercial; // Was nom
    private String principeActif; // New
    private String forme; // Was formatted out?
    private String dosage; // New
    private String type;
    private boolean remboursable;
    private Double prixUnitaire;
    private String description;
    
    private List<prescription> prescriptions;
    public static ma.TeethCare.entities.medicaments.medicaments createTestInstance() {
        return ma.TeethCare.entities.medicaments.medicaments.builder()
                .nomCommercial("Doliprane")
                .principeActif("Paracetamol")
                .forme("Comprime")
                .dosage("1000mg")
                .type("Analgesique")
                .remboursable(true)
                .prixUnitaire(15.0)
                .description("Douleur et fievre")
                .build();
    }
}
