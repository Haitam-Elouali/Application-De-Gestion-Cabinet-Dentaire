package ma.TeethCare.entities.agenda;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.entities.enums.Jour;
import ma.TeethCare.entities.enums.Mois;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class agenda extends baseEntity {
    private Mois mois;
    private List<Jour> joursonDisponible;
}
