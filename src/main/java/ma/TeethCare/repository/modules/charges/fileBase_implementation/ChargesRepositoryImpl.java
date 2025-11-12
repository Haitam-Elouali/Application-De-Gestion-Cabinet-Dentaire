package ma.TeethCare.repository.modules.charges.fileBase_implementation;

import ma.TeethCare.entities.charges.charges;
import ma.TeethCare.repository.modules.charges.api.ChargesRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ChargesRepositoryImpl implements ChargesRepository {

    private static final List<charges> db = new ArrayList<>();
    private static long idCounter = 0L;

    @Override
    public List<charges> findAll() {
        return new ArrayList<>(db);
    }

    @Override
    public charges findById(Long id) {
        return db.stream()
                .filter(c -> Objects.equals(c.getIdEntite(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(charges c) {
        synchronized (ChargesRepositoryImpl.class) {
            idCounter++;
            c.setIdEntite(idCounter);
        }
        c.setDateCreation(LocalDate.now());
        if (c.getCreePar() == null) c.setCreePar("FILE_SYSTEM");

        db.add(c);
    }

    @Override
    public void update(charges updatedCharge) {
        charges existingCharge = findById(updatedCharge.getIdEntite());
        if (existingCharge != null) {
            existingCharge.setTitre(updatedCharge.getTitre());
            existingCharge.setDescription(updatedCharge.getDescription());
            existingCharge.setMontant(updatedCharge.getMontant());
            existingCharge.setDate(updatedCharge.getDate());

            existingCharge.setDateDerniereModification(LocalDateTime.now());
            if (existingCharge.getModifierPar() == null) existingCharge.setModifierPar("FILE_SYSTEM");
        }
    }

    @Override
    public void delete(charges c) {
        if (c != null) {
            deleteById(c.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        db.removeIf(c -> Objects.equals(c.getIdEntite(), id));
    }

    @Override
    public Optional<charges> findByTitre(String titre) {
        return db.stream()
                .filter(c -> c.getTitre().equalsIgnoreCase(titre))
                .findFirst();
    }
}
