package ma.TeethCare.service.modules.caisse.mapper;

import ma.TeethCare.entities.charges.charges;
import ma.TeethCare.service.modules.caisse.dto.ChargesDto;

public class ChargesMapper {

    public static ChargesDto toDto(charges entity) {
        if (entity == null)
            return null;
        return new ChargesDto(
                entity.getId(),
                entity.getTitre(),
                entity.getDescription(),
                entity.getMontant(),
                entity.getCategorie(),
                entity.getDate(),
                entity.getCabinetId());
    }

    public static charges toEntity(ChargesDto dto) {
        if (dto == null)
            return null;
        return charges.builder()
                .id(dto.id())
                .titre(dto.titre())
                .description(dto.description())
                .montant(dto.montant())
                .categorie(dto.categorie())
                .date(dto.date())
                .cabinetId(dto.cabinetId())
                .build();
    }
}
