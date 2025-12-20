package ma.TeethCare.service.modules.caisse.dto;

import java.time.LocalDateTime;

/**
 * Record representing Revenues.
 * Based on ma.TeethCare.entities.revenues.revenues
 */
public record RevenuesDto(
        Long id,
        Long cabinetId,
        String titre,
        String description,
        Double montant,
        String categorie,
        LocalDateTime date) {
}
