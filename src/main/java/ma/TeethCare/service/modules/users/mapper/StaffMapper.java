package ma.TeethCare.service.modules.users.mapper;

import ma.TeethCare.entities.staff.staff;
import ma.TeethCare.mvc.dto.staff.StaffDTO;

public class StaffMapper {

    public static StaffDTO toDTO(staff entity) {
        if (entity == null) return null;
        
        return StaffDTO.builder()
                .id(entity.getId() != null ? entity.getId() : entity.getIdEntite())
                .nom(entity.getNom())
                .prenom(entity.getPrenom())
                .email(entity.getEmail())
                .telephone(entity.getTelephone())
                .dateEmbauche(entity.getDateEmbauche())
                .cin(entity.getCin())
                // .statut() // Mapping assumes status might not be in base staff entity directly or needs interpretation
                .dateCreation(entity.getDateCreation() != null ? entity.getDateCreation().atStartOfDay() : null)
                .build();
    }

    public static staff toEntity(StaffDTO dto) {
        if (dto == null) return null;

        staff entity = new staff();
        if (dto.getId() != null) {
            entity.setIdEntite(dto.getId());
            entity.setId(dto.getId());
        }

        entity.setNom(dto.getNom());
        entity.setPrenom(dto.getPrenom());
        entity.setEmail(dto.getEmail());
        entity.setTelephone(dto.getTelephone());
        entity.setDateEmbauche(dto.getDateEmbauche());
        entity.setCin(dto.getCin());
        
        return entity;
    }
}
