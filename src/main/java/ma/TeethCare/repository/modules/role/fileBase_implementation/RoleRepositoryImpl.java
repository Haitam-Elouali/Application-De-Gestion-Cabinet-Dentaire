package ma.TeethCare.repository.modules.role.fileBase_implementation;

import ma.TeethCare.entities.role.role;
import ma.TeethCare.entities.enums.Libeller;
import ma.TeethCare.repository.modules.role.api.RoleRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class RoleRepositoryImpl implements RoleRepository {

    private static final List<role> db = new ArrayList<>();
    private static long idCounter = 0L;

    @Override
    public List<role> findAll() {
        return new ArrayList<>(db);
    }

    @Override
    public role findById(Long id) {
        return db.stream()
                .filter(r -> Objects.equals(r.getIdEntite(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(role r) {
        synchronized (RoleRepositoryImpl.class) {
            idCounter++;
            r.setIdEntite(idCounter);
        }
        r.setDateCreation(LocalDate.now());
        if (r.getCreePar() == null) r.setCreePar("FILE_SYSTEM");

        db.add(r);
    }

    @Override
    public void update(role updatedRole) {
        role existingRole = findById(updatedRole.getIdEntite());
        if (existingRole != null) {
            existingRole.setIdRole(updatedRole.getIdRole());
            existingRole.setLibeller(updatedRole.getLibeller());

            existingRole.setDateDerniereModification(LocalDateTime.now());
            if (existingRole.getModifierPar() == null) existingRole.setModifierPar("FILE_SYSTEM");
        }
    }

    @Override
    public void delete(role r) {
        if (r != null) {
            deleteById(r.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        db.removeIf(r -> Objects.equals(r.getIdEntite(), id));
    }

    @Override
    public Optional<role> findByIdRole(Long idRole) {
        return db.stream()
                .filter(r -> Objects.equals(r.getIdRole(), idRole))
                .findFirst();
    }

    @Override
    public Optional<role> findByLibeller(Libeller libeller) {
        return db.stream()
                .filter(r -> Objects.equals(r.getLibeller(), libeller))
                .findFirst();
    }
}
