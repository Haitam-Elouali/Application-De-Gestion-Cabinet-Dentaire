package ma.TeethCare.repository.modules.secretaire.fileBase_implementation;

import ma.TeethCare.entities.secretaire.secretaire;
import ma.TeethCare.entities.enums.Sexe;
import ma.TeethCare.repository.modules.secretaire.api.SecretaireRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class secretaireRepositoryImpl implements SecretaireRepository {

    private static final List<secretaire> db = new ArrayList<>();
    private static long idCounter = 0L;

    @Override
    public List<secretaire> findAll() {
        return new ArrayList<>(db);
    }

    @Override
    public secretaire findById(Long id) {
        return db.stream()
                .filter(s -> Objects.equals(s.getIdEntite(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(secretaire s) {
        synchronized (secretaireRepositoryImpl.class) {
            idCounter++;
            s.setIdEntite(idCounter);
        }
        s.setDateCreation(LocalDate.now());
        if (s.getCreePar() == null) s.setCreePar("FILE_SYSTEM");

        db.add(s);
    }

    @Override
    public void update(secretaire updatedSecretaire) {
        secretaire existingSecretaire = findById(updatedSecretaire.getIdEntite());
        if (existingSecretaire != null) {
            existingSecretaire.setNumCNSS(updatedSecretaire.getNumCNSS());
            existingSecretaire.setCommission(updatedSecretaire.getCommission());

            existingSecretaire.setSalaire(updatedSecretaire.getSalaire());
            existingSecretaire.setPrime(updatedSecretaire.getPrime());
            existingSecretaire.setDateRecrutement(updatedSecretaire.getDateRecrutement());
            existingSecretaire.setSoldeConge(updatedSecretaire.getSoldeConge());

            existingSecretaire.setIdUser(updatedSecretaire.getIdUser());
            existingSecretaire.setNom(updatedSecretaire.getNom());
            existingSecretaire.setEmail(updatedSecretaire.getEmail());
            existingSecretaire.setAdresse(updatedSecretaire.getAdresse());
            existingSecretaire.setCin(updatedSecretaire.getCin());
            existingSecretaire.setTel(updatedSecretaire.getTel());
            existingSecretaire.setSexe(updatedSecretaire.getSexe());
            existingSecretaire.setLogin(updatedSecretaire.getLogin());
            existingSecretaire.setMotDePasse(updatedSecretaire.getMotDePasse());
            existingSecretaire.setLastLoginDate(updatedSecretaire.getLastLoginDate());
            existingSecretaire.setDateNaissance(updatedSecretaire.getDateNaissance());

            existingSecretaire.setDateDerniereModification(LocalDateTime.now());
            if (existingSecretaire.getModifierPar() == null) existingSecretaire.setModifierPar("FILE_SYSTEM");
        }
    }

    @Override
    public void delete(secretaire s) {
        if (s != null) {
            deleteById(s.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        db.removeIf(s -> Objects.equals(s.getIdEntite(), id));
    }

    @Override
    public Optional<secretaire> findByNumCNSS(String numCNSS) {
        return db.stream()
                .filter(s -> s.getNumCNSS().equalsIgnoreCase(numCNSS))
                .findFirst();
    }

    @Override
    public Optional<secretaire> findByCin(String cin) {
        return db.stream()
                .filter(s -> s.getCin().equalsIgnoreCase(cin))
                .findFirst();
    }
}