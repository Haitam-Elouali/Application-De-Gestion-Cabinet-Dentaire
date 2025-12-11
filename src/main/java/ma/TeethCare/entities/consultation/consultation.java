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
    private Long idConsultation;
    private Long rdvId;
    private Long patientId;
    private Long medecinId;
    private LocalDate date;
    private Statut statut;
    private String observationMedecin;
    private String diagnostique;
    
    // Relations
    private rdv rdv;
    private medecin medecin;
    private ordonnance ordonnance;
    
    private List<interventionMedecin> interventions;
    private List<certificat> certificats;
    private List<facture> factures;
}
