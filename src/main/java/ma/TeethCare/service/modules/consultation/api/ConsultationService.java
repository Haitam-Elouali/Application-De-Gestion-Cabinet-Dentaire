package ma.TeethCare.service.modules.consultation.api;

public interface ConsultationService {

	Long createFromRdv(Long idRdv);

	void addObservation(Long idConsultation, String notes);

	Long generateOrdonnance(Long idConsultation);
}


