package ma.TeethCare.entities.utilisateur;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.common.enums.Sexe;

import ma.TeethCare.entities.role.role;
import ma.TeethCare.entities.notification.notification;
import ma.TeethCare.entities.log.log;
import java.util.List;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class utilisateur extends baseEntity {
    private Long id; // Was idUser
    private String nom;
    private String prenom; // New
    private String adresse;
    private String telephone; // Was tel
    private String email;
    private String cin;
    private LocalDate dateNaissance;
    private Sexe sexe;
    private String image; // New
    private String username; // Was login
    private String password; // Was motDePasse
    
    private List<role> roles;
    private List<notification> notifications;
    private List<log> logs;
    public static utilisateur createTestInstance() {
        return utilisateur.builder()
                .nom("Utilisateur Test")
                .prenom("Test")
                .email("test@user.com")
                .username("testuser")
                .password("password")
                .telephone("0600000000")
                .dateNaissance(LocalDate.of(1990, 1, 1))
                .sexe(Sexe.Homme)
                .build();
    }
}
