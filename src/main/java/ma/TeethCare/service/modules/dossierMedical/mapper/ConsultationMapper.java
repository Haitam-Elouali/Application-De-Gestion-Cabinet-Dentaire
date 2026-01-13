package ma.TeethCare.service.modules.dossierMedical.mapper;

import ma.TeethCare.common.enums.Statut;
import ma.TeethCare.entities.consultation.consultation;
import ma.TeethCare.mvc.dto.consultation.ConsultationDTO;

public class ConsultationMapper {

    public static ConsultationDTO toDTO(consultation entity) {
        if (entity == null) return null;
        
        return ConsultationDTO.builder()
                .id(entity.getId())
                .patientId(entity.getPatientId())
                .medecinId(entity.getMedecinId())
                .date(entity.getDate())
                .diagnostique(entity.getDiagnostic())
                .notes(entity.getObservation())
                .dateCreation(entity.getDateCreation() != null ? entity.getDateCreation().atStartOfDay() : null)
                .dateDerniereModification(entity.getDateDerniereModification())
                .statut(entity.getStatut() != null ? entity.getStatut().name() : null)
                .build();
    }

    public static consultation toEntity(ConsultationDTO dto) {
        if (dto == null) return null;

        consultation entity = new consultation();
        entity.setIdEntite(dto.getId());
        entity.setId(dto.getId());
        
        entity.setPatientId(dto.getPatientId());
        entity.setMedecinId(dto.getMedecinId());
        
        entity.setDate(dto.getDate());
        entity.setDiagnostic(dto.getDiagnostique());
        entity.setObservation(dto.getNotes());
        if (dto.getStatut() != null) {
            try {
                entity.setStatut(ma.TeethCare.common.enums.Statut.valueOf(dto.getStatut()));
            } catch (IllegalArgumentException e) {
                // Handle invalid enum
                entity.setStatut(Statut.Planifiee);
            }
        }
        
        return entity;
    }
}
