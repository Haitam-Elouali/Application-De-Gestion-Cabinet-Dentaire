package ma.TeethCare.service.modules.dossierMedical.mapper;

import ma.TeethCare.entities.antecedent.antecedent;
import ma.TeethCare.mvc.dto.antecedent.AntecedentDTO;
import ma.TeethCare.common.enums.niveauDeRisque;

public class AntecedentMapper {

    public static AntecedentDTO toDTO(antecedent entity) {
        if (entity == null) return null;
        
        return AntecedentDTO.builder()
                .id(entity.getId())
                .dossierMedicaleId(entity.getDossierMedicaleId())
                .nom(entity.getNom())
                .categorie(entity.getCategorie())
                .niveauDeRisque(entity.getNiveauDeRisque() != null ? entity.getNiveauDeRisque().name() : null)
                .dateAntecedent(entity.getDateCreation()) // baseEntity.dateCreation is LocalDate
                .dateCreation(entity.getDateCreation() != null ? entity.getDateCreation().atStartOfDay() : null) // Convert LocalDate to LocalDateTime
                .build();
    }

    public static antecedent toEntity(AntecedentDTO dto) {
        if (dto == null) return null;

        antecedent entity = new antecedent();
        entity.setIdEntite(dto.getId());
        entity.setId(dto.getId());
        
        entity.setDossierMedicaleId(dto.getDossierMedicaleId());
        entity.setNom(dto.getNom());
        entity.setCategorie(dto.getCategorie());
        
        if (dto.getNiveauDeRisque() != null) {
            try {
                entity.setNiveauDeRisque(niveauDeRisque.valueOf(dto.getNiveauDeRisque()));
            } catch (Exception e) {}
        }
        
        return entity;
    }
}
