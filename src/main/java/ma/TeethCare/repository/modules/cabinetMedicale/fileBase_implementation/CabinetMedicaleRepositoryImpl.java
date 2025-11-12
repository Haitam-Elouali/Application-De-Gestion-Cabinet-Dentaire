package ma.TeethCare.repository.modules.cabinetMedicale.fileBase_implementation;

import ma.TeethCare.entities.cabinetMedicale.cabinetMedicale;
import ma.TeethCare.repository.modules.cabinetMedicale.api.CabinetMedicaleRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CabinetMedicaleRepositoryImpl implements CabinetMedicaleRepository {

    private static final List<cabinetMedicale> db = new ArrayList<>();
    private static long idCounter = 0L;

    @Override
    public List<cabinetMedicale> findAll() {
        return new ArrayList<>(db);
    }

    @Override
    public cabinetMedicale findById(Long id) {
        return db.stream()
                .filter(c -> Objects.equals(c.getIdEntite(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(cabinetMedicale c) {
        synchronized (CabinetMedicaleRepositoryImpl.class) {
            idCounter++;
            c.setIdEntite(idCounter);
        }
        c.setDateCreation(LocalDate.now());
        if (c.getCreePar() == null) c.setCreePar("FILE_SYSTEM");

        db.add(c);
    }

    @Override
    public void update(cabinetMedicale updatedCabinet) {
        cabinetMedicale existingCabinet = findById(updatedCabinet.getIdEntite());
        if (existingCabinet != null) {
            existingCabinet.setIdUser(updatedCabinet.getIdUser());
            existingCabinet.setNom(updatedCabinet.getNom());
            existingCabinet.setEmail(updatedCabinet.getEmail());
            existingCabinet.setLogo(updatedCabinet.getLogo());
            existingCabinet.setCin(updatedCabinet.getCin());
            existingCabinet.setTel1(updatedCabinet.getTel1());
            existingCabinet.setTel2(updatedCabinet.getTel2());
            existingCabinet.setSiteWeb(updatedCabinet.getSiteWeb());
            existingCabinet.setInstagram(updatedCabinet.getInstagram());
            existingCabinet.setFacebook(updatedCabinet.getFacebook());
            existingCabinet.setDescription(updatedCabinet.getDescription());

            existingCabinet.setDateDerniereModification(LocalDateTime.now());
            if (existingCabinet.getModifierPar() == null) existingCabinet.setModifierPar("FILE_SYSTEM");
        }
    }

    @Override
    public void delete(cabinetMedicale c) {
        if (c != null) {
            deleteById(c.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        db.removeIf(c -> Objects.equals(c.getIdEntite(), id));
    }

    @Override
    public Optional<cabinetMedicale> findByEmail(String email) {
        return db.stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }
}