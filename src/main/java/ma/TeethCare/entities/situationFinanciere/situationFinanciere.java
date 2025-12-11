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

@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class situationFinanciere extends baseEntity {
    private Long idSF;
    private Long patientId;
    private Long dossierMedicaleId;
    private Double totaleDesActes;
    private Double totalPaye;
    private Double credit;
    private Double reste;
    private Statut statut;
    private Promo enPromo;
    
    private dossierMedicale dossierMedicale;
    private List<facture> factures;
}
