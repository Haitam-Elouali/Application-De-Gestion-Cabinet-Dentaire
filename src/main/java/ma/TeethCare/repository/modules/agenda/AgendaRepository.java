package ma.TeethCare.repository.modules.agenda;

import ma.TeethCare.entities.agenda.agenda;
import ma.TeethCare.repository.common.BaseRepository;
import java.time.LocalDate;
import java.util.List;

public interface AgendaRepository extends BaseRepository<agenda, Long> {
    List<agenda> findByMedecinId(Long medecinId) throws Exception;
    List<agenda> findByDateRange(LocalDate debut, LocalDate fin) throws Exception;
    List<agenda> findByMois(String mois) throws Exception;
}
