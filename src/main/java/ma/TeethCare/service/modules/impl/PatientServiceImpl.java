package ma.TeethCare.service.modules.impl;

import ma.TeethCare.entities.patient.Patient;
import ma.TeethCare.repository.api.PatientRepository;
import ma.TeethCare.service.modules.api.PatientService;
import ma.TeethCare.common.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * @author Zouhair MOKADAMI
 * @date 2025-12-10
 */

public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    // Injection par constructeur (en vrai projet : ApplicationContext)
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }
    @Override
    public Patient create(Patient entity) throws Exception {
        try {
            if (entity == null) {
                throw new ServiceException("Patient ne peut pas être null");
            }
            patientRepository.create(entity);
            return entity;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création du patient", e);
        }
    }

    @Override
    public Optional<Patient> findById(Long id) throws Exception {
        try {
            if (id == null) {
                throw new ServiceException("ID patient ne peut pas être null");
            }
            return Optional.ofNullable(patientRepository.findById(id));
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération du patient", e);
        }
    }

    @Override
    public List<Patient> findAll() throws Exception {
        try {
            return patientRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de la liste des patients", e);
        }
    }

    @Override
    public Patient update(Patient entity) throws Exception {
        try {
            if (entity == null) {
                throw new ServiceException("Patient ne peut pas être null");
            }
            patientRepository.update(entity);
            return entity;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour du patient", e);
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        try {
            if (!exists(id)) {
                return false;
            }
            patientRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression du patient", e);
        }
    }

    @Override
    public boolean exists(Long id) throws Exception {
        try {
            if (id == null) {
                return false;
            }
            return patientRepository.findById(id) != null;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la vérification d'existence du patient", e);
        }
    }

    @Override
    public long count() throws Exception {
        try {
            return patientRepository.findAll().size();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des patients", e);
        }
    }
}
