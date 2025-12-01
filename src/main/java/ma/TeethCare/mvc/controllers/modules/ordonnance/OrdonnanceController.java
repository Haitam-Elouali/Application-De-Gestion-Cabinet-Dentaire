package ma.TeethCare.mvc.controllers.ordonnance;

import ma.TeethCare.mvc.dto.ordonnance.OrdonnanceDTO;
import java.util.List;

public interface OrdonnanceController {
    void create(OrdonnanceDTO ordonnance) throws Exception;
    OrdonnanceDTO getById(Long id) throws Exception;
    List<OrdonnanceDTO> getAll() throws Exception;
    void update(Long id, OrdonnanceDTO ordonnance) throws Exception;
    void delete(Long id) throws Exception;
    List<OrdonnanceDTO> findByPatientId(Long patientId) throws Exception;
    List<OrdonnanceDTO> findByMedecinId(Long medecinId) throws Exception;
}
