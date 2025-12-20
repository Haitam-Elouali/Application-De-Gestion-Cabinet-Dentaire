package ma.TeethCare.service.modules.users.dto;

import ma.TeethCare.common.enums.Sexe;

import java.time.LocalDate;

public record CreateMedecinRequest(
                String nom,
                String email,
                String adresse,
                String cin,
                String tel,
                Sexe sexe,
                String login,
                String motDePasse,
                LocalDate dateNaissance,
                Double salaire,
                Double prime,
                LocalDate dateRecrutement,
                Integer soldeConge,
                String specialite) {
}
