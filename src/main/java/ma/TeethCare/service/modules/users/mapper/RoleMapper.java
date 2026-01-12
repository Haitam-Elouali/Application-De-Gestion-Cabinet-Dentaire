package ma.TeethCare.service.modules.users.mapper;

import ma.TeethCare.entities.role.role;
import ma.TeethCare.mvc.dto.role.RoleDTO;

public class RoleMapper {

    public static RoleDTO toDTO(role entity) {
        if (entity == null) return null;
        
        return RoleDTO.builder()
                .id(entity.getId())
                .nom(entity.getLibelle())
                // .description() // Not in entity
                // .permissions() // Not in entity
                .dateCreation(entity.getDateCreation() != null ? entity.getDateCreation().atStartOfDay() : null)
                .build();
    }

    public static role toEntity(RoleDTO dto) {
        if (dto == null) return null;

        role entity = new role();
        entity.setId(dto.getId());
        entity.setLibelle(dto.getNom());
        
        return entity;
    }
}
