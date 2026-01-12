package ma.TeethCare.service.modules.dossierMedical.api;

import ma.TeethCare.mvc.dto.dossierMedicale.DossierMedicaleDTO;
import ma.TeethCare.service.common.BaseService;

// Replacing specific signatures with BaseService<DTO, Long> if appropriate, or keeping specific signatures but updating DTO type.
// User wanted strict DTO usage. The existing service didn't extend BaseService.
// I will standardise it to extend BaseService for consistency if possible, OR just update DTO types.
// Given strict "Service.method(DTO)" rule, BaseService fits well.

public interface dossierMedicaleService extends BaseService<DossierMedicaleDTO, Long> {
     // Add specific methods if needed, e.g. findByPatientId
}
