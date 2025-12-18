package ma.TeethCare.service.modules.impl;
import ma.TeethCare.entities.revenues.revenues;
import ma.TeethCare.service.modules.api.revenuesService;
import java.util.List;
import java.util.Optional;

/**
 * @author ELOUALI Haitam
 * @date 2025-12-09
 */

import ma.TeethCare.repository.api.RevenuesRepository;

/**
 * @author Haitam ELOUALI
 * @date 2025-12-10
 */

public class revenuesServiceImpl implements revenuesService {
    
    // Dependency Injection would be better, but for now manual instantiation as per pattern
    private final RevenuesRepository revenuesRepository;

    public revenuesServiceImpl(RevenuesRepository revenuesRepository) {
        this.revenuesRepository = revenuesRepository;
    }

    @Override
    public revenues create(revenues entity) throws Exception {
        revenuesRepository.create(entity);
        return entity;
    }

    @Override
    public Optional<revenues> findById(Long id) throws Exception {
        return Optional.ofNullable(revenuesRepository.findById(id));
    }

    @Override
    public List<revenues> findAll() throws Exception {
        return revenuesRepository.findAll();
    }

    @Override
    public revenues update(revenues entity) throws Exception {
        revenuesRepository.update(entity);
        return entity;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        if (exists(id)) {
            revenuesRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean exists(Long id) throws Exception {
        return revenuesRepository.findById(id) != null;
    }

    @Override
    public long count() throws Exception {
        return findAll().size();
    }
}
