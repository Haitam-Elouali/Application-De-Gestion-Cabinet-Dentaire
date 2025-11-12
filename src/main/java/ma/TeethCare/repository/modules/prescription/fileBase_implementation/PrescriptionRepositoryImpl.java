package ma.TeethCare.repository.modules.prescription.fileBase_implementation;

import ma.TeethCare.entities.prescription.prescription;
import ma.TeethCare.repository.modules.prescription.api.PrescriptionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PrescriptionRepositoryImpl implements PrescriptionRepository {

    private static final List<prescription> db = new ArrayList<>();
    private static long idCounter = 0L;

    @Override
    public List<prescription> findAll() {
        return new ArrayList<>(db);
    }

    @Override
    public prescription findById(Long id) {
        return db.stream()
                .filter(p -> Objects.equals(p.getIdEntite(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(prescription p) {
        synchronized (PrescriptionRepositoryImpl.class) {
            idCounter++;
            p.setIdEntite(idCounter);
        }
        p.setDateCreation(LocalDate.now());
        if (p.getCreePar() == null) p.setCreePar("FILE_SYSTEM");

        db.add(p);
    }

    @Override
    public void update(prescription updatedPrescription) {
        prescription existingPrescription = findById(updatedPrescription.getIdEntite());
        if (existingPrescription != null) {
            existingPrescription.setIdPr(updatedPrescription.getIdPr());
            existingPrescription.setQuantite(updatedPrescription.getQuantite());
            existingPrescription.setFrequence(updatedPrescription.getFrequence());
            existingPrescription.setDureeEnjours(updatedPrescription.getDureeEnjours());

            existingPrescription.setDateDerniereModification(LocalDateTime.now());
            if (existingPrescription.getModifierPar() == null) existingPrescription.setModifierPar("FILE_SYSTEM");
        }
    }

    @Override
    public void delete(prescription p) {
        if (p != null) {
            deleteById(p.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        db.removeIf(p -> Objects.equals(p.getIdEntite(), id));
    }

    @Override
    public Optional<prescription> findByIdPr(Long idPr) {
        return db.stream()
                .filter(p -> Objects.equals(p.getIdPr(), idPr))
                .findFirst();
    }
}