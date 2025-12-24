package ma.TeethCare.service.modules.dashboard_statistiques.impl;

import ma.TeethCare.entities.statistique.statistique;
import ma.TeethCare.repository.api.StatistiqueRepository;
import ma.TeethCare.repository.mySQLImpl.StatistiqueRepositoryImpl;
import ma.TeethCare.service.modules.dashboard_statistiques.api.statistiqueService;
import ma.TeethCare.service.modules.dashboard_statistiques.dto.StatistiqueDto;
import ma.TeethCare.service.modules.dashboard_statistiques.mapper.StatistiqueMapper;

import java.util.List;
import java.util.stream.Collectors;

import ma.TeethCare.entities.statistique.statistique;
import ma.TeethCare.repository.api.StatistiqueRepository;
import ma.TeethCare.common.exceptions.ServiceException;
import ma.TeethCare.entities.statistique.statistique;
import ma.TeethCare.repository.api.StatistiqueRepository;
import ma.TeethCare.service.modules.dashboard_statistiques.dto.StatistiqueDto;
import ma.TeethCare.service.modules.dashboard_statistiques.mapper.StatistiqueMapper;

import java.util.List;
import java.util.stream.Collectors;

public class statistiqueServiceImpl implements statistiqueService {

    private final StatistiqueRepository repository;

    // No Mapper injection needed if methods are static
    public statistiqueServiceImpl(StatistiqueRepository repository) {
        this.repository = repository;
    }

    @Override
    public StatistiqueDto create(StatistiqueDto dto) {
        try {
            statistique entity = StatistiqueMapper.toEntity(dto);
            repository.create(entity);
            return StatistiqueMapper.toDto(entity);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création de la statistique", e);
        }
    }

    @Override
    public StatistiqueDto update(Long id, StatistiqueDto dto) {
        try {
            statistique existing = repository.findById(id);
            if (existing == null) {
                throw new ServiceException("Statistique non trouvée avec l'ID: " + id);
            }
            // Update fields from DTO to Entity
            statistique updatedEntity = StatistiqueMapper.toEntity(dto);
            updatedEntity.setId(id); // Ensure ID is preserved
            repository.update(updatedEntity);
            return StatistiqueMapper.toDto(updatedEntity);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour de la statistique", e);
        }
    }

    @Override
    public StatistiqueDto findById(Long id) {
        try {
            statistique entity = repository.findById(id);
            return StatistiqueMapper.toDto(entity);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de la statistique", e);
        }
    }

    @Override
    public List<StatistiqueDto> findAll() {
        try {
            return repository.findAll().stream()
                    .map(StatistiqueMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des statistiques", e);
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
            throw new ServiceException("Erreur lors de la suppression de la statistique", e);
        }
    }

    @Override
    public boolean exists(Long id) throws Exception {
        try {
            if (id == null)
                return false;
            return repository.findById(id) != null;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la vérification de l'existence", e);
        }
    }

    @Override
    public long count() throws Exception {
        try {
            return repository.findAll().size();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage", e);
        }
    }
}
