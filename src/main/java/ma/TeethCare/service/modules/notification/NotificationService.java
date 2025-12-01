package ma.TeethCare.service.modules.notification;

import ma.TeethCare.entities.notification.notification;
import ma.TeethCare.service.common.BaseService;
import java.util.List;

public interface NotificationService extends BaseService<notification, Long> {
    List<notification> findByNonLues() throws Exception;
    List<notification> findByType(String type) throws Exception;
    void sendNotification(String titre, String message, String type) throws Exception;
}
