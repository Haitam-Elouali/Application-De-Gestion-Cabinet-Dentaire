package ma.TeethCare.service.modules.impl;

import ma.TeethCare.entities.statistique.statistique;
import ma.TeethCare.repository.api.StatistiqueRepository;
import ma.TeethCare.service.modules.api.statistiqueService;

import java.util.List;

public class statistiqueServiceImpl implements statistiqueService {

    private final StatistiqueRepository repository;

    public statistiqueServiceImpl(StatistiqueRepository repository) {
        this.repository = repository;
    }

    @Override
    public statistique create(statistique entity) throws Exception {
        try {
            if (entity == null) {
                throw new ma.TeethCare.common.exceptions.ServiceException("Statistique ne peut pas être null");
            }
            repository.create(entity);
            return entity;
        } catch (Exception e) {
            throw new ma.TeethCare.common.exceptions.ServiceException("Erreur lors de la création de la statistique", e);
        }
    }

    @Override
    public java.util.Optional<statistique> findById(Long id) throws Exception {
        try {
            if (id == null) {
                throw new ma.TeethCare.common.exceptions.ServiceException("ID ne peut pas être null");
            }
            return java.util.Optional.ofNullable(repository.findById(id));
        } catch (Exception e) {
            throw new ma.TeethCare.common.exceptions.ServiceException("Erreur lors de la récupération de la statistique", e);
        }
    }

    @Override
    public List<statistique> findAll() throws Exception {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new ma.TeethCare.common.exceptions.ServiceException("Erreur lors de la récupération de la liste des statistiques", e);
        }
    }

    @Override
    public statistique update(statistique entity) throws Exception {
        try {
            if (entity == null) {
                throw new ma.TeethCare.common.exceptions.ServiceException("Statistique ne peut pas être null");
            }
            repository.update(entity);
            return entity;
        } catch (Exception e) {
            throw new ma.TeethCare.common.exceptions.ServiceException("Erreur lors de la mise à jour de la statistique", e);
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        try {
            if (!exists(id)) {
                return false;
            }
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new ma.TeethCare.common.exceptions.ServiceException("Erreur lors de la suppression de la statistique", e);
        }
    }

    @Override
    public boolean exists(Long id) throws Exception {
        try {
            if (id == null) {
                return false;
            }
            return repository.findById(id) != null;
        } catch (Exception e) {
            throw new ma.TeethCare.common.exceptions.ServiceException("Erreur lors de la vérification de l'existence de la statistique", e);
        }
    }

    @Override
    public long count() throws Exception {
        try {
            return repository.findAll().size();
        } catch (Exception e) {
            throw new ma.TeethCare.common.exceptions.ServiceException("Erreur lors du comptage des statistiques", e);
        }
    }
}
