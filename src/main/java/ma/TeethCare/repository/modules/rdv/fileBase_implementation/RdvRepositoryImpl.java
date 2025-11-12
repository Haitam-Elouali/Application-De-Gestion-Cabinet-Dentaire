package ma.TeethCare.repository.modules.rdv.fileBase_implementation;

import ma.TeethCare.entities.rdv.rdv;
import ma.TeethCare.entities.enums.Statut;
import ma.TeethCare.repository.modules.rdv.api.RdvRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class RdvRepositoryImpl implements RdvRepository {

    private static final List<rdv> db = new ArrayList<>();
    private static long idCounter = 0L;

    @Override
    public List<rdv> findAll() {
        return new ArrayList<>(db);
    }

    @Override
    public rdv findById(Long id) {
        return db.stream()
                .filter(r -> Objects.equals(r.getIdEntite(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(rdv r) {
        synchronized (RdvRepositoryImpl.class) {
            idCounter++;
            r.setIdEntite(idCounter);
        }
        r.setDateCreation(LocalDate.now());
        if (r.getCreePar() == null) r.setCreePar("FILE_SYSTEM");

        db.add(r);
    }

    @Override
    public void update(rdv updatedRdv) {
        rdv existingRdv = findById(updatedRdv.getIdEntite());
        if (existingRdv != null) {
            existingRdv.setIdRDV(updatedRdv.getIdRDV());
            existingRdv.setDate(updatedRdv.getDate());
            existingRdv.setHeure(updatedRdv.getHeure());
            existingRdv.setMotif(updatedRdv.getMotif());
            existingRdv.setStatut(updatedRdv.getStatut());
            existingRdv.setNoteMedecin(updatedRdv.getNoteMedecin());

            existingRdv.setDateDerniereModification(LocalDateTime.now());
            if (existingRdv.getModifierPar() == null) existingRdv.setModifierPar("FILE_SYSTEM");
        }
    }

    @Override
    public void delete(rdv r) {
        if (r != null) {
            deleteById(r.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        db.removeIf(r -> Objects.equals(r.getIdEntite(), id));
    }

    @Override
    public Optional<rdv> findByIdRDV(Long idRDV) {
        return db.stream()
                .filter(r -> Objects.equals(r.getIdRDV(), idRDV))
                .findFirst();
    }

    @Override
    public List<rdv> findByDate(LocalDate date) {
        return db.stream()
                .filter(r -> Objects.equals(r.getDate(), date))
                .collect(Collectors.toList());
    }
}
