package ma.TeethCare.entities.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.utilisateur.utilisateur;
import ma.TeethCare.entities.cabinetMedicale.cabinetMedicale;
import ma.TeethCare.entities.admin.admin;
@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class admin extends utilisateur {
    private String permissionAdmin;
    private String domaine;
    
    private cabinetMedicale cabinetMedicale;
    public static admin createTestInstance() {
        return admin.builder()
                .nom("AdminUser")
                .email("admin@system.com")
                .cin("ADM001")
                .build();
    }
}
