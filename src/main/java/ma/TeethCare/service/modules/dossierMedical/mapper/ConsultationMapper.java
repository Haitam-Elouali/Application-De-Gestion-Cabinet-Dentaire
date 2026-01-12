package ma.TeethCare.service.modules.dossierMedical.mapper;

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
        
        return entity;
    }
}
