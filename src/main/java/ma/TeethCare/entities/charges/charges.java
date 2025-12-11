package ma.TeethCare.entities.charges;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.entities.cabinetMedicale.cabinetMedicale;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class charges extends baseEntity {
    private Long idCharge;
    private String titre;
    private String description;
    private String categorie;
    private Double montant;
    private LocalDateTime date;
    
    private cabinetMedicale cabinetMedicale;
}
