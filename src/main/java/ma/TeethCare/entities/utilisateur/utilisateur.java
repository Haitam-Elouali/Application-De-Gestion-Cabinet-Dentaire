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
    private Long idUser;
    private String nom;
    private String email;
    private String adresse;
    private String cin;
    private String tel;
    private Sexe sexe;
    private String login;
    private String motDePasse;
    private LocalDate lastLoginDate;
    private LocalDate dateNaissance;
    
    private List<role> roles;
    private List<notification> notifications;
    private List<log> logs;
    public static utilisateur createTestInstance() {
        return utilisateur.builder()
                .nom("Utilisateur Test")
                .email("test@user.com")
                .login("testuser")
                .motDePasse("password")
                .dateNaissance(LocalDate.of(1990, 1, 1))
                .build();
    }
}
