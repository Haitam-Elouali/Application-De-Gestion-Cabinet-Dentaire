package ma.TeethCare.entities.utilisateur;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.entities.enums.Sexe;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
}
