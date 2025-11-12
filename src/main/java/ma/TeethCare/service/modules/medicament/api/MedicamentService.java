package ma.TeethCare.service.modules.medicament.api;

import java.util.List;

import ma.TeethCare.service.common.base.CrudService;
import ma.TeethCare.service.modules.medicament.dto.MedicamentDTO;

public interface MedicamentService extends CrudService<MedicamentDTO, Long> {

	List<MedicamentDTO> searchByName(String namePart);

	void decrementOnPrescription(Long idMedicament, int quantity);
}


