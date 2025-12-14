package ma.TeethCare.entities.consultation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.common.enums.Statut;
import ma.TeethCare.entities.rdv.rdv;
import ma.TeethCare.entities.medecin.medecin;
import ma.TeethCare.entities.ordonnance.ordonnance;
import ma.TeethCare.entities.interventionMedecin.interventionMedecin;
import ma.TeethCare.entities.certificat.certificat;
import ma.TeethCare.entities.facture.facture;
import java.util.List;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class consultation extends baseEntity {
    private Long id; // Was idConsultation
    // Removed rdvId not in schema
    private Long patientId;
    private Long medecinId;
    private LocalDate date;
    private String motif; // Add motif
    private String diagnostic; // Was diagnostique
    private String observation; // Was observationMedecin
    private Statut statut;
    
    // Relations
    private rdv rdv; // Should likely remove or keep as transient? Schema does not have FK. I'll leave object link for now but fields match schema.
    private medecin medecin;
    private ordonnance ordonnance;
    
    private List<interventionMedecin> interventions;
    private List<certificat> certificats;
    private List<facture> factures;
    public static consultation createTestInstance(ma.TeethCare.entities.patient.Patient patient, ma.TeethCare.entities.medecin.medecin medecin) {
        return consultation.builder()
                .date(LocalDate.now())
                .motif("Douleur dentaire")
                .medecin(medecin)
                .patientId(patient.getIdEntite())
                .statut(Statut.En_attente)
                .build();
    }
}
