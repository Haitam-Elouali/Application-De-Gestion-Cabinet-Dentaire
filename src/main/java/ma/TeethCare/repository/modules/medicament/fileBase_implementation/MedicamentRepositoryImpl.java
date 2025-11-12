package ma.TeethCare.repository.modules.medicament.fileBase_implementation;

import ma.TeethCare.entities.medicaments.medicaments;
import ma.TeethCare.repository.modules.medicament.api.MedicamentRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MedicamentRepositoryImpl implements MedicamentRepository {

    private static final List<medicaments> db = new ArrayList<>();
    private static long idCounter = 0L;

    @Override
    public List<medicaments> findAll() {
        return new ArrayList<>(db);
    }

    @Override
    public medicaments findById(Long id) {
        return db.stream()
                .filter(m -> Objects.equals(m.getIdEntite(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(medicaments m) {
        synchronized (MedicamentRepositoryImpl.class) {
            idCounter++;
            m.setIdEntite(idCounter);
        }
        m.setDateCreation(LocalDate.now());
        if (m.getCreePar() == null) m.setCreePar("FILE_SYSTEM");

        db.add(m);
    }

    @Override
    public void update(medicaments updatedMedicament) {
        medicaments existingMedicament = findById(updatedMedicament.getIdEntite());
        if (existingMedicament != null) {
            existingMedicament.setIdMed(updatedMedicament.getIdMed());
            existingMedicament.setNom(updatedMedicament.getNom());
            existingMedicament.setLaboratoire(updatedMedicament.getLaboratoire());
            existingMedicament.setType(updatedMedicament.getType());
            existingMedicament.setRemboursable(updatedMedicament.isRemboursable());
            existingMedicament.setPrixUnitaire(updatedMedicament.getPrixUnitaire());
            existingMedicament.setDescription(updatedMedicament.getDescription());

            existingMedicament.setDateDerniereModification(LocalDateTime.now());
            if (existingMedicament.getModifierPar() == null) existingMedicament.setModifierPar("FILE_SYSTEM");
        }
    }

    @Override
    public void delete(medicaments m) {
        if (m != null) {
            deleteById(m.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        db.removeIf(m -> Objects.equals(m.getIdEntite(), id));
    }

    @Override
    public Optional<medicaments> findByIdMed(Long idMed) {
        return db.stream()
                .filter(m -> Objects.equals(m.getIdMed(), idMed))
                .findFirst();
    }

    @Override
    public Optional<medicaments> findByNom(String nom) {
        return db.stream()
                .filter(m -> m.getNom().equalsIgnoreCase(nom))
                .findFirst();
    }
}