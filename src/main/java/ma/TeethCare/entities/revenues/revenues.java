package ma.TeethCare.entities.revenues;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class revenues extends baseEntity {
    private Long idRevenue;
    private Long factureId;
    private String titre;
    private String description;
    private Double montant;
    private LocalDateTime date;
}
