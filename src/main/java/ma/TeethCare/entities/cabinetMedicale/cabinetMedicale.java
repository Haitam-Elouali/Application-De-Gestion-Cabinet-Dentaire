package ma.TeethCare.entities.cabinetMedicale;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.TeethCare.entities.baseEntity.baseEntity;
import ma.TeethCare.entities.utilisateur.utilisateur;
import ma.TeethCare.entities.medicaments.medicaments;
import ma.TeethCare.entities.revenues.revenues;
import ma.TeethCare.entities.charges.charges;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@lombok.experimental.SuperBuilder
public class cabinetMedicale extends baseEntity {
    private Long id; // Was idCabinet
    private String nomCabinet; // Was nom
    private String adresse;
    private String tele; // Was tel1
    private String email;
    private String logo;
    private String instagram;
    private String siteWeb;
    private String description;
    // Removed cin, tel2, facebook

    
    private List<utilisateur> staff;
    private List<medicaments> stock;
    private List<revenues> revenues;
    private List<charges> charges;
    public static cabinetMedicale createTestInstance() {
        return cabinetMedicale.builder()
                .nomCabinet("Cabinet Test")
                .adresse("123 Rue Test")
                .email("contact@cabinet.com")
                .tele("0522000000")
                .build();
    }
}
