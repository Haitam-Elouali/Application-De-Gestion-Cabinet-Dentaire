package ma.TeethCare.repository.modules.revenues.fileBase_implementation;

import ma.TeethCare.entities.revenues.revenues;
import ma.TeethCare.repository.modules.revenues.api.RevenuesRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class RevenuesRepositoryImpl implements RevenuesRepository {

    private static final List<revenues> db = new ArrayList<>();
    private static long idCounter = 0L;

    @Override
    public List<revenues> findAll() {
        return new ArrayList<>(db);
    }

    @Override
    public revenues findById(Long id) {
        return db.stream()
                .filter(r -> Objects.equals(r.getIdEntite(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(revenues r) {
        synchronized (RevenuesRepositoryImpl.class) {
            idCounter++;
            r.setIdEntite(idCounter);
        }
        r.setDateCreation(LocalDate.now());
        if (r.getCreePar() == null) r.setCreePar("FILE_SYSTEM");

        db.add(r);
    }

    @Override
    public void update(revenues updatedRevenues) {
        revenues existingRevenues = findById(updatedRevenues.getIdEntite());
        if (existingRevenues != null) {
            existingRevenues.setTitre(updatedRevenues.getTitre());
            existingRevenues.setDescription(updatedRevenues.getDescription());
            existingRevenues.setMontant(updatedRevenues.getMontant());
            existingRevenues.setDate(updatedRevenues.getDate());

            existingRevenues.setDateDerniereModification(LocalDateTime.now());
            if (existingRevenues.getModifierPar() == null) existingRevenues.setModifierPar("FILE_SYSTEM");
        }
    }

    @Override
    public void delete(revenues r) {
        if (r != null) {
            deleteById(r.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        db.removeIf(r -> Objects.equals(r.getIdEntite(), id));
    }

    @Override
    public Optional<revenues> findByTitre(String titre) {
        return db.stream()
                .filter(r -> r.getTitre().equalsIgnoreCase(titre))
                .findFirst();
    }

    @Override
    public List<revenues> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return db.stream()
                .filter(r -> r.getDate() != null &&
                        (r.getDate().isEqual(startDate) || r.getDate().isAfter(startDate)) &&
                        (r.getDate().isEqual(endDate) || r.getDate().isBefore(endDate)))
                .collect(Collectors.toList());
    }
}