package ma.TeethCare.mvc.controllers.modules.antecedent.agenda;

import ma.TeethCare.mvc.dto.agenda.AgendaDTO;
import java.time.LocalDate;
import java.util.List;

public interface AgendaController {
    void create(AgendaDTO agenda) throws Exception;
    AgendaDTO getById(Long id) throws Exception;
    List<AgendaDTO> getAll() throws Exception;
    void update(Long id, AgendaDTO agenda) throws Exception;
    void delete(Long id) throws Exception;
    List<AgendaDTO> findByMedecinId(Long medecinId) throws Exception;
    List<AgendaDTO> findByDateRange(LocalDate debut, LocalDate fin) throws Exception;
}
