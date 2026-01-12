package ma.TeethCare.service.modules.patient.mapper;

import ma.TeethCare.entities.patient.Patient;
import ma.TeethCare.mvc.dto.patient.PatientDTO;
import ma.TeethCare.service.modules.dossierMedical.mapper.AntecedentMapper;
import ma.TeethCare.common.enums.Assurance;
import ma.TeethCare.common.enums.Sexe;
import java.util.stream.Collectors;
import java.util.Collections;

public class PatientMapper {

    public static PatientDTO toDTO(Patient entity) {
        if (entity == null) return null;
        
        return PatientDTO.builder()
                .id(entity.getId()) 
                .nom(entity.getNom())
                .prenom(entity.getPrenom())
                .email(entity.getEmail())
                .telephone(entity.getTelephone())
                .adresse(entity.getAdresse())
                .dateNaissance(entity.getDateNaissance())
                .sexe(entity.getSexe() != null ? entity.getSexe().name() : null)
                .assurance(entity.getAssurance() != null ? entity.getAssurance().name() : null)
                .dateCreation(entity.getDateCreation() != null ? entity.getDateCreation().atStartOfDay() : null)
                .dateDerniereModification(entity.getDateDerniereModification())
                .antecedents(entity.getAntecedents() != null ? 
                    entity.getAntecedents().stream().map(AntecedentMapper::toDTO).collect(Collectors.toList()) : 
                    Collections.emptyList())
                .build();
    }

    public static Patient toEntity(PatientDTO dto) {
        if (dto == null) return null;

        Patient entity = new Patient();
        entity.setIdEntite(dto.getId()); 
        entity.setId(dto.getId());
        
        entity.setNom(dto.getNom());
        entity.setPrenom(dto.getPrenom());
        entity.setEmail(dto.getEmail());
        entity.setTelephone(dto.getTelephone());
        entity.setAdresse(dto.getAdresse());
        entity.setDateNaissance(dto.getDateNaissance());
        
        if (dto.getSexe() != null) {
            try {
                entity.setSexe(Sexe.valueOf(dto.getSexe()));
            } catch (IllegalArgumentException e) {}
        }
        
        if (dto.getAssurance() != null) {
            try {
                entity.setAssurance(Assurance.valueOf(dto.getAssurance()));
            } catch (IllegalArgumentException e) {}
        }
        
        // Antecedents handled by Service/Repository relationships usually
        
        return entity;
    }
}
