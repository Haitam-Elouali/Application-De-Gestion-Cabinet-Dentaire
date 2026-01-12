package ma.TeethCare.service.modules.users.mapper;

import ma.TeethCare.entities.medecin.medecin;
import ma.TeethCare.mvc.dto.medecin.MedecinDTO;

public class MedecinMapper {

    public static MedecinDTO toDTO(medecin entity) {
        if (entity == null) return null;
        
        return MedecinDTO.builder()
                .id(entity.getId())
                .nom(entity.getNom())
                .prenom(entity.getPrenom())
                .email(entity.getEmail())
                .telephone(entity.getTelephone())
                .specialite(entity.getSpecialite())
                // .numeroLicence() // Not in entity
                .dateCreation(entity.getDateCreation() != null ? entity.getDateCreation().atStartOfDay() : null)
                .build();
    }

    public static medecin toEntity(MedecinDTO dto) {
        if (dto == null) return null;

        medecin entity = new medecin();
        entity.setIdEntite(dto.getId());
        entity.setId(dto.getId()); // or setIdMedecin if applicable, but standard is getId
        
        entity.setNom(dto.getNom());
        entity.setPrenom(dto.getPrenom());
        entity.setEmail(dto.getEmail());
        entity.setTelephone(dto.getTelephone());
        entity.setSpecialite(dto.getSpecialite());
        
        // Inherited fields like username/password might need default or separate handling if not in DTO?
        // Medecin creation usually involves password. DTO doesn't have it.
        // Assuming update scenario mainly, or creation handled with specific Request object separately in Controller?
        // But Service.create() typically takes DTO.
        // If DTO lacks password, creation might fail or set null.
        // For now, mapping what we have.
        
        return entity;
    }
}
