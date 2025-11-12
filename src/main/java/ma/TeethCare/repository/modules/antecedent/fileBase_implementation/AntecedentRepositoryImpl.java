package ma.TeethCare.repository.modules.antecedent.fileBase_implementation;

import ma.TeethCare.entities.antecedent.antecedent;
import ma.TeethCare.entities.enums.niveauDeRisque;
import ma.TeethCare.repository.modules.antecedent.api.AntecedentRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AntecedentRepositoryImpl implements AntecedentRepository {

    private static final List<antecedent> db = new ArrayList<>();
    private static long idCounter = 0L;

    @Override
    public List<antecedent> findAll() {
        return new ArrayList<>(db);
    }

    @Override
    public antecedent findById(Long id) {
        return db.stream()
                .filter(a -> Objects.equals(a.getIdEntite(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(antecedent a) {
        synchronized (AntecedentRepositoryImpl.class) {
            idCounter++;
            a.setIdEntite(idCounter);
        }
        a.setDateCreation(LocalDate.now());
        if (a.getCreePar() == null) a.setCreePar("FILE_SYSTEM");

        db.add(a);
    }

    @Override
    public void update(antecedent updatedAntecedent) {
        antecedent existingAntecedent = findById(updatedAntecedent.getIdEntite());
        if (existingAntecedent != null) {
            existingAntecedent.setId_Antecedent(updatedAntecedent.getId_Antecedent());
            existingAntecedent.setNom(updatedAntecedent.getNom());
            existingAntecedent.setCategorie(updatedAntecedent.getCategorie());
            existingAntecedent.setNiveauRisque(updatedAntecedent.getNiveauRisque());

            existingAntecedent.setDateDerniereModification(LocalDateTime.now());
            if (existingAntecedent.getModifierPar() == null) existingAntecedent.setModifierPar("FILE_SYSTEM");
        }
    }

    @Override
    public void delete(antecedent a) {
        if (a != null) {
            deleteById(a.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        db.removeIf(a -> Objects.equals(a.getIdEntite(), id));
    }

    @Override
    public List<antecedent> findByCategorie(String categorie) {
        return db.stream()
                .filter(a -> a.getCategorie().equalsIgnoreCase(categorie))
                .collect(Collectors.toList());
    }
}