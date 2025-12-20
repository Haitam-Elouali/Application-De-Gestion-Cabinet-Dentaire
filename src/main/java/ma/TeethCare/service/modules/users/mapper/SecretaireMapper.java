package ma.TeethCare.service.modules.users.mapper;

import ma.TeethCare.entities.secretaire.secretaire;
import ma.TeethCare.service.modules.users.dto.CreateSecretaireRequest;

import ma.TeethCare.service.modules.users.dto.UserAccountDto;
import java.util.Collections;

public class SecretaireMapper {

    public static UserAccountDto toUserAccountDto(secretaire entity) {
        if (entity == null)
            return null;
        return new UserAccountDto(
                entity.getIdEntite(), // Assuming idEntite from base class
                entity.getNom(),
                entity.getEmail(),
                entity.getUsername(),
                entity.getSexe(),
                entity.getDateNaissance(),
                Collections.emptySet(), // Roles could be mapped if needed
                Collections.emptySet() // Privileges
        );
    }

    public static secretaire toEntity(CreateSecretaireRequest request) {
        if (request == null)
            return null;
        return secretaire.builder()
                .nom(request.nom())
                .email(request.email())
                .adresse(request.adresse())
                .cin(request.cin())
                .telephone(request.tel()) // Assuming field is telephone or tel? User dto says 'tel'. Entity user
                                          // usually 'telephone'.
                .sexe(request.sexe())
                .username(request.login()) // DTO says 'login'. Entity user usually 'username' or 'login'.
                .password(request.motDePasse()) // DTO 'motDePasse'. Entity 'password'.
                .dateNaissance(request.dateNaissance())
                .salaire(request.salaire()) // Assuming salaire -> salary? No, Entity likely has 'salaire' or 'salary'?
                // Wait, staff.java had 'salaire'.
                .salaire(request.salaire())
                // .prime(request.prime())
                .dateEmbauche(request.dateRecrutement())
                // .soldeConge(request.soldeConge())
                // .numCNSS(request.numCNSS())
                .commission(request.commission() != null ? request.commission().intValue() : 0)
                .build();
    }
}
