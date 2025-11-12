package ma.TeethCare.entities.certificat;

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

public class certificat extends baseEntity {
    private Long idCertif;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private int dureer;
    private String noteMedecin;
}
