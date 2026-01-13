package ma.TeethCare.service.modules.dossierMedical.impl;

import ma.TeethCare.entities.certificat.certificat;
import ma.TeethCare.entities.consultation.consultation;
import ma.TeethCare.entities.patient.Patient;
import ma.TeethCare.mvc.dto.certificat.CertificatDTO;
import ma.TeethCare.repository.api.CertificatRepository;
import ma.TeethCare.repository.api.ConsultationRepository;
import ma.TeethCare.repository.api.PatientRepository;
import ma.TeethCare.repository.mySQLImpl.ConsultationRepositoryImpl;
import ma.TeethCare.repository.mySQLImpl.PatientRepositoryImpl;
import ma.TeethCare.service.modules.dossierMedical.api.certificatService;
import ma.TeethCare.service.modules.dossierMedical.mapper.CertificatMapper;
import ma.TeethCare.common.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class certificatServiceImpl implements certificatService {

    private final CertificatRepository repository;
    private final ConsultationRepository consultationRepository;
    private final PatientRepository patientRepository;

    public certificatServiceImpl(CertificatRepository repository) {
        this.repository = repository;
        this.consultationRepository = new ConsultationRepositoryImpl();
        this.patientRepository = new PatientRepositoryImpl();
    }

    @Override
    public CertificatDTO create(CertificatDTO dto) throws Exception {
        try {
            if (dto == null) throw new ServiceException("DTO null");
            certificat entity = CertificatMapper.toEntity(dto);
            // Default type if null
            if (entity.getType() == null) entity.setType("Certificat Médical");
            
            repository.create(entity);
            return CertificatMapper.toDTO(entity);
        } catch (Exception e) {
            throw new ServiceException("Erreur création certificat", e);
        }
    }

    @Override
    public Optional<CertificatDTO> findById(Long id) throws Exception {
        try {
            if (id == null) return Optional.empty();
            certificat entity = repository.findById(id);
            if (entity == null) return Optional.empty();
            
            CertificatDTO dto = CertificatMapper.toDTO(entity);
            populatePatient(dto);
            return Optional.of(dto);
        } catch (Exception e) {
            throw new ServiceException("Erreur searching certificat", e);
        }
    }

    @Override
    public List<CertificatDTO> findAll() throws Exception {
        try {
            return repository.findAll().stream()
                    .map(CertificatMapper::toDTO)
                    .map(this::populatePatient)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Erreur listing certificats", e);
        }
    }

    private CertificatDTO populatePatient(CertificatDTO dto) {
        if (dto.getConsultationId() != null) {
            consultation cons = consultationRepository.findById(dto.getConsultationId());
            if (cons != null && cons.getPatientId() != null) {
                 Patient p = patientRepository.findById(cons.getPatientId());
                 if (p != null) {
                     dto.setPatientNom(p.getNom());
                     dto.setPatientPrenom(p.getPrenom());
                 }
            }
        }
        return dto;
    }

    @Override
    public CertificatDTO update(CertificatDTO dto) throws Exception {
        try {
            if (dto == null) throw new ServiceException("DTO null");
            certificat entity = CertificatMapper.toEntity(dto);
            repository.update(entity);
            return dto;
        } catch (Exception e) {
            throw new ServiceException("Erreur updating certificat", e);
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        try {
            if (!exists(id)) return false;
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new ServiceException("Erreur deleting certificat", e);
        }
    }

    @Override
    public boolean exists(Long id) throws Exception {
        try {
            return id != null && repository.findById(id) != null;
        } catch (Exception e) {
             throw new ServiceException("Erreur checking existence", e);
        }
    }

    @Override
    public long count() throws Exception {
        try {
            return repository.findAll().size();
        } catch (Exception e) {
             throw new ServiceException("Erreur counting", e);
        }
    }
}
