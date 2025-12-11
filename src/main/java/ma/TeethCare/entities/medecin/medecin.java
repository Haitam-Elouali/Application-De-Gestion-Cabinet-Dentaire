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
    private Long idMedecin;
    private String specialite;
    private String numeroOrdre;
    private String diplome;
    
    private agenda agendaMensuel;
    private List<consultation> consultations;
}
