package ma.TeethCare.repository.modules.notification;

import ma.TeethCare.entities.notification.notification;
import ma.TeethCare.repository.common.BaseRepository;
import java.util.List;

public interface NotificationRepository extends BaseRepository<notification, Long> {
    List<notification> findByNonLues() throws Exception;
    List<notification> findByType(String type) throws Exception;
}
