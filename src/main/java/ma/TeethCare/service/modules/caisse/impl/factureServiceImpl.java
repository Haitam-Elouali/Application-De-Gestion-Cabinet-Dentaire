package ma.TeethCare.service.modules.caisse.impl;

import ma.TeethCare.entities.facture.facture;
import ma.TeethCare.mvc.dto.facture.FactureDTO;
import ma.TeethCare.repository.api.FactureRepository;
import ma.TeethCare.service.modules.caisse.api.factureService;

import ma.TeethCare.service.modules.caisse.mapper.FactureMapper;
import ma.TeethCare.common.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class factureServiceImpl implements factureService {

    private final FactureRepository factureRepository;

    public factureServiceImpl(FactureRepository factureRepository) {
        this.factureRepository = factureRepository;
    }

    @Override
    public FactureDTO create(FactureDTO dto) throws Exception {
        try {
            if (dto == null) throw new ServiceException("DTO null");
            facture entity = FactureMapper.toEntity(dto);
            factureRepository.create(entity);
            return FactureMapper.toDTO(entity);
        } catch (Exception e) {
            throw new ServiceException("Erreur création facture", e);
        }
    }

    @Override
    public Optional<FactureDTO> findById(Long id) throws Exception {
        try {
            if (id == null) throw new ServiceException("ID null");
            facture entity = factureRepository.findById(id);
            return Optional.ofNullable(entity).map(FactureMapper::toDTO);
        } catch (Exception e) {
            throw new ServiceException("Erreur recherche facture", e);
        }
    }

    @Override
    public List<FactureDTO> findAll() throws Exception {
        try {
            return factureRepository.findAll().stream()
                    .map(FactureMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Erreur listing factures", e);
        }
    }

    @Override
    public FactureDTO update(FactureDTO dto) throws Exception {
        try {
            if (dto == null) throw new ServiceException("DTO null");
            facture entity = FactureMapper.toEntity(dto);
            factureRepository.update(entity);
            return dto;
        } catch (Exception e) {
            throw new ServiceException("Erreur mise à jour facture", e);
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        try {
            if (!exists(id)) return false;
            factureRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new ServiceException("Erreur suppression facture", e);
        }
    }

    @Override
    public boolean exists(Long id) throws Exception {
        try {
            if (id == null) return false;
            return factureRepository.findById(id) != null;
        } catch (Exception e) {
            throw new ServiceException("Erreur existence facture", e);
        }
    }

    @Override
    public long count() throws Exception {
        try {
            return factureRepository.findAll().size();
        } catch (Exception e) {
            throw new ServiceException("Erreur comptage factures", e);
        }
    }
}
