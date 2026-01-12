package ma.TeethCare.service.modules.agenda.mapper;

import ma.TeethCare.entities.rdv.rdv;
import ma.TeethCare.mvc.dto.rdv.RdvDTO;
import ma.TeethCare.common.enums.Statut;

public class RdvMapper {

    public static RdvDTO toDTO(rdv entity) {
        if (entity == null) return null;
        
        return RdvDTO.builder()
                .idRDV(entity.getId()) // Entity uses 'id' (inherited/overridden), DTO uses 'idRDV'
                .patientId(entity.getPatientId())
                // .medecinId(entity.getMedecinId()) // likely removed from entity, strict mapping implies ignored if null
                .date(entity.getDate())
                .heure(entity.getHeure())
                .motif(entity.getMotif())
                .statut(entity.getStatut() != null ? entity.getStatut().name() : null)
                .dateCreation(entity.getDateCreation() != null ? entity.getDateCreation().atStartOfDay() : null)
                .dateDerniereModification(entity.getDateDerniereModification())
                .build();
    }

    public static rdv toEntity(RdvDTO dto) {
        if (dto == null) return null;

        rdv entity = new rdv();
        entity.setIdEntite(dto.getIdRDV()); // BaseEntity ID
        entity.setId(dto.getIdRDV());       // Specific ID
        
        entity.setPatientId(dto.getPatientId());
        // entity.setMedecinId(dto.getMedecinId()); 
        
        entity.setDate(dto.getDate());
        entity.setHeure(dto.getHeure());
        entity.setMotif(dto.getMotif());
        
        if (dto.getStatut() != null) {
            try {
                entity.setStatut(Statut.valueOf(dto.getStatut()));
            } catch (IllegalArgumentException e) {
                // Ignore or set default
            }
        }
        
        return entity;
    }
}
