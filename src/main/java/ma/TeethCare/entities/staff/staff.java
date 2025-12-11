package ma.TeethCare.entities.staff;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.utilisateur.utilisateur;
import ma.TeethCare.entities.cabinetMedicale.cabinetMedicale;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class staff extends utilisateur {
    private Double salaire;
    private Double prime;
    private LocalDate dateRecrutement;
    private int soldeConge;
    
    private cabinetMedicale cabinetMedicale;
}
