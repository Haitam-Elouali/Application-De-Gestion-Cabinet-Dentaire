package ma.TeethCare.mvc.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Data Transfer Object pour Patient
 * Utilisé pour les échanges HTTP (request/response)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDTO {
    private Long idPatient;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String adresse;
    private String sexe;
    private String assurance;
    private LocalDateTime dateCreation;
    private LocalDateTime dateDerniereModification;
}
