package ma.TeethCare.repository.modules.actes.fileBase_implementation;

import ma.TeethCare.entities.actes.actes;
import ma.TeethCare.repository.modules.actes.api.ActesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ActesRepositoryImpl implements ActesRepository{

    private static final List<actes> db = new ArrayList<>();
    private static final AtomicLong idGenerator = new AtomicLong(0);

    static {
        actes a1 = new actes(101L, "Détartrage", "Préventif", 400.0);
        a1.setIdEntite(idGenerator.incrementAndGet());
        db.add(a1);

        actes a2 = new actes(102L, "Extraction", "Chirurgicale", 1200.0);
        a2.setIdEntite(idGenerator.incrementAndGet());
        db.add(a2);
    }

    @Override
    public List<actes> findAll() {
        return new ArrayList<>(db);
    }

    @Override
    public actes findById(Long id) {
        Optional<actes> acteOpt = db.stream()
                .filter(acte -> Objects.equals(acte.getIdEntite(), id))
                .findFirst();
        return acteOpt.orElse(null);
    }

    @Override
    public void create(actes acte) {
        acte.setIdEntite(idGenerator.incrementAndGet());
        db.add(acte);
    }

    @Override
    public void update(actes acte) {
        actes existingActe = findById(acte.getIdEntite());
        if (existingActe != null) {
            existingActe.setIdIm(acte.getIdIm());
            existingActe.setLibeller(acte.getLibeller());
            existingActe.setCategorie(acte.getCategorie());
            existingActe.setPrixDeBase(acte.getPrixDeBase());
        }
    }

    @Override
    public void delete(actes acte) {
        if (acte != null) {
            deleteById(acte.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        db.removeIf(acte -> Objects.equals(acte.getIdEntite(), id));
    }

    @Override
    public List<actes> findByCategorie(String categorie) {
        return db.stream()
                .filter(acte -> acte.getCategorie().equalsIgnoreCase(categorie))
                .collect(Collectors.toList());
    }
}