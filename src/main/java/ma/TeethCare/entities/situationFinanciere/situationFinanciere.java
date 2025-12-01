package ma.TeethCare.entities.situationFinanciere;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.entities.enums.Promo;
import ma.TeethCare.entities.enums.Statut;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class situationFinanciere extends baseEntity {
    private Long idSF;
    private Long patientId;
    private Double totaleDesActes;
    private Double totalPaye;
    private Double credit;
    private Double reste;
    private Statut statut;
    private Promo enPromo;
}
