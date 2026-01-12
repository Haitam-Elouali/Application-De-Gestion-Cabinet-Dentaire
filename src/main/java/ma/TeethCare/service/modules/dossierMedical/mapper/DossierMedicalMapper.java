package ma.TeethCare.service.modules.dossierMedical.mapper;

import ma.TeethCare.entities.dossierMedicale.dossierMedicale;
import ma.TeethCare.mvc.dto.dossierMedicale.DossierMedicaleDTO;

public class DossierMedicalMapper {

    public static DossierMedicaleDTO toDTO(dossierMedicale entity) {
        if (entity == null) return null;
        
        return DossierMedicaleDTO.builder()
                .id(entity.getId())
                .patientId(entity.getPatientId())
                // .numero(entity.getNumero()) // Field missing in entity, using id or null
                // .diagnostic(...) // This likely should come from Consultation aggregation
                // .historique(...) 
                .dateCreation(entity.getDateDeCreation()) // Entity uses dateDeCreation
                .dateDerniereModification(entity.getDateDerniereModification())
                .build();
    }

    public static dossierMedicale toEntity(DossierMedicaleDTO dto) {
        if (dto == null) return null;

        dossierMedicale entity = new dossierMedicale();
        entity.setIdEntite(dto.getId());
        entity.setId(dto.getId());
        entity.setPatientId(dto.getPatientId());
        entity.setDateDeCreation(dto.getDateCreation());
        // Other fields not Present in Base Entity or specific entity
        
        return entity;
    }
}
