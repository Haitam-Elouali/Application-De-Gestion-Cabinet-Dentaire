package ma.TeethCare.entities.secretaire;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.staff.staff;
import ma.TeethCare.entities.rdv.rdv;
import ma.TeethCare.entities.facture.facture;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class secretaire extends staff {
    private String numCNSS;
    private Double commission;
    
    // Relations
    private List<rdv> rdvs;
    private List<facture> factures;
    public static secretaire createTestInstance() {
        return secretaire.builder()
                .nom("Moneypenny")
                .email("sec@office.com")
                .cin("SEC001")
                .build();
    }
}
