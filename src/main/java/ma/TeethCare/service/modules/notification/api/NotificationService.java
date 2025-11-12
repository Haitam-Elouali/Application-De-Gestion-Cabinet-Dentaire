package ma.TeethCare.service.modules.notification.api;

import ma.TeethCare.service.modules.notification.dto.NotificationDTO;

public interface NotificationService {

	void send(NotificationDTO notification);

	void sendRappelRdv(Long idRdv);
}


