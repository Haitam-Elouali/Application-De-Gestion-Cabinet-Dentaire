package ma.TeethCare.entities.medecin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.staff.staff;
import ma.TeethCare.entities.agenda.agenda;
import ma.TeethCare.entities.consultation.consultation;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class medecin extends staff {
    private Long id; // Was idMedecin
    private String specialite;
    // Removed numeroOrdre, diplome as not in schema.
    
    private agenda agendaMensuel;
    private List<consultation> consultations;
    public static medecin createTestInstance() {
        return medecin.builder()
                .nom("House")
                .email("house@hospital.com")
                .username("house") // Inherited from admin/user
                .password("vicodin")
                .specialite("Diagnostician")
                .build();
    }
}
