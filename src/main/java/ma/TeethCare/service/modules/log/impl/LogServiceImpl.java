package ma.TeethCare.service.modules.log.impl;

import ma.TeethCare.entities.log.log;
import ma.TeethCare.mvc.dto.log.LogDTO;
import ma.TeethCare.repository.api.LogRepository;
import ma.TeethCare.service.modules.log.api.logService;
import ma.TeethCare.service.modules.log.mapper.LogMapper;
import ma.TeethCare.common.exceptions.ServiceException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LogServiceImpl implements logService {

    private final LogRepository logRepository;

    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public LogDTO create(LogDTO dto) throws Exception {
        try {
            log entity = LogMapper.toEntity(dto);
            logRepository.create(entity);
            return LogMapper.toDTO(entity);
        } catch (Exception e) {
            throw new ServiceException("Erreur création log", e);
        }
    }

    @Override
    public Optional<LogDTO> findById(Long id) throws Exception {
        try {
            log entity = logRepository.findById(id);
            return Optional.ofNullable(entity).map(LogMapper::toDTO);
        } catch (Exception e) {
            throw new ServiceException("Erreur recherche log", e);
        }
    }

    @Override
    public List<LogDTO> findAll() throws Exception {
        try {
            return logRepository.findAll().stream()
                    .map(LogMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Erreur listing logs", e);
        }
    }

    @Override
    public LogDTO update(LogDTO dto) throws Exception {
        // Logs are usually immutable, but satisfying interface
        try {
            log entity = LogMapper.toEntity(dto);
            logRepository.update(entity);
            return dto;
        } catch (Exception e) {
            throw new ServiceException("Erreur update log", e);
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        try {
            if (!exists(id)) return false;
            logRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new ServiceException("Erreur suppression log", e);
        }
    }

    @Override
    public boolean exists(Long id) throws Exception {
        try {
            if (id == null) return false;
            return logRepository.findById(id) != null;
        } catch (Exception e) {
            throw new ServiceException("Erreur existence log", e);
        }
    }

    @Override
    public long count() throws Exception {
        try {
            return logRepository.findAll().size();
        } catch (Exception e) {
            throw new ServiceException("Erreur comptage logs", e);
        }
    }

    @Override
    public List<LogDTO> findByAction(String action) throws Exception {
        return List.of();
    }

    @Override
    public List<LogDTO> findByDateRange(LocalDateTime debut, LocalDateTime fin) throws Exception {
        return List.of();
    }
}
