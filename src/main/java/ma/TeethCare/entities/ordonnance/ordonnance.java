package ma.TeethCare.entities.ordonnance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ordonnance extends baseEntity {
    private Long idOrd;
    private Long consultationId;
    private Long medecinId;
    private Long patientId;
    private LocalDate date;
    private String duree;
    private String frequence;
}
