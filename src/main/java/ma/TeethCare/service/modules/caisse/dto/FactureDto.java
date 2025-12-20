package ma.TeethCare.service.modules.caisse.dto;

import ma.TeethCare.common.enums.Statut;

import java.time.LocalDateTime;

/**
 * Record representing a Facture.
 * Based on ma.TeethCare.entities.facture.facture
 */
public record FactureDto(
        Long id,
        Long consultationId,
        Long patientId,
        Long secretaireId,
        Double totaleFacture,
        Double totalePaye,
        Double reste,
        Statut statut,
        String modePaiement,
        LocalDateTime dateFacture) {
}
