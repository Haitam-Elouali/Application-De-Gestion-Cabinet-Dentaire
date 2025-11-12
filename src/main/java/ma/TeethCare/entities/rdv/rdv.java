package ma.TeethCare.entities.rdv;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.entities.enums.Statut;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class rdv extends baseEntity {
    private Long idRDV;
    private LocalDate date;
    private LocalTime heure;
    private String motif;
    private Statut statut;
    private String noteMedecin;
}
