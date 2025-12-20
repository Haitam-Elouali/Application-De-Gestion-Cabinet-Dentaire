package ma.TeethCare.service.test;

import ma.TeethCare.entities.facture.facture;
import ma.TeethCare.repository.api.FactureRepository;
import ma.TeethCare.service.modules.caisse.api.factureService;
import ma.TeethCare.service.modules.caisse.impl.factureServiceImpl;
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

public class FactureServiceTest {

    static class FactureRepositoryStub implements FactureRepository {
        private Map<Long, facture> data = new HashMap<>();
        private long idCounter = 1;

        @Override
        public List<facture> findAll() throws SQLException {
            return new ArrayList<>(data.values());
        }

        @Override
        public facture findById(Long id) {
            return data.get(id);
        }

        @Override
        public void create(facture entity) {
            if (entity.getId() == null) {
                entity.setId(idCounter++);
            }
            data.put(entity.getId(), entity);
        }

        @Override
        public void update(facture entity) {
            data.put(entity.getId(), entity);
        }

        @Override
        public void delete(facture entity) {
            data.remove(entity.getId());
        }

        @Override
        public void deleteById(Long id) {
            data.remove(id);
        }
    }

    public static void main(String[] args) {
        try {
            FactureRepositoryStub repo = new FactureRepositoryStub();
            factureService service = new factureServiceImpl(repo);

            testCreate(service);
            testFindById(service);
            testFindAll(service);
            testUpdate(service);
            testDelete(service);
            testExists(service);
            testCount(service);
            
            System.out.println("All FactureService tests passed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testCreate(factureService service) throws Exception {
        System.out.println("Testing Create...");
        facture f = facture.builder()
            .totaleFacture(100.0)
            .build();
        facture created = service.create(f);
        if (created.getId() == null) throw new RuntimeException("Create failed: ID is null");
        System.out.println("Create passed.");
    }

    public static void testFindById(factureService service) throws Exception {
        System.out.println("Testing FindById...");
        facture f = facture.builder().totaleFacture(200.0).build();
        f = service.create(f);
        Optional<facture> found = service.findById(f.getId());
        if (!found.isPresent()) throw new RuntimeException("FindById failed: not found");
        System.out.println("FindById passed.");
    }

    public static void testFindAll(factureService service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();
        service.create(facture.builder().totaleFacture(300.0).build());
        if (service.findAll().size() != initialCount + 1) throw new RuntimeException("FindAll failed: count mismatch");
        System.out.println("FindAll passed.");
    }

    public static void testUpdate(factureService service) throws Exception {
        System.out.println("Testing Update...");
        facture f = facture.builder().totaleFacture(400.0).build();
        f = service.create(f);
        f.setTotaleFacture(500.0);
        facture updated = service.update(f);
        if (updated.getTotaleFacture() != 500.0) throw new RuntimeException("Update failed: value mismatch");
        System.out.println("Update passed.");
    }

    public static void testDelete(factureService service) throws Exception {
        System.out.println("Testing Delete...");
        facture f = facture.builder().totaleFacture(600.0).build();
        f = service.create(f);
        Long id = f.getId();
        service.delete(id);
        if (service.exists(id)) throw new RuntimeException("Delete failed: still exists");
        System.out.println("Delete passed.");
    }

    public static void testExists(factureService service) throws Exception {
        System.out.println("Testing Exists...");
        facture f = facture.builder().totaleFacture(700.0).build();
        f = service.create(f);
        if (!service.exists(f.getId())) throw new RuntimeException("Exists failed: returned false");
        System.out.println("Exists passed.");
    }

    public static void testCount(factureService service) throws Exception {
        System.out.println("Testing Count...");
        long count = service.count();
        if (count < 0) throw new RuntimeException("Count failed: negative");
        System.out.println("Count passed.");
    }
}

