package ma.TeethCare.service.modules.users.impl;

import ma.TeethCare.entities.medecin.medecin;
import ma.TeethCare.mvc.dto.medecin.MedecinDTO;
import ma.TeethCare.repository.api.MedecinRepository;
import ma.TeethCare.service.modules.users.api.medecinService;
import ma.TeethCare.service.modules.users.mapper.MedecinMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Hamza ALAOUI
 * @date 2025-12-17
 */

public class medecinServiceImpl implements medecinService {

    private final MedecinRepository medecinRepository;

    public medecinServiceImpl(MedecinRepository medecinRepository) {
        this.medecinRepository = medecinRepository;
    }

    @Override
    public MedecinDTO create(MedecinDTO dto) throws Exception {
        if (dto == null) throw new IllegalArgumentException("MedecinDTO is null");

        medecin entity = MedecinMapper.toEntity(dto);
        medecinRepository.create(entity);

        if (entity.getId() != null) {
            return MedecinMapper.toDTO(medecinRepository.findById(entity.getId()));
        }
        return MedecinMapper.toDTO(entity);
    }

    @Override
    public Optional<MedecinDTO> findById(Long id) throws Exception {
        if (id == null) return Optional.empty();
        try {
            medecin found = medecinRepository.findById(id);
            return Optional.ofNullable(MedecinMapper.toDTO(found));
        } catch (Exception e) {
            throw new Exception("Erreur lors de la recherche du médecin par id=" + id, e);
        }
    }

    @Override
    public List<MedecinDTO> findAll() throws Exception {
        try {
            return medecinRepository.findAll().stream()
                    .map(MedecinMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            // throw new Exception("Erreur SQL lors de la récupération de tous les médecins", e); // Original exception type
            throw new RuntimeException("Erreur lors de la récupération de tous les médecins", e);
        }
    }

    @Override
    public MedecinDTO update(MedecinDTO dto) throws Exception {
        if (dto == null) throw new IllegalArgumentException("MedecinDTO is null");
        if (dto.getId() == null) throw new IllegalArgumentException("Medecin id is required for update");

        medecin entity = MedecinMapper.toEntity(dto);
        
        try {
            medecinRepository.update(entity);
            return MedecinMapper.toDTO(medecinRepository.findById(entity.getId()));
        } catch (Exception e) {
            throw new Exception("Erreur lors de la mise à jour du médecin id=" + dto.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        if (id == null) return false;

        try {
            if (!exists(id)) return false;

            medecinRepository.deleteById(id);
            return true;

        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression du médecin id=" + id, e);
        }
    }

    @Override
    public boolean exists(Long id) throws Exception {
        if (id == null) return false;
        try {
            return medecinRepository.findById(id) != null;
        } catch (Exception e) {
            throw new Exception("Erreur lors de la vérification d'existence du médecin id=" + id, e);
        }
    }

    @Override
    public long count() throws Exception {
        return medecinRepository.findAll().size();
    }
}
