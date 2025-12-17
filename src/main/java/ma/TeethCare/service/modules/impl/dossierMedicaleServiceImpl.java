package ma.TeethCare.service.modules.impl;

import ma.TeethCare.entities.dossierMedicale.dossierMedicale;
import ma.TeethCare.repository.api.DossierMedicaleRepository;
import ma.TeethCare.service.modules.api.dossierMedicaleService;

import java.util.List;
import java.util.Optional;

public class dossierMedicaleServiceImpl implements dossierMedicaleService {

    private final DossierMedicaleRepository dossierMedicaleRepository;

    public dossierMedicaleServiceImpl(DossierMedicaleRepository dossierMedicaleRepository) {
        this.dossierMedicaleRepository = dossierMedicaleRepository;
    }

    @Override
    public dossierMedicale create(dossierMedicale entity) throws Exception {
        if (entity == null) {
            throw new IllegalArgumentException("DossierMedicale ne peut pas être null");
        }
        dossierMedicaleRepository.create(entity);
        return entity;
    }

    @Override
    public Optional<dossierMedicale> findById(Long id) throws Exception {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(dossierMedicaleRepository.findById(id));
    }

    @Override
    public List<dossierMedicale> findAll() throws Exception {
        return dossierMedicaleRepository.findAll();
    }

    @Override
    public dossierMedicale update(dossierMedicale entity) throws Exception {
        if (entity == null || entity.getId() == null) {
            throw new IllegalArgumentException("DossierMedicale ou ID invalide");
        }
        dossierMedicaleRepository.update(entity);
        return entity;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        if (id == null) {
            return false;
        }
        dossierMedicaleRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean exists(Long id) throws Exception {
        if (id == null) {
            return false;
        }
        return dossierMedicaleRepository.findById(id) != null;
    }

    @Override
    public long count() throws Exception {
        return dossierMedicaleRepository.findAll().size();
    }
}
