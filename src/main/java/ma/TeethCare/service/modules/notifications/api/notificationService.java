package ma.TeethCare.service.modules.notifications.api;

import ma.TeethCare.mvc.dto.notification.NotificationDTO;
import ma.TeethCare.service.common.BaseService;

public interface notificationService extends BaseService<NotificationDTO, Long> {
    java.util.List<NotificationDTO> findByNonLues() throws Exception;
    java.util.List<NotificationDTO> findByType(String type) throws Exception;
}
