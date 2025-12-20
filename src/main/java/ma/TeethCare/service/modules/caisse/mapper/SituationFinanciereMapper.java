package ma.TeethCare.service.modules.caisse.mapper;

import ma.TeethCare.entities.situationFinanciere.situationFinanciere;
import ma.TeethCare.service.modules.caisse.dto.SituationFinanciereDto;

public class SituationFinanciereMapper {

    public static SituationFinanciereDto toDto(situationFinanciere entity) {
        if (entity == null)
            return null;
        return new SituationFinanciereDto(
                entity.getId(),
                entity.getTotalDesActes(),
                entity.getTotalPaye(),
                entity.getCredit(),
                entity.getStatut(),
                entity.getEnPromo(),
                entity.getDossierMedicaleId());
    }

    public static situationFinanciere toEntity(SituationFinanciereDto dto) {
        if (dto == null)
            return null;
        return situationFinanciere.builder()
                .id(dto.id())
                .totalDesActes(dto.totalDesActes())
                .totalPaye(dto.totalPaye())
                .credit(dto.credit())
                .statut(dto.statut())
                .enPromo(dto.enPromo())
                .dossierMedicaleId(dto.dossierMedicaleId())
                .build();
    }
}
