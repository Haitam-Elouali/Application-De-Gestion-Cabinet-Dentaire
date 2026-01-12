package ma.TeethCare.service.modules.caisse.mapper;

import ma.TeethCare.entities.facture.facture;
import ma.TeethCare.mvc.dto.facture.FactureDTO;
import ma.TeethCare.common.enums.Statut;
import java.math.BigDecimal;

public class FactureMapper {

    public static FactureDTO toDTO(facture entity) {
        if (entity == null) return null;
        
        return FactureDTO.builder()
                .id(entity.getId())
                .consultationId(entity.getConsultationId())
                .patientId(entity.getPatientId())
                .totalFacture(entity.getTotaleFacture() != null ? BigDecimal.valueOf(entity.getTotaleFacture()) : null)
                .totalPaye(entity.getTotalePaye() != null ? BigDecimal.valueOf(entity.getTotalePaye()) : null)
                .reste(entity.getReste() != null ? BigDecimal.valueOf(entity.getReste()) : null)
                .statut(entity.getStatut() != null ? entity.getStatut().name() : null)
                .dateCreation(entity.getDateFacture()) 
                .dateDerniereModification(entity.getDateDerniereModification())
                .build();
    }

    public static facture toEntity(FactureDTO dto) {
        if (dto == null) return null;

        facture entity = new facture();
        entity.setIdEntite(dto.getId());
        entity.setId(dto.getId());
        
        entity.setConsultationId(dto.getConsultationId());
        entity.setPatientId(dto.getPatientId());
        
        if (dto.getTotalFacture() != null) entity.setTotaleFacture(dto.getTotalFacture().doubleValue());
        if (dto.getTotalPaye() != null) entity.setTotalePaye(dto.getTotalPaye().doubleValue());
        if (dto.getReste() != null) entity.setReste(dto.getReste().doubleValue());
        
        if (dto.getStatut() != null) {
            try {
                entity.setStatut(Statut.valueOf(dto.getStatut()));
            } catch (Exception e) {}
        }
        
        if (dto.getDateCreation() != null) entity.setDateFacture(dto.getDateCreation());
        
        return entity;
    }
}
