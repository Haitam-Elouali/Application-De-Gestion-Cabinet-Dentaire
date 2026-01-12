package ma.TeethCare.service.modules.dossierMedical.mapper;

import ma.TeethCare.entities.ordonnance.ordonnance;
import ma.TeethCare.mvc.dto.ordonnance.OrdonnanceDTO;

public class OrdonnanceMapper {

    public static OrdonnanceDTO toDTO(ordonnance entity) {
        if (entity == null) return null;
        
        return OrdonnanceDTO.builder()
                .idOrd(entity.getId())
                .consultationId(entity.getConsultationId())
                .date(entity.getDateOrdonnance()) // mapped to dateOrdonnance
                .dateCreation(entity.getDateCreation() != null ? entity.getDateCreation().atStartOfDay() : null)
                .dateDerniereModification(entity.getDateDerniereModification())
                .build();
    }

    public static ordonnance toEntity(OrdonnanceDTO dto) {
        if (dto == null) return null;

        ordonnance entity = new ordonnance();
        entity.setIdEntite(dto.getIdOrd());
        entity.setId(dto.getIdOrd());
        
        entity.setConsultationId(dto.getConsultationId());
        entity.setDateOrdonnance(dto.getDate());
        
        return entity;
    }
}
