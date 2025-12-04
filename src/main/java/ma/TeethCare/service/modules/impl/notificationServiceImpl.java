package ma.TeethCare.service.impl;

import ma.TeethCare.entities.notification.notification;
import ma.TeethCare.service.api.notificationService;
import java.util.List;
import java.util.Optional;

public class notificationServiceImpl implements notificationService {

    @Override
    public notification create(notification entity) throws Exception {
        // TODO: Implement method
        return null;
    }

    @Override
    public Optional<notification> findById(Long id) throws Exception {
        // TODO: Implement method
        return Optional.empty();
    }

    @Override
    public List<notification> findAll() throws Exception {
        // TODO: Implement method
        return null;
    }

    @Override
    public notification update(notification entity) throws Exception {
        // TODO: Implement method
        return null;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        // TODO: Implement method
        return false;
    }

    @Override
    public boolean exists(Long id) throws Exception {
        // TODO: Implement method
        return false;
    }

    @Override
    public long count() throws Exception {
        // TODO: Implement method
        return 0;
    }
}
