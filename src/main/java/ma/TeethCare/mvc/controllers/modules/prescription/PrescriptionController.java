package ma.TeethCare.mvc.controllers.prescription;

import ma.TeethCare.mvc.dto.prescription.PrescriptionDTO;
import java.util.List;

public interface PrescriptionController {
    void create(PrescriptionDTO prescription) throws Exception;
    PrescriptionDTO getById(Long id) throws Exception;
    List<PrescriptionDTO> getAll() throws Exception;
    void update(Long id, PrescriptionDTO prescription) throws Exception;
    void delete(Long id) throws Exception;
    List<PrescriptionDTO> findByOrdonnanceId(Long ordonnanceId) throws Exception;
}
