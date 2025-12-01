package ma.TeethCare.entities.consultation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.entities.enums.Statut;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class consultation extends baseEntity {
    private Long idConsultation;
    private Long rdvId;
    private Long patientId;
    private Long medecinId;
    private LocalDate date;
    private Statut statut;
    private String observationMedecin;
    private String diagnostique;
}
