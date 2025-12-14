package ma.TeethCare.entities.situationFinanciere;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.common.enums.Promo;
import ma.TeethCare.common.enums.Statut;
import ma.TeethCare.entities.dossierMedicale.dossierMedicale;
import ma.TeethCare.entities.facture.facture;
import java.util.List;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class situationFinanciere extends baseEntity {
    private Long id; // Was idSF
    private Double totalDesActes;
    private Double totalPaye;
    private Double credit;
    private Statut statut;
    private Promo enPromo;
    private Long dossierMedicaleId;
    
    // Relations
    private dossierMedicale dossierMedicale; 
    private List<facture> factures;

    public static situationFinanciere createTestInstance(ma.TeethCare.entities.dossierMedicale.dossierMedicale dossierMedicale) {
        return situationFinanciere.builder()
                .dossierMedicale(dossierMedicale)
                .dossierMedicaleId(dossierMedicale != null ? dossierMedicale.getId() : null)
                .totalDesActes(150.0)
                .totalPaye(0.0)
                .credit(150.0)
                .statut(Statut.En_attente)
                .enPromo(Promo.Aucune)
                .build();
    }
}
