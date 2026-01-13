package ma.TeethCare.service.modules.dossierMedical.mapper;

import ma.TeethCare.entities.certificat.certificat;
import ma.TeethCare.mvc.dto.certificat.CertificatDTO;

public class CertificatMapper {

    public static CertificatDTO toDTO(certificat entity) {
        if (entity == null) return null;
        
        return CertificatDTO.builder()
                .id(entity.getId())
                .consultationId(entity.getConsultationId())
                .type(entity.getType())
                .dateEmission(entity.getDateDebut())
                .dateExpiration(entity.getDateFin())
                .duree(entity.getDuree())
                .motif(entity.getNote())
                .dateCreation(entity.getDateCreation() != null ? entity.getDateCreation().atStartOfDay() : null)
                .build();
    }

    public static certificat toEntity(CertificatDTO dto) {
        if (dto == null) return null;

        certificat entity = new certificat();
        entity.setIdEntite(dto.getId());
        entity.setId(dto.getId()); // ID
        
        entity.setConsultationId(dto.getConsultationId());
        entity.setType(dto.getType());
        entity.setDateDebut(dto.getDateEmission());
        entity.setDateFin(dto.getDateExpiration());
        entity.setDuree(dto.getDuree() != null ? dto.getDuree() : 0);
        entity.setNote(dto.getMotif());
        
        // Base entity fields if needed (dateCreation usually handled by DB default or set logic)
        
        return entity;
    }
}
