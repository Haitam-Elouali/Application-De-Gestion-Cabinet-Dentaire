package ma.TeethCare.entities.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.utilisateur.utilisateur;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class admin extends utilisateur {
    private String permissionAdmin;
    private String domaine;
}
