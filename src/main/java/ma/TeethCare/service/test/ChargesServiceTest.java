package ma.TeethCare.service.test;

import ma.TeethCare.entities.charges.charges;
import ma.TeethCare.repository.api.ChargesRepository;
import ma.TeethCare.service.modules.api.chargesService;
import ma.TeethCare.service.modules.impl.chargesServiceImpl;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.sql.SQLException;

/**
 * @author Haitam ELOUALI
 * @date 2025-12-14
 */

public class ChargesServiceTest {

    static class ChargesRepositoryStub implements ChargesRepository {
        private Map<Long, charges> data = new HashMap<>();
        private long idCounter = 1;

        @Override
        public List<charges> findAll() throws SQLException {
            return new ArrayList<>(data.values());
        }

        @Override
        public charges findById(Long id) {
            return data.get(id);
        }

        @Override
        public void create(charges entity) {
            if (entity.getIdCharge() == null) {
                entity.setIdCharge(idCounter++);
            }
            data.put(entity.getIdCharge(), entity);
        }

        @Override
        public void update(charges entity) {
            data.put(entity.getIdCharge(), entity);
        }

        @Override
        public void delete(charges entity) {
            data.remove(entity.getIdCharge());
        }

        @Override
        public void deleteById(Long id) {
            data.remove(id);
        }

        @Override
        public Optional<charges> findByTitre(String titre) {
            return data.values().stream().filter(c -> c.getTitre().equals(titre)).findFirst();
        }
    }

    public static void main(String[] args) {
        try {
            ChargesRepositoryStub repo = new ChargesRepositoryStub();
            chargesService service = new chargesServiceImpl(repo);

            testCreate(service);
            testFindById(service);
            testFindAll(service);
            testUpdate(service);
            testDelete(service);
            testExists(service);
            testCount(service);
            
            System.out.println("All ChargesService tests passed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testCreate(chargesService service) throws Exception {
        System.out.println("Testing Create...");
        charges c = charges.builder()
            .titre("Facture Electrique")
            .montant(500.0)
            .date(LocalDateTime.now())
            .categorie("Utilities")
            .build();
        charges created = service.create(c);
        if (created.getIdCharge() == null) throw new RuntimeException("Create failed: ID is null");
        System.out.println("Create passed.");
    }

    public static void testFindById(chargesService service) throws Exception {
        System.out.println("Testing FindById...");
        charges c = charges.builder().titre("Test ID").build();
        c = service.create(c);
        Optional<charges> found = service.findById(c.getIdCharge());
        if (!found.isPresent()) throw new RuntimeException("FindById failed: not found");
        System.out.println("FindById passed.");
    }

    public static void testFindAll(chargesService service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();
        service.create(charges.builder().titre("All 1").build());
        if (service.findAll().size() != initialCount + 1) throw new RuntimeException("FindAll failed: count mismatch");
        System.out.println("FindAll passed.");
    }

    public static void testUpdate(chargesService service) throws Exception {
        System.out.println("Testing Update...");
        charges c = charges.builder().titre("Old Title").build();
        c = service.create(c);
        c.setTitre("New Title");
        charges updated = service.update(c);
        if (!updated.getTitre().equals("New Title")) throw new RuntimeException("Update failed: value mismatch");
        System.out.println("Update passed.");
    }

    public static void testDelete(chargesService service) throws Exception {
        System.out.println("Testing Delete...");
        charges c = charges.builder().titre("Delete Me").build();
        c = service.create(c);
        Long id = c.getIdCharge();
        service.delete(id);
        if (service.exists(id)) throw new RuntimeException("Delete failed: still exists");
        System.out.println("Delete passed.");
    }

    public static void testExists(chargesService service) throws Exception {
        System.out.println("Testing Exists...");
        charges c = charges.builder().titre("Exists").build();
        c = service.create(c);
        if (!service.exists(c.getIdCharge())) throw new RuntimeException("Exists failed: returned false");
        System.out.println("Exists passed.");
    }

    public static void testCount(chargesService service) throws Exception {
        System.out.println("Testing Count...");
        long count = service.count();
        if (count < 0) throw new RuntimeException("Count failed: negative");
        System.out.println("Count passed.");
    }
}
