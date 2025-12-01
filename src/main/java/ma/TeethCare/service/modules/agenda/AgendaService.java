package ma.TeethCare.service.modules.agenda;

import ma.TeethCare.entities.agenda.agenda;
import ma.TeethCare.service.common.BaseService;
import java.time.LocalDate;
import java.util.List;

public interface AgendaService extends BaseService<agenda, Long> {
    List<agenda> findByMedecinId(Long medecinId) throws Exception;
    List<agenda> findByDateRange(LocalDate debut, LocalDate fin) throws Exception;
    List<agenda> findByMois(String mois) throws Exception;
}
