package ma.TeethCare.repository.modules.interventionMedecin.fileBase_implementation;

import ma.TeethCare.entities.interventionMedecin.interventionMedecin;
import ma.TeethCare.repository.modules.interventionMedecin.api.InterventionMedecinRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class InterventionMedecinRepositoryImpl implements InterventionMedecinRepository {

    private static final List<interventionMedecin> db = new ArrayList<>();
    private static long idCounter = 0L;

    @Override
    public List<interventionMedecin> findAll() {
        return new ArrayList<>(db);
    }

    @Override
    public interventionMedecin findById(Long id) {
        return db.stream()
                .filter(i -> Objects.equals(i.getIdEntite(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(interventionMedecin i) {
        synchronized (InterventionMedecinRepositoryImpl.class) {
            idCounter++;
            i.setIdEntite(idCounter);
        }
        i.setDateCreation(LocalDate.now());
        if (i.getCreePar() == null) i.setCreePar("FILE_SYSTEM");

        db.add(i);
    }

    @Override
    public void update(interventionMedecin updatedIntervention) {
        interventionMedecin existingIntervention = findById(updatedIntervention.getIdEntite());
        if (existingIntervention != null) {
            existingIntervention.setIdIM(updatedIntervention.getIdIM());
            existingIntervention.setPrixDePatient(updatedIntervention.getPrixDePatient());
            existingIntervention.setNumDent(updatedIntervention.getNumDent());

            existingIntervention.setDateDerniereModification(LocalDateTime.now());
            if (existingIntervention.getModifierPar() == null) existingIntervention.setModifierPar("FILE_SYSTEM");
        }
    }

    @Override
    public void delete(interventionMedecin i) {
        if (i != null) {
            deleteById(i.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        db.removeIf(i -> Objects.equals(i.getIdEntite(), id));
    }

    @Override
    public Optional<interventionMedecin> findByIdIM(Long idIM) {
        return db.stream()
                .filter(i -> Objects.equals(i.getIdIM(), idIM))
                .findFirst();
    }
}