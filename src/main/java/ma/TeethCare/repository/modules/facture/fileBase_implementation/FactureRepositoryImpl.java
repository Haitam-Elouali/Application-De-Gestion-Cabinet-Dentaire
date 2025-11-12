package ma.TeethCare.repository.modules.facture.fileBase_implementation;

import ma.TeethCare.entities.facture.facture;
import ma.TeethCare.entities.enums.Statut;
import ma.TeethCare.repository.modules.facture.api.FactureRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FactureRepositoryImpl implements FactureRepository {

    private static final List<facture> db = new ArrayList<>();
    private static long idCounter = 0L;

    @Override
    public List<facture> findAll() {
        return new ArrayList<>(db);
    }

    @Override
    public facture findById(Long id) {
        return db.stream()
                .filter(f -> Objects.equals(f.getIdEntite(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(facture f) {
        synchronized (FactureRepositoryImpl.class) {
            idCounter++;
            f.setIdEntite(idCounter);
        }
        f.setDateCreation(LocalDate.now());
        if (f.getCreePar() == null) f.setCreePar("FILE_SYSTEM");

        db.add(f);
    }

    @Override
    public void update(facture updatedFacture) {
        facture existingFacture = findById(updatedFacture.getIdEntite());
        if (existingFacture != null) {
            existingFacture.setIdFacture(updatedFacture.getIdFacture());
            existingFacture.setTotaleFacture(updatedFacture.getTotaleFacture());
            existingFacture.setTotalPaye(updatedFacture.getTotalPaye());
            existingFacture.setReste(updatedFacture.getReste());
            existingFacture.setStatut(updatedFacture.getStatut());
            existingFacture.setDateFacture(updatedFacture.getDateFacture());

            existingFacture.setDateDerniereModification(LocalDateTime.now());
            if (existingFacture.getModifierPar() == null) existingFacture.setModifierPar("FILE_SYSTEM");
        }
    }

    @Override
    public void delete(facture f) {
        if (f != null) {
            deleteById(f.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        db.removeIf(f -> Objects.equals(f.getIdEntite(), id));
    }

    @Override
    public Optional<facture> findByIdFacture(Long idFacture) {
        return db.stream()
                .filter(f -> Objects.equals(f.getIdFacture(), idFacture))
                .findFirst();
    }
}