package ma.TeethCare.mvc.controllers.facture;

import ma.TeethCare.mvc.dto.facture.FactureDTO;
import java.util.List;

public interface FactureController {
    void create(FactureDTO facture) throws Exception;
    FactureDTO getById(Long id) throws Exception;
    List<FactureDTO> getAll() throws Exception;
    void update(Long id, FactureDTO facture) throws Exception;
    void delete(Long id) throws Exception;
    List<FactureDTO> findByPatientId(Long patientId) throws Exception;
    List<FactureDTO> findUnpaidInvoices() throws Exception;
    Double getTotalByPatientId(Long patientId) throws Exception;
}
