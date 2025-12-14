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
    private Long idCabinet;
    private String nom;
    private String email;
    private String logo;
    private String adresse;
    private String cin;
    private String tel1;
    private String tel2;
    private String siteWeb;
    private String instagram;
    private String facebook;
    private String description;
    
    private List<utilisateur> staff;
    private List<medicaments> stock;
    private List<revenues> revenues;
    private List<charges> charges;
    public static cabinetMedicale createTestInstance() {
        return cabinetMedicale.builder()
                .nom("Cabinet Test")
                .adresse("123 Rue Test")
                .email("contact@cabinet.com")
                .build();
    }
}
