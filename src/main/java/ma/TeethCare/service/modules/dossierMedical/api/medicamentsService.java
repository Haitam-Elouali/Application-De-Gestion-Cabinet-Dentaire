package ma.TeethCare.service.modules.dossierMedical.api;

import ma.TeethCare.mvc.dto.medicament.MedicamentDTO;
import ma.TeethCare.service.common.BaseService;

import java.util.Optional;

public interface medicamentsService extends BaseService<MedicamentDTO, Long> {
     Optional<MedicamentDTO> findByNomCommercial(String nomCommercial) throws Exception;
}
