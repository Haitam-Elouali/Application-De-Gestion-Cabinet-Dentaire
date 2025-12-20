package ma.TeethCare.service.modules.caisse.mapper;

import ma.TeethCare.entities.caisse.caisse;
import ma.TeethCare.service.modules.caisse.dto.CaisseDto;

public class CaisseMapper {

    public static CaisseDto toDto(caisse entity) {
        if (entity == null)
            return null;
        return new CaisseDto(
                entity.getIdCaisse(),
                entity.getFactureId(),
                entity.getMontant(),
                entity.getDateEncaissement(),
                entity.getModeEncaissement(),
                entity.getReference());
    }

    public static caisse toEntity(CaisseDto dto) {
        if (dto == null)
            return null;
        return caisse.builder()
                .idCaisse(dto.idCaisse())
                .factureId(dto.factureId())
                .montant(dto.montant())
                .dateEncaissement(dto.dateEncaissement())
                .modeEncaissement(dto.modeEncaissement())
                .reference(dto.reference())
                .build();
    }
}
