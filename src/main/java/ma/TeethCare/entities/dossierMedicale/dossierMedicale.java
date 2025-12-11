package ma.TeethCare.entities.dossierMedicale;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;

import ma.TeethCare.entities.patient.Patient;
import ma.TeethCare.entities.situationFinanciere.situationFinanciere;
import ma.TeethCare.entities.rdv.rdv;
import ma.TeethCare.entities.consultation.consultation;
import ma.TeethCare.entities.ordonnance.ordonnance;
import ma.TeethCare.entities.certificat.certificat;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class dossierMedicale extends baseEntity {
    private Long idDM;
    private Long patientId;
    private LocalDateTime dateDeCreation;
    
    private Patient patient;
    private situationFinanciere situationFinanciere;
    
    private List<rdv> rdvs;
    private List<consultation> consultations;
    private List<ordonnance> ordonnances;
    private List<certificat> certificats;
}
