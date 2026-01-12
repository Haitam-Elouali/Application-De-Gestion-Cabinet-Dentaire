package ma.TeethCare.service.modules.dossierMedical.mapper;

import ma.TeethCare.entities.actes.actes;
import ma.TeethCare.mvc.dto.actes.ActesDTO;

public class ActesMapper {

    public static ActesDTO toDTO(actes entity) {
        if (entity == null) return null;
        
        return ActesDTO.builder()
                .id(entity.getId())
                .nom(entity.getNom())
                .categorie(entity.getCategorie())
                .description(entity.getDescription())
                .prix(entity.getPrix())
                .code(entity.getCode())
                .interventionId(entity.getInterventionId())
                // .dateActe() // Entity doesn't seem to have dateActe directly in previous view, maybe check baseEntity or assume missing?
                // Checking entity view: fields are id, nom, categorie, description, prix, code, interventionId. No dateActe. 
                // baseEntity fields? usually idEntite, dateCreation.
                .dateCreation(entity.getDateCreation() != null ? entity.getDateCreation().atStartOfDay() : null)
                .build();
    }

    public static actes toEntity(ActesDTO dto) {
        if (dto == null) return null;

        actes entity = new actes();
        entity.setId(dto.getId()); // or setIdEntite if baseEntity
        entity.setNom(dto.getNom());
        entity.setCategorie(dto.getCategorie());
        entity.setDescription(dto.getDescription());
        entity.setPrix(dto.getPrix());
        entity.setCode(dto.getCode());
        entity.setInterventionId(dto.getInterventionId());
        
        return entity;
    }
}
