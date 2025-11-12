package ma.TeethCare.entities.charges;

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
public class charges extends baseEntity {
    private String titre;
    private String description;
    private Double montant;
    private LocalDateTime date;
}
