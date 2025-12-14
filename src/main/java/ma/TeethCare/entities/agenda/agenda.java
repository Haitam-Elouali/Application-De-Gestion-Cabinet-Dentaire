package ma.TeethCare.entities.agenda;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.common.enums.Jour;
import ma.TeethCare.common.enums.Mois;
import ma.TeethCare.entities.medecin.medecin;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class agenda extends baseEntity {
    private Long idAgenda;
    private Long medecinId;
    private Mois mois;
    private List<Jour> joursDisponible;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    
    private medecin medecin;
    public static agenda createTestInstance(ma.TeethCare.entities.medecin.medecin medecin) {
        return agenda.builder()
                .medecin(medecin)
                .dateDebut(LocalDate.now())
                .dateFin(LocalDate.now().plusYears(1))
                .build();
    }
}
