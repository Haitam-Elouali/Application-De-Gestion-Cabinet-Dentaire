package ma.TeethCare.service.modules.cabinet.mapper;

import ma.TeethCare.entities.cabinetMedicale.cabinetMedicale;
import ma.TeethCare.mvc.dto.cabinetMedicale.CabinetMedicaleDTO;

public class CabinetMedicaleMapper {

    public static CabinetMedicaleDTO toDTO(cabinetMedicale entity) {
        if (entity == null) return null;
        
        return CabinetMedicaleDTO.builder()
                .id(entity.getId())
                .nom(entity.getNomCabinet())
                .adresse(entity.getAdresse())
                .telephone(entity.getTele())
                .email(entity.getEmail())
                .siteWeb(entity.getSiteWeb())
                // .numeroLicence() // Not in entity
                // .directeur() // Not in entity
                .build();
    }

    public static cabinetMedicale toEntity(CabinetMedicaleDTO dto) {
        if (dto == null) return null;

        cabinetMedicale entity = new cabinetMedicale();
        entity.setId(dto.getId());
        entity.setNomCabinet(dto.getNom());
        entity.setAdresse(dto.getAdresse());
        entity.setTele(dto.getTelephone());
        entity.setEmail(dto.getEmail());
        entity.setSiteWeb(dto.getSiteWeb());
        
        return entity;
    }
}
