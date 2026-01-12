package ma.TeethCare.service.modules.log.api;
import ma.TeethCare.mvc.dto.log.LogDTO;
import ma.TeethCare.service.common.BaseService;

public interface logService extends BaseService<LogDTO, Long> {
    java.util.List<LogDTO> findByAction(String action) throws Exception;
    java.util.List<LogDTO> findByDateRange(java.time.LocalDateTime debut, java.time.LocalDateTime fin) throws Exception;
}
