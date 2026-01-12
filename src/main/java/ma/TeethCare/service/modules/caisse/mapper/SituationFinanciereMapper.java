package ma.TeethCare.service.modules.caisse.mapper;

import ma.TeethCare.entities.situationFinanciere.situationFinanciere;
import ma.TeethCare.mvc.dto.situationFinanciere.SituationFinanciereDTO;
import ma.TeethCare.common.enums.Statut;
import ma.TeethCare.common.enums.Promo;

public class SituationFinanciereMapper {

    public static SituationFinanciereDTO toDto(situationFinanciere entity) {
        if (entity == null)
            return null;
        return SituationFinanciereDTO.builder()
                .id(entity.getId())
                .totalDesActes(entity.getTotalDesActes())
                .totalPaye(entity.getTotalPaye())
                .credit(entity.getCredit())
                .statut(entity.getStatut() != null ? entity.getStatut().name() : null)
                .enPromo(entity.getEnPromo() != null ? entity.getEnPromo().name() : null)
                .dossierMedicaleId(entity.getDossierMedicaleId())
                .build();
    }

    public static situationFinanciere toEntity(SituationFinanciereDTO dto) {
        if (dto == null)
            return null;
        situationFinanciere.situationFinanciereBuilder<?, ?> builder = situationFinanciere.builder()
                .id(dto.getId())
                .totalDesActes(dto.getTotalDesActes())
                .totalPaye(dto.getTotalPaye())
                .credit(dto.getCredit())
                .dossierMedicaleId(dto.getDossierMedicaleId());

        if (dto.getStatut() != null) {
            try {
                builder.statut(Statut.valueOf(dto.getStatut()));
            } catch (Exception e) {}
        }
        
        if (dto.getEnPromo() != null) {
            try {
                builder.enPromo(Promo.valueOf(dto.getEnPromo()));
            } catch (Exception e) {}
        } else {
             builder.enPromo(Promo.Aucune);
        }

        return builder.build();
    }
}
