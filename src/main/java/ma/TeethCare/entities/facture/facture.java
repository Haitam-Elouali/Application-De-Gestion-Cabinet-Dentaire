package ma.TeethCare.entities.facture;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.common.enums.Statut;
import ma.TeethCare.entities.consultation.consultation;
import ma.TeethCare.entities.situationFinanciere.situationFinanciere;
import ma.TeethCare.entities.revenues.revenues;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class facture extends baseEntity {
    private Long id; // Was idFacture
    private Long consultationId;
    private Long patientId;
    private Long secretaireId; // New
    private Double totaleFacture;
    private Double totalePaye; // Was totalPaye
    private Double reste;
    private Statut statut;
    private String modePaiement; // New
    private LocalDateTime dateFacture;
    
    private consultation consultation;
    private situationFinanciere situationFinanciere;
    private List<revenues> revenues;
    
    // Transient field for display purposes (populated via repository joins)
    private ma.TeethCare.entities.patient.Patient patient;

    public static facture createTestInstance(ma.TeethCare.entities.patient.Patient patient, ma.TeethCare.entities.consultation.consultation consultation) {
        return facture.builder()
                .dateFacture(LocalDateTime.now())
                .totaleFacture(150.0)
                .totalePaye(0.0) 
                .reste(150.0)
                .patientId(patient.getIdEntite())
                .consultation(consultation)
                .statut(Statut.En_attente)
                .modePaiement("Espece")
                .build();
    }
}
