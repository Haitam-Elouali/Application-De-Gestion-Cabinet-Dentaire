package ma.TeethCare.service.modules.caisse.dto;

import java.time.LocalDate;

/**
 * Record representing a Caisse transaction (encaissement).
 * Based on ma.TeethCare.entities.caisse.caisse
 */
public record CaisseDto(
        Long idCaisse,
        Long factureId,
        Double montant,
        LocalDate dateEncaissement,
        String modeEncaissement,
        String reference) {
}
