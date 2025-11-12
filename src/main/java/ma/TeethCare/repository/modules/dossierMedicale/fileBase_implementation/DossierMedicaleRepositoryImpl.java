package ma.TeethCare.repository.modules.dossierMedicale.fileBase_implementation;


import ma.TeethCare.entities.dossierMedicale.dossierMedicale;
import ma.TeethCare.repository.modules.dossierMedicale.api.DossierMedicaleRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DossierMedicaleRepositoryImpl implements DossierMedicaleRepository {

    private static final List<dossierMedicale> db = new ArrayList<>();
    private static long idCounter = 0L;

    @Override
    public List<dossierMedicale> findAll() {
        return new ArrayList<>(db);
    }

    @Override
    public dossierMedicale findById(Long id) {
        return db.stream()
                .filter(d -> Objects.equals(d.getIdEntite(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(dossierMedicale d) {
        synchronized (DossierMedicaleRepositoryImpl.class) {
            idCounter++;
            d.setIdEntite(idCounter);
        }
        d.setDateCreation(LocalDate.now());
        if (d.getCreePar() == null) d.setCreePar("FILE_SYSTEM");

        db.add(d);
    }

    @Override
    public void update(dossierMedicale updatedDM) {
        dossierMedicale existingDM = findById(updatedDM.getIdEntite());
        if (existingDM != null) {
            existingDM.setIdDM(updatedDM.getIdDM());
            existingDM.setDateDeCreation(updatedDM.getDateDeCreation());

            existingDM.setDateDerniereModification(LocalDateTime.now());
            if (existingDM.getModifierPar() == null) existingDM.setModifierPar("FILE_SYSTEM");
        }
    }

    @Override
    public void delete(dossierMedicale d) {
        if (d != null) {
            deleteById(d.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        db.removeIf(d -> Objects.equals(d.getIdEntite(), id));
    }

    @Override
    public Optional<dossierMedicale> findByIdDM(Long idDM) {
        return db.stream()
                .filter(d -> Objects.equals(d.getIdDM(), idDM))
                .findFirst();
    }
}