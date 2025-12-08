package ma.TeethCare.entities.rdv;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.common.enums.Statut;
import ma.TeethCare.entities.patient.Patient;
import ma.TeethCare.entities.medecin.medecin;
import ma.TeethCare.entities.consultation.consultation;
import ma.TeethCare.entities.dossierMedicale.dossierMedicale;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class rdv extends baseEntity {
    private Long idRDV;
    private Long patientId;
    private Long medecinId;
    private LocalDate date;
    private LocalTime heure;
    private String motif;
    private Statut statut;
    private String noteMedecin;
    
    private Patient patient;
    private medecin medecin;
    private consultation consultation;
    private dossierMedicale dossierMedicale;
}
