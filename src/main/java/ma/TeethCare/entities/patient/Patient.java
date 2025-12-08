package ma.TeethCare.entities.patient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.common.enums.Assurance;
import ma.TeethCare.common.enums.Sexe;
import ma.TeethCare.entities.dossierMedicale.dossierMedicale;
import ma.TeethCare.entities.antecedent.antecedent;
import ma.TeethCare.entities.baseEntity.baseEntity;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Patient extends baseEntity {


    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String email;
    private LocalDate dateNaissance;

    private Sexe sexe;
    private Assurance assurance;
    
    // Relations
    private dossierMedicale dossierMedicale;
    private List<antecedent> antecedents;
}

