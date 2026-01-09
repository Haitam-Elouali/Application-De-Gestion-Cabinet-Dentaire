package ma.TeethCare.service.modules.impl;
import ma.TeethCare.entities.log.log;
import ma.TeethCare.service.modules.api.logService;
import ma.TeethCare.repository.api.LogRepository;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

/**
 * @author CHOUKHAIRI Noureddine
 * @date 2025-12-14
 */

public class LogServiceImpl implements logService {

    private final LogRepository repository;

    public LogServiceImpl(LogRepository repository) {
        this.repository = repository;
    }

    @Override
    public log create(log entity) throws Exception {
        repository.create(entity);
        return entity;
    }

    @Override
    public Optional<log> findById(Long id) throws Exception {
        return Optional.ofNullable(repository.findById(id));
    }

    @Override
    public List<log> findAll() throws Exception {
        return repository.findAll();
    }

    @Override
    public log update(log entity) throws Exception {
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
    
    public List<log> findByUtilisateur(String utilisateur) throws Exception {
        return repository.findByUtilisateur(utilisateur);
    }
    
    public List<log> findByAction(String action) throws Exception {
        return repository.findByAction(action);
    }
    
    public List<log> findByDateRange(LocalDateTime debut, LocalDateTime fin) throws Exception {
        return repository.findByDateRange(debut, fin);
    }
}
