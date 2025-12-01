package ma.TeethCare.mvc.controllers.rdv;

import ma.TeethCare.mvc.dto.rdv.RdvDTO;
import java.time.LocalDate;
import java.util.List;

public interface RdvController {
    void create(RdvDTO rdv) throws Exception;
    RdvDTO getById(Long id) throws Exception;
    List<RdvDTO> getAll() throws Exception;
    void update(Long id, RdvDTO rdv) throws Exception;
    void delete(Long id) throws Exception;
    List<RdvDTO> findByPatientId(Long patientId) throws Exception;
    List<RdvDTO> findByMedecinId(Long medecinId) throws Exception;
    List<RdvDTO> findByDate(LocalDate date) throws Exception;
}
