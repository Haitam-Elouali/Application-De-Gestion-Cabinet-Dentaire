package ma.TeethCare.service.modules.dossierMedical.mapper;

import ma.TeethCare.entities.prescription.prescription;
import ma.TeethCare.mvc.dto.prescription.PrescriptionDTO;

public class PrescriptionMapper {

    public static PrescriptionDTO toDTO(prescription entity) {
        if (entity == null) return null;
        
        return PrescriptionDTO.builder()
                .id(entity.getId())
                .ordonnanceId(entity.getOrdonnanceId())
                .medicamentId(entity.getMedicamentId())
                .duree(entity.getDureeEnJours()) // Int to Integer
                .instructions(entity.getPosologie()) // Map posologie to instructions
                // .dosage() // Not present in entity
                // .frequence() // Not present in entity
                .dateCreation(entity.getDateCreation() != null ? entity.getDateCreation().atStartOfDay() : null)
                .build();
    }

    public static prescription toEntity(PrescriptionDTO dto) {
        if (dto == null) return null;

        prescription entity = new prescription();
        entity.setIdEntite(dto.getId());
        entity.setId(dto.getId());
        
        entity.setOrdonnanceId(dto.getOrdonnanceId());
        entity.setMedicamentId(dto.getMedicamentId());
        
        if (dto.getDuree() != null) {
            entity.setDureeEnJours(dto.getDuree());
        }
        
        entity.setPosologie(dto.getInstructions());
        // entity.setQuantite(1); // Default? Or handle in service if needed.
        
        return entity;
    }
}
