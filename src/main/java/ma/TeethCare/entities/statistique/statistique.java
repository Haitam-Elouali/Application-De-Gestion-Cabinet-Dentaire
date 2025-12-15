package ma.TeethCare.entities.statistique;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.entities.cabinetMedicale.cabinetMedicale;
import ma.TeethCare.entities.statistique.statistique;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class statistique extends baseEntity {

    private Long id;
    private String nom;
    private Double chiffre;
    private String type;
    private LocalDate dateCalcul;

    private Long cabinetId;
    private cabinetMedicale cabinetMedicale;

    public static statistique createTestInstance() {
        return statistique.builder()
                .nom("Chiffre d'affaire Mensuel")
                .chiffre(15000.0)
                .type("SITUATION_FINANCIERE")
                .dateCalcul(LocalDate.now())
                .cabinetId(1L)
                .build();
    }
}
