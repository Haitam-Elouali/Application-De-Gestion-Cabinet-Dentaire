package ma.TeethCare.service.modules.users.dto;

import ma.TeethCare.common.enums.Sexe;

import java.time.LocalDate;

public record UpdateUserProfileRequest(
                Long id,
                String nom,
                String email,
                String adresse,
                String tel,
                Sexe sexe,
                LocalDate dateNaissance) {
}
