package ma.TeethCare.entities.staff;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.utilisateur.utilisateur;
import ma.TeethCare.entities.cabinetMedicale.cabinetMedicale;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class staff extends utilisateur {
    private Double salaire;
    private LocalDate dateEmbauche; // Was dateRecrutement
    // Removed prime, soldeConge, cabinetMedicale field if not in schema (cabinet_id usually here? or in subclass?)
    // Assuming staff is just an abstract or table with common staff fields. 
    // Schema doesn't specify cabinet_id on staff, leaving it if needed or removing. 
    // Wait, cabinetMedicale is likely needed for relationship. Keeping it if it makes sense, but schema strictness?
    // Let's keep cabinetMedicale for now, but update fields.
    
    private cabinetMedicale cabinetMedicale;
    public static staff createTestInstance() {
        return staff.builder()
                .nom("StaffGeneric")
                .email("staff@test.com")
                .cin("STF001")
                .salaire(3000.0)
                .dateEmbauche(LocalDate.now())
                .build();
    }
}
