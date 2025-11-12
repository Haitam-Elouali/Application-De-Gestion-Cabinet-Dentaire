package ma.TeethCare.repository.modules.ordonnance.fileBase_implementation;


import ma.TeethCare.entities.ordonnance.ordonnance;
import ma.TeethCare.repository.modules.ordonnance.api.OrdonnanceRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class OrdonnanceRepositoryImpl implements OrdonnanceRepository {

    private static final List<ordonnance> db = new ArrayList<>();
    private static long idCounter = 0L;

    @Override
    public List<ordonnance> findAll() {
        return new ArrayList<>(db);
    }

    @Override
    public ordonnance findById(Long id) {
        return db.stream()
                .filter(o -> Objects.equals(o.getIdEntite(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(ordonnance o) {
        synchronized (OrdonnanceRepositoryImpl.class) {
            idCounter++;
            o.setIdEntite(idCounter);
        }
        o.setDateCreation(LocalDate.now());
        if (o.getCreePar() == null) o.setCreePar("FILE_SYSTEM");

        db.add(o);
    }

    @Override
    public void update(ordonnance updatedOrdonnance) {
        ordonnance existingOrdonnance = findById(updatedOrdonnance.getIdEntite());
        if (existingOrdonnance != null) {
            existingOrdonnance.setIdOrd(updatedOrdonnance.getIdOrd());
            existingOrdonnance.setDate(updatedOrdonnance.getDate());

            existingOrdonnance.setDateDerniereModification(LocalDateTime.now());
            if (existingOrdonnance.getModifierPar() == null) existingOrdonnance.setModifierPar("FILE_SYSTEM");
        }
    }

    @Override
    public void delete(ordonnance o) {
        if (o != null) {
            deleteById(o.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        db.removeIf(o -> Objects.equals(o.getIdEntite(), id));
    }

    @Override
    public Optional<ordonnance> findByIdOrd(Long idOrd) {
        return db.stream()
                .filter(o -> Objects.equals(o.getIdOrd(), idOrd))
                .findFirst();
    }
}