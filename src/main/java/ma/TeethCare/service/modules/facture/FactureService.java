package ma.TeethCare.service.modules.facture;

import ma.TeethCare.entities.facture.facture;
import ma.TeethCare.service.common.BaseService;
import java.util.List;

public interface FactureService extends BaseService<facture, Long> {
    List<facture> findByPatientId(Long patientId) throws Exception;
    List<facture> findByConsultationId(Long consultationId) throws Exception;
    List<facture> findByStatut(String statut) throws Exception;
    List<facture> findUnpaidInvoices() throws Exception;
    Double getTotalByPatientId(Long patientId) throws Exception;
}
