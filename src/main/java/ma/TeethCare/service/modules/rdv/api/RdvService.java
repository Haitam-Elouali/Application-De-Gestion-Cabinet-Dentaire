package ma.TeethCare.service.modules.rdv.api;

import ma.TeethCare.service.common.dto.DateRange;

public interface RdvService {

	boolean checkAvailability(Long idMedecin, DateRange slot);

	Long book(Long idPatient, Long idMedecin, DateRange slot, String motif);

	void reschedule(Long idRdv, DateRange newSlot);

	void cancel(Long idRdv, String reason);
}


