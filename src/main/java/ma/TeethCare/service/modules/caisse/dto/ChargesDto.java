package ma.TeethCare.service.modules.caisse.dto;

import java.time.LocalDateTime;

/**
 * Record representing Charges.
 * Based on ma.TeethCare.entities.charges.charges
 */
public record ChargesDto(
        Long id,
        String titre,
        String description,
        Double montant,
        String categorie,
        LocalDateTime date,
        Long cabinetId) {
}
