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
@Builder
public class secretaire extends staff {
    private String numCNSS;
    private Double commission;
    
    // Relations
    private List<rdv> rdvs;
    private List<facture> factures;
}
