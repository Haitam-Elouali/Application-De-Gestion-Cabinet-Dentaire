package ma.TeethCare.service.modules.caisse.dto;

import ma.TeethCare.common.enums.Promo;
import ma.TeethCare.common.enums.Statut;

/**
 * Record representing SituationFinanciere.
 * Based on ma.TeethCare.entities.situationFinanciere.situationFinanciere
 */
public record SituationFinanciereDto(
        Long id,
        Double totalDesActes,
        Double totalPaye,
        Double credit,
        Statut statut,
        Promo enPromo,
        Long dossierMedicaleId) {
}
