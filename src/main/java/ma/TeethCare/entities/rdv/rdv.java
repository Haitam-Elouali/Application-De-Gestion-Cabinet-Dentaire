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
@lombok.experimental.SuperBuilder
public class rdv extends baseEntity {
    private Long id; // Was idRDV
    private Long numero; // New
    private Long patientId;
    private Long situationfinancierId; // New
    private LocalDate date;
    private LocalTime heure;
    private Statut statut;
    private String motif;
    // Removed medecinId, noteMedecin
    
    private Patient patient;
    private ma.TeethCare.entities.situationFinanciere.situationFinanciere situationFinanciere;
    private consultation consultation;
    private dossierMedicale dossierMedicale;
    public static rdv createTestInstance(ma.TeethCare.entities.patient.Patient patient) {
        return rdv.builder()
                .numero(1L)
                .date(LocalDate.now().plusDays(1))
                .heure(LocalTime.of(10, 0))
                .patient(patient)
                .motif("consultation")
                .statut(Statut.Planifiee)
                .build();
    }
}
