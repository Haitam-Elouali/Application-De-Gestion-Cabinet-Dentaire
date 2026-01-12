package ma.TeethCare.service.modules.users.mapper;

import ma.TeethCare.entities.admin.admin;
import ma.TeethCare.mvc.dto.admin.AdminDTO;

public class AdminMapper {

    public static AdminDTO toDTO(admin entity) {
        if (entity == null) return null;
        
        return AdminDTO.builder()
                .id(entity.getId())
                .nom(entity.getNom())
                .prenom(entity.getPrenom())
                .email(entity.getEmail())
                .telephone(entity.getTelephone())
                .domaine(entity.getDomaine())
                .dateCreation(entity.getDateCreation() != null ? entity.getDateCreation().atStartOfDay() : null)
                .build();
    }

    public static admin toEntity(AdminDTO dto) {
        if (dto == null) return null;

        admin entity = new admin();
        entity.setIdEntite(dto.getId());
        entity.setId(dto.getId());

        entity.setNom(dto.getNom());
        entity.setPrenom(dto.getPrenom());
        entity.setEmail(dto.getEmail());
        entity.setTelephone(dto.getTelephone());
        entity.setDomaine(dto.getDomaine());
        
        return entity;
    }
}
