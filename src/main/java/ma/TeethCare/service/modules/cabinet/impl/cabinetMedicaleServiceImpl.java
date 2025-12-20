package ma.TeethCare.service.modules.cabinet.impl;
import ma.TeethCare.entities.cabinetMedicale.cabinetMedicale;
import ma.TeethCare.service.modules.cabinet.api.cabinetMedicaleService;
import ma.TeethCare.repository.api.CabinetMedicaleRepository;
import java.util.List;
import java.util.Optional;

/**
 * @author Haitam ELOUALI
 * @date 2025-12-14
 */

public class cabinetMedicaleServiceImpl implements cabinetMedicaleService {

    private final CabinetMedicaleRepository repository;

    public cabinetMedicaleServiceImpl(CabinetMedicaleRepository repository) {
        this.repository = repository;
    }

    @Override
    public cabinetMedicale create(cabinetMedicale entity) throws Exception {
        repository.create(entity);
        return entity;
    }

    @Override
    public Optional<cabinetMedicale> findById(Long id) throws Exception {
        return Optional.ofNullable(repository.findById(id));
    }

    @Override
    public List<cabinetMedicale> findAll() throws Exception {
        return repository.findAll();
    }

    @Override
    public cabinetMedicale update(cabinetMedicale entity) throws Exception {
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
}
