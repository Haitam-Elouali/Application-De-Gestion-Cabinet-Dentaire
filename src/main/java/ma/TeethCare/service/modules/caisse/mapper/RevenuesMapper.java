package ma.TeethCare.service.modules.caisse.mapper;

import ma.TeethCare.entities.revenues.revenues;
import ma.TeethCare.service.modules.caisse.dto.RevenuesDto;

public class RevenuesMapper {

    public static RevenuesDto toDto(revenues entity) {
        if (entity == null)
            return null;
        return new RevenuesDto(
                entity.getId(),
                entity.getCabinetId(),
                entity.getTitre(),
                entity.getDescription(),
                entity.getMontant(),
                entity.getCategorie(),
                entity.getDate());
    }

    public static revenues toEntity(RevenuesDto dto) {
        if (dto == null)
            return null;
        return revenues.builder()
                .id(dto.id())
                .cabinetId(dto.cabinetId())
                .titre(dto.titre())
                .description(dto.description())
                .montant(dto.montant())
                .categorie(dto.categorie())
                .date(dto.date())
                .build();
    }
}
