package ma.TeethCare.mvc.dto.antecedent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AntecedentDTO {
    private Long id;
    private Long dossierMedicaleId;
    private String nom; // Maps to Description in UI
    private String categorie; // Maps to Type in UI
    private String niveauDeRisque; // Maps to Risque in UI
    private LocalDate dateAntecedent;
    private LocalDateTime dateCreation;
}
