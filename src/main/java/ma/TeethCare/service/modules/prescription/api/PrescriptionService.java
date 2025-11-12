package ma.TeethCare.service.modules.prescription.api;

import ma.TeethCare.service.modules.prescription.dto.PrescriptionItemDTO;

public interface PrescriptionService {

	void addPrescription(Long idOrdonnance, PrescriptionItemDTO item);
}


