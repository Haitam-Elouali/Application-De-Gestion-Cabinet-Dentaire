package ma.TeethCare.entities.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.entities.enums.Libeller;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class role extends baseEntity {
    private Long idRole;
    private Libeller libeller;
}
