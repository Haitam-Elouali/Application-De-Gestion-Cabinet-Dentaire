package ma.TeethCare.service.test;

import ma.TeethCare.entities.caisse.caisse;
import ma.TeethCare.repository.api.CaisseRepository;
import ma.TeethCare.service.modules.api.caisseService;
import ma.TeethCare.service.modules.impl.caisseServiceImpl;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.sql.SQLException;

/**
 * @author ELOUALI Haitam
 * @date 2025-12-09
 */

public class CaisseServiceTest {

    static class CaisseRepositoryStub implements CaisseRepository {
        private Map<Long, caisse> data = new HashMap<>();
        private long idCounter = 1;

        @Override
        public List<caisse> findAll() throws SQLException {
            return new ArrayList<>(data.values());
        }

        @Override
        public caisse findById(Long id) {
            return data.get(id);
        }

        @Override
        public void create(caisse entity) {
            if (entity.getIdCaisse() == null) {
                entity.setIdCaisse(idCounter++);
            }
            data.put(entity.getIdCaisse(), entity);
        }

        @Override
        public void update(caisse entity) {
            data.put(entity.getIdCaisse(), entity);
        }

        @Override
        public void delete(caisse entity) {
            data.remove(entity.getIdCaisse());
        }

        @Override
        public void deleteById(Long id) {
            data.remove(id);
        }
    }

    public static void main(String[] args) {
        try {
            CaisseRepositoryStub repo = new CaisseRepositoryStub();
            caisseService service = new caisseServiceImpl(repo);

            testCreate(service);
            testFindById(service);
            testFindAll(service);
            testUpdate(service);
            testDelete(service);
            testExists(service);
            testCount(service);
            
            System.out.println("All CaisseService tests passed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testCreate(caisseService service) throws Exception {
        System.out.println("Testing Create...");
        caisse c = caisse.builder()
            .montant(150.0)
            .build();
        caisse created = service.create(c);
        if (created.getIdCaisse() == null) throw new RuntimeException("Create failed: ID is null");
        System.out.println("Create passed.");
    }

    public static void testFindById(caisseService service) throws Exception {
        System.out.println("Testing FindById...");
        caisse c = caisse.builder().montant(250.0).build();
        c = service.create(c);
        Optional<caisse> found = service.findById(c.getIdCaisse());
        if (!found.isPresent()) throw new RuntimeException("FindById failed: not found");
        System.out.println("FindById passed.");
    }

    public static void testFindAll(caisseService service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();
        service.create(caisse.builder().montant(350.0).build());
        if (service.findAll().size() != initialCount + 1) throw new RuntimeException("FindAll failed: count mismatch");
        System.out.println("FindAll passed.");
    }

    public static void testUpdate(caisseService service) throws Exception {
        System.out.println("Testing Update...");
        caisse c = caisse.builder().montant(450.0).build();
        c = service.create(c);
        c.setMontant(550.0);
        caisse updated = service.update(c);
        if (updated.getMontant() != 550.0) throw new RuntimeException("Update failed: value mismatch");
        System.out.println("Update passed.");
    }

    public static void testDelete(caisseService service) throws Exception {
        System.out.println("Testing Delete...");
        caisse c = caisse.builder().montant(650.0).build();
        c = service.create(c);
        Long id = c.getIdCaisse();
        service.delete(id);
        if (service.exists(id)) throw new RuntimeException("Delete failed: still exists");
        System.out.println("Delete passed.");
    }

    public static void testExists(caisseService service) throws Exception {
        System.out.println("Testing Exists...");
        caisse c = caisse.builder().montant(750.0).build();
        c = service.create(c);
        if (!service.exists(c.getIdCaisse())) throw new RuntimeException("Exists failed: returned false");
        System.out.println("Exists passed.");
    }

    public static void testCount(caisseService service) throws Exception {
        System.out.println("Testing Count...");
        long count = service.count();
        if (count < 0) throw new RuntimeException("Count failed: negative");
        System.out.println("Count passed.");
    }
}
