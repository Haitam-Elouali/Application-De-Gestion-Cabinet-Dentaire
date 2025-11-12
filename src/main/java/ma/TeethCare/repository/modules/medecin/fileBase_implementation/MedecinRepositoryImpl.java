package ma.TeethCare.repository.modules.medecin.fileBase_implementation;

import ma.TeethCare.entities.medecin.medecin;
import ma.TeethCare.repository.modules.medecin.api.MedecinRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MedecinRepositoryImpl implements MedecinRepository {

    private static final List<medecin> db = new ArrayList<>();
    private static long idCounter = 0L;

    @Override
    public List<medecin> findAll() {
        return new ArrayList<>(db);
    }

    @Override
    public medecin findById(Long id) {
        return db.stream()
                .filter(m -> Objects.equals(m.getIdEntite(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(medecin m) {
        synchronized (MedecinRepositoryImpl.class) {
            idCounter++;
            m.setIdEntite(idCounter);
        }
        m.setDateCreation(LocalDate.now());
        if (m.getCreePar() == null) m.setCreePar("FILE_SYSTEM");

        db.add(m);
    }

    @Override
    public void update(medecin updatedMedecin) {
        medecin existingMedecin = findById(updatedMedecin.getIdEntite());
        if (existingMedecin != null) {

            existingMedecin.setSpecialite(updatedMedecin.getSpecialite());

            existingMedecin.setSalaire(updatedMedecin.getSalaire());
            existingMedecin.setPrime(updatedMedecin.getPrime());
            existingMedecin.setDateRecrutement(updatedMedecin.getDateRecrutement());
            existingMedecin.setSoldeConge(updatedMedecin.getSoldeConge());

            existingMedecin.setIdUser(updatedMedecin.getIdUser());
            existingMedecin.setNom(updatedMedecin.getNom());
            existingMedecin.setEmail(updatedMedecin.getEmail());
            existingMedecin.setAdresse(updatedMedecin.getAdresse());
            existingMedecin.setCin(updatedMedecin.getCin());
            existingMedecin.setTel(updatedMedecin.getTel());
            existingMedecin.setSexe(updatedMedecin.getSexe());
            existingMedecin.setLogin(updatedMedecin.getLogin());
            existingMedecin.setMotDePasse(updatedMedecin.getMotDePasse());
            existingMedecin.setLastLoginDate(updatedMedecin.getLastLoginDate());
            existingMedecin.setDateNaissance(updatedMedecin.getDateNaissance());

            existingMedecin.setDateDerniereModification(LocalDateTime.now());
            if (existingMedecin.getModifierPar() == null) existingMedecin.setModifierPar("FILE_SYSTEM");
        }
    }

    @Override
    public void delete(medecin m) {
        if (m != null) {
            deleteById(m.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        db.removeIf(m -> Objects.equals(m.getIdEntite(), id));
    }

    @Override
    public Optional<medecin> findByCin(String cin) {
        return db.stream()
                .filter(m -> m.getCin().equalsIgnoreCase(cin))
                .findFirst();
    }

    @Override
    public Optional<medecin> findByEmail(String email) {
        return db.stream()
                .filter(m -> m.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }
}