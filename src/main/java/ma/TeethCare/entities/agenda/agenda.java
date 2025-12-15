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
    private Long id; // Was idAgenda
    private Long medecinId;
    private Mois mois;
    private Integer annee; // New
    private List<Jour> joursNonDisponibles; // Was joursDisponible
    // dateDebut and dateFin removed as they are not in schema
    
    private medecin medecin;
    public static agenda createTestInstance(ma.TeethCare.entities.medecin.medecin medecin) {
        return agenda.builder()
                .medecin(medecin)
                .mois(Mois.Janvier) // Assuming Enum
                .annee(2025)
                .build();
    }
}
