package ma.TeethCare.service.modules.users.mapper;

import ma.TeethCare.entities.utilisateur.utilisateur;
import ma.TeethCare.mvc.dto.utilisateur.UtilisateurDTO;

public class UtilisateurMapper {

    public static UtilisateurDTO toDTO(utilisateur entity) {
        if (entity == null) return null;
        
        return UtilisateurDTO.builder()
                .idUser(entity.getId() != null ? entity.getId() : (entity.getIdEntite()))
                .nom(entity.getNom())
                .prenom(entity.getPrenom())
                .email(entity.getEmail())
                .login(entity.getUsername())
                .motDePasse(entity.getPassword())
                // .roleId() // Entity usually has List<Role> or similar, difficult to map single ID without context.
                // .statut()
                .dateCreation(entity.getDateCreation() != null ? entity.getDateCreation().atStartOfDay() : null)
                .build();
    }

    public static utilisateur toEntity(UtilisateurDTO dto) {
        if (dto == null) return null;

        utilisateur entity = new utilisateur();
        if (dto.getIdUser() != null) {
            entity.setIdEntite(dto.getIdUser());
            entity.setId(dto.getIdUser());
        }

        entity.setNom(dto.getNom());
        entity.setPrenom(dto.getPrenom());
        entity.setEmail(dto.getEmail());
        entity.setUsername(dto.getLogin());
        entity.setPassword(dto.getMotDePasse());
        
        return entity;
    }
}
