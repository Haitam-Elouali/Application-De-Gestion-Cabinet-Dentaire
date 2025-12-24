package ma.TeethCare.service.modules.dashboard_statistiques.mapper;

import ma.TeethCare.entities.statistique.statistique;
import ma.TeethCare.service.modules.dashboard_statistiques.dto.StatistiqueDto;

public class StatistiqueMapper {

    public static StatistiqueDto toDto(statistique entity) {
        if (entity == null) {
            return null;
        }
        return new StatistiqueDto(
                entity.getId(),
                entity.getNom(),
                entity.getChiffre(),
                entity.getType(),
                entity.getDateCalcul(),
                entity.getCabinetId());
    }

    public static statistique toEntity(StatistiqueDto dto) {
        if (dto == null) {
            return null;
        }
        return statistique.builder()
                .id(dto.id())
                .nom(dto.nom())
                .chiffre(dto.chiffre())
                .type(dto.type())
                .dateCalcul(dto.dateCalcul())
                .cabinetId(dto.cabinetId())
                .build();
    }
}
