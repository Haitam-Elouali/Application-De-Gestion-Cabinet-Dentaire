package ma.TeethCare.repository.modules.situationFinanciere.fileBase_implementation;

import ma.TeethCare.entities.situationFinanciere.situationFinanciere;
import ma.TeethCare.repository.modules.situationFinanciere.api.situationFinanciereRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class situationFinanciereRepositoryImpl implements situationFinanciereRepository {

    private static final List<situationFinanciere> db = new ArrayList<>();
    private static long idCounter = 0L;


    @Override
    public List<situationFinanciere> findAll() {
        return new ArrayList<>(db);
    }

    @Override
    public situationFinanciere findById(Long id) {
        return db.stream()
                .filter(sf -> Objects.equals(sf.getIdEntite(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(situationFinanciere sf) {
        synchronized (situationFinanciereRepositoryImpl.class) {
            idCounter++;
            sf.setIdEntite(idCounter);
        }
        sf.setDateCreation(LocalDate.now());
        if (sf.getCreePar() == null) sf.setCreePar("FILE_SYSTEM");

        db.add(sf);
    }

    @Override
    public void update(situationFinanciere updatedSF) {
        situationFinanciere existingSF = findById(updatedSF.getIdEntite());
        if (existingSF != null) {
            existingSF.setIdSF(updatedSF.getIdSF());
            existingSF.setTotaleDesActes(updatedSF.getTotaleDesActes());
            existingSF.setTotalPaye(updatedSF.getTotalPaye());
            existingSF.setCredit(updatedSF.getCredit());
            existingSF.setStatut(updatedSF.getStatut());
            existingSF.setEnPromo(updatedSF.getEnPromo());

            existingSF.setDateDerniereModification(LocalDateTime.now());
            if (existingSF.getModifierPar() == null) existingSF.setModifierPar("FILE_SYSTEM");
        }
    }

    @Override
    public void delete(situationFinanciere sf) {
        if (sf != null) {
            deleteById(sf.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        db.removeIf(sf -> Objects.equals(sf.getIdEntite(), id));
    }

    @Override
    public Optional<situationFinanciere> findByIdSF(Long idSF) {
        return db.stream()
                .filter(sf -> Objects.equals(sf.getIdSF(), idSF))
                .findFirst();
    }
}