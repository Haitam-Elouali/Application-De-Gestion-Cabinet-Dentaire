package ma.TeethCare.entities.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.utilisateur.utilisateur;
import ma.TeethCare.entities.cabinetMedicale.cabinetMedicale;

@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class admin extends utilisateur {
    private String permissionAdmin;
    private String domaine;
    
    private cabinetMedicale cabinetMedicale;
}
