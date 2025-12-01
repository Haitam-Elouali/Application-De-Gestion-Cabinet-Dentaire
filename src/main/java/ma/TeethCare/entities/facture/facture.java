package ma.TeethCare.entities.facture;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.entities.enums.Statut;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class facture extends baseEntity {
    private Long idFacture;
    private Long consultationId;
    private Long patientId;
    private Double totaleFacture;
    private Double totalPaye;
    private Double reste;
    private Statut statut;
    private LocalDateTime dateFacture;
}
