spackage ma.TeethCare.entities.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.utilisateur.utilisateur;
import ma.TeethCare.entities.cabinetMedicale.cabinetMedicale;
@Data
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class admin extends utilisateur {
    // Schema 'admin' only has 'id'. It inherits from 'utilisateur' (which has nom, prenom, etc.)
    // No specific fields in schema for admin table.
    
    public static admin createTestInstance() {
        return admin.builder()
                .nom("AdminUser")
                .email("admin@system.com")
                .username("admin") // Schema has username
                .password("password") // Schema has password
                .build();
    }
}
