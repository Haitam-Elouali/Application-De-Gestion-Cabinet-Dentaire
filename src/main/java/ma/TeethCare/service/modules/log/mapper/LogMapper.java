package ma.TeethCare.service.modules.log.mapper; 

import ma.TeethCare.entities.log.log;
import ma.TeethCare.mvc.dto.log.LogDTO;

public class LogMapper {

    public static LogDTO toDTO(log entity) {
        if (entity == null) return null;
        
        return LogDTO.builder()
                .id(entity.getId())
                .utilisateurId(entity.getUtilisateurId() != null ? String.valueOf(entity.getUtilisateurId()) : null)
                .action(entity.getTypeSupp()) // Approx mapping: type -> action
                .description(entity.getMessage()) 
                .module("SYSTEM") // or derive from somewhere?
                .dateLog(entity.getDateAction())
                .build();
    }

    public static log toEntity(LogDTO dto) {
        if (dto == null) return null;

        log entity = new log();
        entity.setIdEntite(dto.getId());
        entity.setId(dto.getId());
        
        if (dto.getUtilisateurId() != null) {
            try {
                entity.setUtilisateurId(Long.valueOf(dto.getUtilisateurId()));
            } catch (NumberFormatException e) {}
        }
        
        entity.setMessage(dto.getDescription());
        entity.setTypeSupp(dto.getAction()); // map back
        entity.setDateAction(dto.getDateLog());
        
        return entity;
    }
}
