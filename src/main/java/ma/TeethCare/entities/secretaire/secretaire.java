package ma.TeethCare.entities.secretaire;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.staff.staff;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class secretaire extends staff {
    private String numCNSS;
    private Double commission;
}
