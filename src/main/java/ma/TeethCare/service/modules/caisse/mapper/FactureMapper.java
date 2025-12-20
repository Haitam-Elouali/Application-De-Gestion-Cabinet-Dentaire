package ma.TeethCare.service.modules.caisse.mapper;

import ma.TeethCare.entities.facture.facture;
import ma.TeethCare.service.modules.caisse.dto.FactureDto;

public class FactureMapper {

    public static FactureDto toDto(facture entity) {
        if (entity == null)
            return null;
        return new FactureDto(
                entity.getId(),
                entity.getConsultationId(),
                entity.getPatientId(),
                entity.getSecretaireId(),
                entity.getTotaleFacture(),
                entity.getTotalePaye(),
                entity.getReste(),
                entity.getStatut(),
                entity.getModePaiement(),
                entity.getDateFacture());
    }

    public static facture toEntity(FactureDto dto) {
        if (dto == null)
            return null;
        return facture.builder()
                .id(dto.id())
                .consultationId(dto.consultationId())
                .patientId(dto.patientId())
                .secretaireId(dto.secretaireId())
                .totaleFacture(dto.totaleFacture())
                .totalePaye(dto.totalePaye())
                .reste(dto.reste())
                .statut(dto.statut())
                .modePaiement(dto.modePaiement())
                .dateFacture(dto.dateFacture())
                .build();
    }
}
