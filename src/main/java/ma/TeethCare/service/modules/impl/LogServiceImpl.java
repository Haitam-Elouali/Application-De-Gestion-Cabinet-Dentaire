package ma.TeethCare.service.modules.impl;

import ma.TeethCare.entities.log.log;
import ma.TeethCare.repository.api.LogRepository;
import ma.TeethCare.service.modules.api.logService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class LogServiceImpl implements logService {

    private final LogRepository logRepository;

    // Constructor injection pour ton repository
    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public log create(log entity) throws Exception {
        logRepository.create(entity);
        return entity;
    }

    @Override
    public Optional<log> findById(Long id) throws Exception {
        return Optional.ofNullable(logRepository.findById(id));
    }

    @Override
    public List<log> findAll() throws Exception {
        return logRepository.findAll();
    }

    @Override
    public log update(log entity) throws Exception {
        logRepository.update(entity);
        return entity;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        logRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean exists(Long id) throws Exception {
        return logRepository.findById(id) != null;
    }

    @Override
    public long count() throws Exception {
        return findAll().size();
    }
}