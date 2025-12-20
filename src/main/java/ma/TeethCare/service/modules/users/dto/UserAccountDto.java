package ma.TeethCare.service.modules.users.dto;

import ma.TeethCare.common.enums.RoleType;
import ma.TeethCare.common.enums.Sexe;

import java.time.LocalDate;
import java.util.Set;

public record UserAccountDto(
                Long id,
                String nom,
                String email,
                String login,
                Sexe sexe,
                LocalDate dateNaissance,
                Set<RoleType> roles,
                Set<String> privileges) {
}
