package ma.TeethCare.repository.modules.staff.fileBase_implementation;

import ma.TeethCare.entities.staff.staff;
import ma.TeethCare.repository.modules.staff.api.StaffRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class StaffRepositoryImpl implements StaffRepository {

    private static final List<staff> db = new ArrayList<>();
    private static long idCounter = 0L;

    @Override
    public List<staff> findAll() {
        return new ArrayList<>(db);
    }

    @Override
    public staff findById(Long id) {
        return db.stream()
                .filter(s -> Objects.equals(s.getIdEntite(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(staff s) {
        synchronized (StaffRepositoryImpl.class) {
            idCounter++;
            s.setIdEntite(idCounter);
        }
        s.setDateCreation(LocalDate.now());
        if (s.getCreePar() == null) s.setCreePar("FILE_SYSTEM");

        db.add(s);
    }

    @Override
    public void update(staff updatedStaff) {
        staff existingStaff = findById(updatedStaff.getIdEntite());
        if (existingStaff != null) {

            existingStaff.setSalaire(updatedStaff.getSalaire());
            existingStaff.setPrime(updatedStaff.getPrime());
            existingStaff.setDateRecrutement(updatedStaff.getDateRecrutement());
            existingStaff.setSoldeConge(updatedStaff.getSoldeConge());

            existingStaff.setIdUser(updatedStaff.getIdUser());
            existingStaff.setNom(updatedStaff.getNom());
            existingStaff.setEmail(updatedStaff.getEmail());
            existingStaff.setAdresse(updatedStaff.getAdresse());
            existingStaff.setCin(updatedStaff.getCin());
            existingStaff.setTel(updatedStaff.getTel());
            existingStaff.setSexe(updatedStaff.getSexe());
            existingStaff.setLogin(updatedStaff.getLogin());
            existingStaff.setMotDePasse(updatedStaff.getMotDePasse());
            existingStaff.setLastLoginDate(updatedStaff.getLastLoginDate());
            existingStaff.setDateNaissance(updatedStaff.getDateNaissance());
            existingStaff.setDateDerniereModification(LocalDateTime.now());
            if (existingStaff.getModifierPar() == null) existingStaff.setModifierPar("FILE_SYSTEM");
        }
    }

    @Override
    public void delete(staff s) {
        if (s != null) {
            deleteById(s.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        db.removeIf(s -> Objects.equals(s.getIdEntite(), id));
    }

    @Override
    public Optional<staff> findByEmail(String email) {
        return db.stream()
                .filter(s -> s.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public Optional<staff> findByCin(String cin) {
        return db.stream()
                .filter(s -> s.getCin().equalsIgnoreCase(cin))
                .findFirst();
    }
}