package ma.TeethCare.mvc.controllers.notification;

import ma.TeethCare.mvc.dto.notification.NotificationDTO;
import java.util.List;

public interface NotificationController {
    void create(NotificationDTO notification) throws Exception;
    NotificationDTO getById(Long id) throws Exception;
    List<NotificationDTO> getAll() throws Exception;
    void delete(Long id) throws Exception;
    List<NotificationDTO> findUnread() throws Exception;
}
