package ma.TeethCare.service.modules.rdv;

import ma.TeethCare.entities.rdv.rdv;
import ma.TeethCare.service.common.BaseService;
import java.time.LocalDate;
import java.util.List;

public interface RdvService extends BaseService<rdv, Long> {
    List<rdv> findByPatientId(Long patientId) throws Exception;
    List<rdv> findByMedecinId(Long medecinId) throws Exception;
    List<rdv> findByDate(LocalDate date) throws Exception;
    List<rdv> findByPatientIdAndDate(Long patientId, LocalDate date) throws Exception;
    List<rdv> findByStatut(String statut) throws Exception;
}
