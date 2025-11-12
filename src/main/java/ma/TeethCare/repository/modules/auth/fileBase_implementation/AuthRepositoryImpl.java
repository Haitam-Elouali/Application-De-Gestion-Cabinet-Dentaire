package ma.TeethCare.repository.modules.auth.fileBase_implementation;

import ma.TeethCare.entities.auth.auth;
import ma.TeethCare.repository.modules.auth.api.AuthRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AuthRepositoryImpl implements AuthRepository {

    private static final List<auth> db = new ArrayList<>();
    private static long idCounter = 0L;

    @Override
    public List<auth> findAll() {
        return new ArrayList<>(db);
    }

    @Override
    public auth findById(Long id) {
        return db.stream()
                .filter(u -> Objects.equals(u.getIdEntite(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(auth u) {
        synchronized (AuthRepositoryImpl.class) {
            idCounter++;
            u.setIdEntite(idCounter);
        }
        u.setDateCreation(LocalDate.now());
        if (u.getCreePar() == null) u.setCreePar("FILE_SYSTEM");

        db.add(u);
    }

    @Override
    public void update(auth updatedAuth) {
        auth existingAuth = findById(updatedAuth.getIdEntite());
        if (existingAuth != null) {
            existingAuth.setEmail(updatedAuth.getEmail());
            existingAuth.setMotDePasse(updatedAuth.getMotDePasse());

            existingAuth.setDateDerniereModification(LocalDateTime.now());
            if (existingAuth.getModifierPar() == null) existingAuth.setModifierPar("FILE_SYSTEM");
        }
    }

    @Override
    public void delete(auth u) {
        if (u != null) {
            deleteById(u.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        db.removeIf(u -> Objects.equals(u.getIdEntite(), id));
    }

    @Override
    public Optional<auth> findByEmail(String email) {
        return db.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }
}