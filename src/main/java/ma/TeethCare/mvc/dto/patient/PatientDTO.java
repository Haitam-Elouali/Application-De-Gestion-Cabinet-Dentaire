package ma.TeethCare.mvc.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.mvc.dto.antecedent.AntecedentDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

/**
 * Data Transfer Object pour Patient
 * Utilisé pour les échanges HTTP (request/response)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDTO {
    private Long id; 
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String adresse;
    private String sexe;
    private String cin;
    private String assurance;
    private LocalDate dateNaissance; // Changed from java.time.LocalDate to LocalDate and added import
    private LocalDateTime dateCreation;
    private LocalDateTime dateDerniereModification;
    
    @Builder.Default
    private List<AntecedentDTO> antecedents = new ArrayList<>();
}
