package ma.TeethCare.entities.certificat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.entities.consultation.consultation;
import ma.TeethCare.entities.medecin.medecin;
import ma.TeethCare.entities.patient.Patient;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class certificat extends baseEntity {
    private Long id; // Was idCertif
    private String type; // New
    private LocalDate dateDebut;
    private LocalDate dateFin; // New
    private int duree;
    private String note; // Was noteMedecin
    private Long consultationId; // Schema link
    
    private consultation consultation; // Changed from patient/medecin to consultation
    
    public static certificat createTestInstance(ma.TeethCare.entities.consultation.consultation consultation) {
        return certificat.builder()
                .type("Maladie")
                .dateDebut(LocalDate.now())
                .dateFin(LocalDate.now().plusDays(3))
                .duree(3)
                .note("Repose required")
                .consultation(consultation)
                .build();
    }
}
