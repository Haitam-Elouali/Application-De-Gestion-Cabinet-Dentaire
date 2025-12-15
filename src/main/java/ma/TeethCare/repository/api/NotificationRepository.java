package ma.TeethCare.repository.api;

import ma.TeethCare.entities.notification.notification;
import ma.TeethCare.repository.common.CrudRepository;

import java.util.List;

public interface NotificationRepository extends CrudRepository<notification, Long> {
    List<notification> findByNonLues() throws Exception;

    List<notification> findByType(String type) throws Exception;
}
