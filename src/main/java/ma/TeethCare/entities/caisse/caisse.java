package ma.TeethCare.entities.caisse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class caisse extends baseEntity {
    private Long idCaisse;
    private Long factureId;
    private Double montant;
    private LocalDate dateEncaissement;
    private String modeEncaissement;
    private String reference;
}
