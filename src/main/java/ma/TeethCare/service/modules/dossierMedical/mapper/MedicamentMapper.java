package ma.TeethCare.service.modules.dossierMedical.mapper;

import ma.TeethCare.entities.medicaments.medicaments;
import ma.TeethCare.mvc.dto.medicament.MedicamentDTO;

public class MedicamentMapper {

    public static MedicamentDTO toDTO(medicaments entity) {
        if (entity == null) return null;
        
        return MedicamentDTO.builder()
                .id(entity.getId())
                .nom(entity.getNomCommercial())
                .description(entity.getDescription())
                .composition(entity.getPrincipeActif()) // Mapping composition -> principeActif
                .prix(entity.getPrixUnitaire()) // Mapping prix -> prixUnitaire
                .dosage(entity.getDosage())
                // .quantiteStock() // Not in entity
                .dateCreation(entity.getDateCreation() != null ? entity.getDateCreation().atStartOfDay() : null)
                .build();
    }

    public static medicaments toEntity(MedicamentDTO dto) {
        if (dto == null) return null;

        medicaments entity = new medicaments();
        entity.setId(dto.getId());
        entity.setNomCommercial(dto.getNom());
        entity.setDescription(dto.getDescription());
        entity.setPrincipeActif(dto.getComposition());
        entity.setPrixUnitaire(dto.getPrix());
        entity.setDosage(dto.getDosage());
        // quantiteStock ignored
        
        return entity;
    }
}
