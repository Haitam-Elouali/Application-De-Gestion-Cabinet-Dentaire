package ma.TeethCare.service.modules.dossierMedical.dto;

import java.time.LocalDateTime;

public record DossierMedicalDto(
        Long id,
        Long patientId,
        LocalDateTime dateDeCreation) {
}
