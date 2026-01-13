package ma.TeethCare.service.modules.agenda.api;

import ma.TeethCare.mvc.dto.rdv.RdvDTO;
import ma.TeethCare.service.common.BaseService;

public interface rdvService extends BaseService<RdvDTO, Long> {
    java.util.List<RdvDTO> findTodayAppointments() throws Exception;
    java.util.List<RdvDTO> findWaitingQueue() throws Exception;
}
