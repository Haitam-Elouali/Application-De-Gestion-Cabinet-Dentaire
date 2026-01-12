package ma.TeethCare.service.modules.dossierMedical.mapper;

import ma.TeethCare.entities.interventionMedecin.interventionMedecin;
import ma.TeethCare.mvc.dto.interventionMedecin.InterventionMedecinDTO;

public class InterventionMedecinMapper {

    public static InterventionMedecinDTO toDTO(interventionMedecin entity) {
        if (entity == null) return null;
        
        return InterventionMedecinDTO.builder()
                .id(entity.getId())
                .consultationId(entity.getConsultationId())
                .duree(entity.getDuree())
                .note(entity.getNote())
                .resultatImagerie(entity.getResultatImagerie())
                .dateCreation(entity.getDateCreation() != null ? entity.getDateCreation().atStartOfDay() : null)
                .build();
    }

    public static interventionMedecin toEntity(InterventionMedecinDTO dto) {
        if (dto == null) return null;

        interventionMedecin entity = new interventionMedecin();
        entity.setId(dto.getId());
        entity.setConsultationId(dto.getConsultationId());
        entity.setDuree(dto.getDuree() != null ? dto.getDuree() : 0);
        entity.setNote(dto.getNote());
        entity.setResultatImagerie(dto.getResultatImagerie());
        
        return entity;
    }
}
