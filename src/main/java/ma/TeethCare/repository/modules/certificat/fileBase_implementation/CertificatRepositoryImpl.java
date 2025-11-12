package ma.TeethCare.repository.modules.certificat.fileBase_implementation;


import ma.TeethCare.entities.certificat.certificat;
import ma.TeethCare.repository.modules.certificat.api.CertificatRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CertificatRepositoryImpl implements CertificatRepository {

    private static final List<certificat> db = new ArrayList<>();
    private static long idCounter = 0L;

    @Override
    public List<certificat> findAll() {
        return new ArrayList<>(db);
    }

    @Override
    public certificat findById(Long id) {
        return db.stream()
                .filter(c -> Objects.equals(c.getIdEntite(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(certificat c) {
        synchronized (CertificatRepositoryImpl.class) {
            idCounter++;
            c.setIdEntite(idCounter);
        }
        c.setDateCreation(LocalDate.now());
        if (c.getCreePar() == null) c.setCreePar("FILE_SYSTEM");

        db.add(c);
    }

    @Override
    public void update(certificat updatedCertif) {
        certificat existingCertif = findById(updatedCertif.getIdEntite());
        if (existingCertif != null) {
            existingCertif.setIdCertif(updatedCertif.getIdCertif());
            existingCertif.setDateDebut(updatedCertif.getDateDebut());
            existingCertif.setDateFin(updatedCertif.getDateFin());
            existingCertif.setDureer(updatedCertif.getDureer());
            existingCertif.setNoteMedecin(updatedCertif.getNoteMedecin());

            existingCertif.setDateDerniereModification(LocalDateTime.now());
            if (existingCertif.getModifierPar() == null) existingCertif.setModifierPar("FILE_SYSTEM");
        }
    }

    @Override
    public void delete(certificat c) {
        if (c != null) {
            deleteById(c.getIdEntite());
        }
    }

    @Override
    public void deleteById(Long id) {
        db.removeIf(c -> Objects.equals(c.getIdEntite(), id));
    }

    @Override
    public Optional<certificat> findByIdCertif(Long idCertif) {
        return db.stream()
                .filter(c -> Objects.equals(c.getIdCertif(), idCertif))
                .findFirst();
    }
}
