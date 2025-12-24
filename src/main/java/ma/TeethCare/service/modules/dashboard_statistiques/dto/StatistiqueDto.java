package ma.TeethCare.service.modules.dashboard_statistiques.dto;

import java.time.LocalDate;

public record StatistiqueDto(
        Long id,
        String nom,
        Double chiffre,
        String type,
        LocalDate dateCalcul,
        Long cabinetId) {
}
