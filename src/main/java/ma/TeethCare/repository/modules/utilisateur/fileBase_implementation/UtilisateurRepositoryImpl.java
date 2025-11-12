package ma.TeethCare.repository.modules.utilisateur.fileBase_implementation;

import ma.TeethCare.entities.utilisateur.utilisateur;
import ma.TeethCare.repository.modules.utilisateur.api.UtilisateurRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UtilisateurRepositoryImpl implements UtilisateurRepository {

    private static final List<utilisateur> db = new ArrayList<>();
    private static long idCounter = 0L;

    @Override
    public List<utilisateur> findAll() {
        return new ArrayList<>(db);
    }

    @Override
    public utilisateur findById(Long id) {
        return db.stream()
                .filter(u -> Objects.equals(u.getIdEntite(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(utilisateur u) {
        synchronized (UtilisateurRepositoryImpl.class) {
            idCounter++;
            u.setIdEntite(idCounter);
        }
        u.setDateCreation(LocalDate.now());
        if (u.getCreePar() == null) u.setCreePar("FILE_SYSTEM");

        db.add(u);
    }

    @Override
    public void update(utilisateur updatedUser) {
        utilisateur existingUser = findById(updatedUser.getIdEntite());
        if (existingUser != null) {
            existingUser.setIdUser(updatedUser.getIdUser());
            existingUser.setNom(updatedUser.getNom());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setAdresse(updatedUser.getAdresse());
            existingUser.setCin(updatedUser.getCin());
            existingUser.setTel(updatedUser.getTel());
            existingUser.setSexe(updatedUser.getSexe());
            existingUser.setLogin(updatedUser.getLogin());
            existingUser.setMotDePasse(updatedUser.getMotDePasse());
            existingUser.setLastLoginDate(updatedUser.getLastLoginDate());
            existingUser.setDateNaissance(updatedUser.getDateNaissance());

            existingUser.setDateDerniereModification(LocalDateTime.now());
            if (existingUser.getModifierPar() == null) existingUser.setModifierPar("FILE_SYSTEM");
        }
    }

    @Override
    public void delete(utilisateur u) {
        if (u != null) {
            deleteById(u.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        db.removeIf(u -> Objects.equals(u.getIdEntite(), id));
    }

    @Override
    public Optional<utilisateur> findByEmail(String email) {
        return db.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public Optional<utilisateur> findByLogin(String login) {
        return db.stream()
                .filter(u -> u.getLogin().equalsIgnoreCase(login))
                .findFirst();
    }
}