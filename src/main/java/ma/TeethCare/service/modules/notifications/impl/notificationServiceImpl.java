package ma.TeethCare.service.modules.notifications.impl;
import ma.TeethCare.entities.notification.notification;
import ma.TeethCare.service.modules.notifications.api.notificationService;
import ma.TeethCare.repository.api.NotificationRepository;
import java.util.List;
import java.util.Optional;

/**
 * @author CHOUKHAIRI Noureddine
 * @date 2025-12-14
 */

public class notificationServiceImpl implements notificationService {

    private final NotificationRepository repository;

    public notificationServiceImpl(NotificationRepository repository) {
        this.repository = repository;
    }

    @Override
    public notification create(notification entity) throws Exception {
        repository.create(entity);
        return entity;
    }

    @Override
    public Optional<notification> findById(Long id) throws Exception {
        return Optional.ofNullable(repository.findById(id));
    }

    @Override
    public List<notification> findAll() throws Exception {
        return repository.findAll();
    }

    @Override
    public notification update(notification entity) throws Exception {
        repository.update(entity);
        return entity;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        repository.deleteById(id);
        return true;
    }

    @Override
    public boolean exists(Long id) throws Exception {
        return repository.findById(id) != null;
    }

    @Override
    public long count() throws Exception {
        return repository.findAll().size();
    }

    public List<notification> findByNonLues() throws Exception {
        return repository.findByNonLues();
    }

    public List<notification> findByType(String type) throws Exception {
        return repository.findByType(type);
    }
}
