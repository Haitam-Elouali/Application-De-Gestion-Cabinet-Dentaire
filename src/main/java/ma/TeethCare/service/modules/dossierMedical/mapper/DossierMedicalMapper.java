package ma.TeethCare.service.modules.dossierMedical.mapper;

import ma.TeethCare.entities.dossierMedicale.dossierMedicale;
import ma.TeethCare.service.modules.dossierMedical.dto.DossierMedicalDto;

public class DossierMedicalMapper {

    public static DossierMedicalDto toDto(dossierMedicale entity) {
        if (entity == null) {
            return null;
        }
        return new DossierMedicalDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getDateDeCreation());
    }

    public static dossierMedicale toEntity(DossierMedicalDto dto) {
        if (dto == null) {
            return null;
        }
        return dossierMedicale.builder()
                .id(dto.id())
                .patientId(dto.patientId())
                .dateDeCreation(dto.dateDeCreation())
                .build();
    }
}
