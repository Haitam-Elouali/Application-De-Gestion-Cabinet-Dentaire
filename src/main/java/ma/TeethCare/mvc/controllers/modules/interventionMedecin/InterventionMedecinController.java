package ma.TeethCare.mvc.controllers.interventionMedecin;

import ma.TeethCare.mvc.dto.interventionMedecin.InterventionMedecinDTO;
import java.util.List;

public interface InterventionMedecinController {
    void create(InterventionMedecinDTO intervention) throws Exception;
    InterventionMedecinDTO getById(Long id) throws Exception;
    List<InterventionMedecinDTO> getAll() throws Exception;
    void update(Long id, InterventionMedecinDTO intervention) throws Exception;
    void delete(Long id) throws Exception;
    List<InterventionMedecinDTO> findByMedecinId(Long medecinId) throws Exception;
}
