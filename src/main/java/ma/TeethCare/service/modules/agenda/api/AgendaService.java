package ma.TeethCare.service.modules.agenda.api;

import java.util.List;

import ma.TeethCare.service.modules.agenda.dto.AgendaMonthView;

public interface AgendaService {

	void setIndisponibilites(Long idMedecin, String mois, List<Integer> joursNonDisponibles);

	AgendaMonthView getAgenda(Long idMedecin, String mois);
}


