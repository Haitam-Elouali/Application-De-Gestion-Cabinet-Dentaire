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
    private Long idCertif;
    private Long consultationId;
    private Long medecinId;
    private Long patientId;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private int duree;
    private String noteMedecin;
    
    private consultation consultation;
    private medecin medecin;
    private Patient patient;
}
