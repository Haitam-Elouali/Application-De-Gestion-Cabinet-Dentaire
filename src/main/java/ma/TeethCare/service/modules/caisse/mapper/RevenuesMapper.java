package ma.TeethCare.service.modules.caisse.mapper;

import ma.TeethCare.entities.revenues.revenues;
import ma.TeethCare.mvc.dto.revenues.RevenuesDTO;

public class RevenuesMapper {

    public static RevenuesDTO toDto(revenues entity) {
        if (entity == null)
            return null;
        return RevenuesDTO.builder()
                .id(entity.getId())
                .cabinetId(entity.getCabinetId())
                .titre(entity.getTitre())
                .description(entity.getDescription())
                .montant(entity.getMontant())
                .categorie(entity.getCategorie())
                .date(entity.getDate())
                .build();
    }

    public static revenues toEntity(RevenuesDTO dto) {
        if (dto == null)
            return null;
        return revenues.builder()
                .id(dto.getId())
                .cabinetId(dto.getCabinetId())
                .titre(dto.getTitre())
                .description(dto.getDescription())
                .montant(dto.getMontant())
                .categorie(dto.getCategorie())
                .date(dto.getDate())
                .build();
    }
}
