package ma.TeethCare.entities.revenues;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.entities.facture.facture;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class revenues extends baseEntity {
    private Long id; // Was idRevenue
    // Removed factureId
    private Long cabinetId; // New
    private String titre;
    private String description;
    private Double montant;
    private String categorie; // New
    private LocalDateTime date;
    
    private facture facture; // Keep link if mapped
    public static revenues createTestInstance() {
        return revenues.builder()
                .titre("Consultation")
                .montant(150.0)
                .date(LocalDateTime.now())
                .description("Consultation fee")
                .categorie("Acte")
                .build();
    }
}
