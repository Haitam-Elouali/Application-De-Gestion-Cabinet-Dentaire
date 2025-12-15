package ma.TeethCare.entities.caisse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.entities.facture.facture;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class caisse extends baseEntity {
    private Long idCaisse;
    private Long factureId;
    private Double montant;
    private LocalDate dateEncaissement;
    private String modeEncaissement;
    private String reference;
    
    private facture facture;
    public static caisse createTestInstance(ma.TeethCare.entities.facture.facture facture) {
        return caisse.builder()
                .montant(100.0)
                .dateEncaissement(LocalDate.now())
                .modeEncaissement("Espece")
                .facture(facture)
                .build();
    }
}
