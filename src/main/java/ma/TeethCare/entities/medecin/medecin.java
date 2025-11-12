package ma.TeethCare.entities.medecin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.staff.staff;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class medecin extends staff {
    private String specialite;
    //private AgendaDocteur agendaMensuel;
}
