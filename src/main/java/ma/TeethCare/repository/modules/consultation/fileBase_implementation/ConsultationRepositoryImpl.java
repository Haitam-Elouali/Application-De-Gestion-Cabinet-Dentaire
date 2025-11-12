package ma.TeethCare.repository.modules.consultation.fileBase_implementation;

import ma.TeethCare.entities.consultation.consultation;
import ma.TeethCare.entities.enums.Statut;
import ma.TeethCare.repository.modules.consultation.api.ConsultationRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ConsultationRepositoryImpl implements ConsultationRepository {

    private static final List<consultation> db = new ArrayList<>();
    private static long idCounter = 0L;

    @Override
    public List<consultation> findAll() {
        return new ArrayList<>(db);
    }

    @Override
    public consultation findById(Long id) {
        return db.stream()
                .filter(c -> Objects.equals(c.getIdEntite(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(consultation c) {
        synchronized (ConsultationRepositoryImpl.class) {
            idCounter++;
            c.setIdEntite(idCounter);
        }
        c.setDateCreation(LocalDate.now());
        if (c.getCreePar() == null) c.setCreePar("FILE_SYSTEM");

        db.add(c);
    }

    @Override
    public void update(consultation updatedConsultation) {
        consultation existingConsultation = findById(updatedConsultation.getIdEntite());
        if (existingConsultation != null) {
            existingConsultation.setIdConsultation(updatedConsultation.getIdConsultation());
            existingConsultation.setDate(updatedConsultation.getDate());
            existingConsultation.setStatut(updatedConsultation.getStatut());
            existingConsultation.setObservationMedecin(updatedConsultation.getObservationMedecin());

            existingConsultation.setDateDerniereModification(LocalDateTime.now());
            if (existingConsultation.getModifierPar() == null) existingConsultation.setModifierPar("FILE_SYSTEM");
        }
    }

    @Override
    public void delete(consultation c) {
        if (c != null) {
            deleteById(c.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        db.removeIf(c -> Objects.equals(c.getIdEntite(), id));
    }

    @Override
    public Optional<consultation> findByIdConsultation(Long idConsultation) {
        return db.stream()
                .filter(c -> Objects.equals(c.getIdConsultation(), idConsultation))
                .findFirst();
    }
}