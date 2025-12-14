package ma.TeethCare.entities.charges;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.entities.cabinetMedicale.cabinetMedicale;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class charges extends baseEntity {
    private Long id; // Was idCharge
    private String titre;
    private String description;
    private Double montant;
    private String categorie; // New/Was there but reordered? Schema: titre, description, montant, categorie, date, cabinet_id
    private LocalDateTime date; // Schema says DATETIME
    private Long cabinetId; // New
    
    private cabinetMedicale cabinetMedicale;
    public static charges createTestInstance() {
        return charges.builder()
                .titre("Facture Electrique")
                .montant(500.0)
                .description("Electricity")
                .categorie("Energie")
                .date(LocalDateTime.now())
                .build();
    }
}
