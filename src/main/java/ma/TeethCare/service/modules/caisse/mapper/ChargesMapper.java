package ma.TeethCare.service.modules.caisse.mapper;

import ma.TeethCare.entities.charges.charges;
import ma.TeethCare.mvc.dto.charges.ChargesDTO;

public class ChargesMapper {

    public static ChargesDTO toDTO(charges entity) {
        if (entity == null) return null;
        
        return ChargesDTO.builder()
                .id(entity.getId())
                .titre(entity.getTitre())
                .description(entity.getDescription())
                .montant(entity.getMontant())
                .categorie(entity.getCategorie())
                .date(entity.getDate()) // Use generic LocalDateTime
                .cabinetId(entity.getCabinetId())
                .dateCreation(entity.getDateCreation() != null ? entity.getDateCreation().atStartOfDay() : null)
                .build();
    }

    public static charges toEntity(ChargesDTO dto) {
        if (dto == null) return null;

        charges entity = new charges();
        entity.setId(dto.getId());
        entity.setTitre(dto.getTitre());
        entity.setDescription(dto.getDescription());
        entity.setMontant(dto.getMontant());
        entity.setCategorie(dto.getCategorie());
        entity.setDate(dto.getDate());
        entity.setCabinetId(dto.getCabinetId());
        
        return entity;
    }
}
