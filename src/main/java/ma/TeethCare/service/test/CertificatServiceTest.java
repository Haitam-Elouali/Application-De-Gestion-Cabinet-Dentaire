package ma.TeethCare.service.test;

import ma.TeethCare.entities.certificat.certificat;
import ma.TeethCare.repository.api.CertificatRepository;
import ma.TeethCare.service.modules.api.certificatService;
import ma.TeethCare.service.modules.impl.certificatServiceImpl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.sql.SQLException;

/**
 * @author CHOUKHAIRI Noureddine
 * @date 2025-12-14
 */

public class CertificatServiceTest {

    static class CertificatRepositoryStub implements CertificatRepository {
        private Map<Long, certificat> data = new HashMap<>();
        private long idCounter = 1;

        @Override
        public List<certificat> findAll() throws SQLException {
            return new ArrayList<>(data.values());
        }

        @Override
        public certificat findById(Long id) {
            return data.get(id);
        }

        @Override
        public void create(certificat entity) {
            if (entity.getIdCertif() == null) {
                entity.setIdCertif(idCounter++);
            }
            data.put(entity.getIdCertif(), entity);
        }

        @Override
        public void update(certificat entity) {
            data.put(entity.getIdCertif(), entity);
        }

        @Override
        public void delete(certificat entity) {
            data.remove(entity.getIdCertif());
        }

        @Override
        public void deleteById(Long id) {
            data.remove(id);
        }
    }

    public static void main(String[] args) {
        try {
            CertificatRepositoryStub repo = new CertificatRepositoryStub();
            certificatService service = new certificatServiceImpl(repo);

            testCreate(service);
            testFindById(service);
            testFindAll(service);
            testUpdate(service);
            testDelete(service);
            testExists(service);
            testCount(service);
            
            System.out.println("All CertificatService tests passed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testCreate(certificatService service) throws Exception {
        System.out.println("Testing Create...");
        certificat c = certificat.builder()
            .noteMedecin("Repos 3 jours")
            .duree(3)
            .dateDebut(LocalDate.now())
            .build();
        certificat created = service.create(c);
        if (created.getIdCertif() == null) throw new RuntimeException("Create failed: ID is null");
        System.out.println("Create passed.");
    }

    public static void testFindById(certificatService service) throws Exception {
        System.out.println("Testing FindById...");
        certificat c = certificat.builder().noteMedecin("Test ID").build();
        c = service.create(c);
        Optional<certificat> found = service.findById(c.getIdCertif());
        if (!found.isPresent()) throw new RuntimeException("FindById failed: not found");
        System.out.println("FindById passed.");
    }

    public static void testFindAll(certificatService service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();
        service.create(certificat.builder().noteMedecin("All 1").build());
        if (service.findAll().size() != initialCount + 1) throw new RuntimeException("FindAll failed: count mismatch");
        System.out.println("FindAll passed.");
    }

    public static void testUpdate(certificatService service) throws Exception {
        System.out.println("Testing Update...");
        certificat c = certificat.builder().noteMedecin("Old Note").build();
        c = service.create(c);
        c.setNoteMedecin("New Note");
        certificat updated = service.update(c);
        if (!updated.getNoteMedecin().equals("New Note")) throw new RuntimeException("Update failed: value mismatch");
        System.out.println("Update passed.");
    }

    public static void testDelete(certificatService service) throws Exception {
        System.out.println("Testing Delete...");
        certificat c = certificat.builder().noteMedecin("Delete Me").build();
        c = service.create(c);
        Long id = c.getIdCertif();
        service.delete(id);
        if (service.exists(id)) throw new RuntimeException("Delete failed: still exists");
        System.out.println("Delete passed.");
    }

    public static void testExists(certificatService service) throws Exception {
        System.out.println("Testing Exists...");
        certificat c = certificat.builder().noteMedecin("Exists").build();
        c = service.create(c);
        if (!service.exists(c.getIdCertif())) throw new RuntimeException("Exists failed: returned false");
        System.out.println("Exists passed.");
    }

    public static void testCount(certificatService service) throws Exception {
        System.out.println("Testing Count...");
        long count = service.count();
        if (count < 0) throw new RuntimeException("Count failed: negative");
        System.out.println("Count passed.");
    }
}
