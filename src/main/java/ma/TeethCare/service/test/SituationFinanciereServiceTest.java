package ma.TeethCare.service.test;

import ma.TeethCare.entities.situationFinanciere.situationFinanciere;
import ma.TeethCare.repository.api.SituationFinanciereRepository;
import ma.TeethCare.service.modules.caisse.api.situationFinanciereService;
import ma.TeethCare.service.modules.caisse.impl.situationFinanciereServiceImpl;
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

public class SituationFinanciereServiceTest {

    static class SituationFinanciereRepositoryStub implements SituationFinanciereRepository {
        private Map<Long, situationFinanciere> data = new HashMap<>();
        private long idCounter = 1;

        @Override
        public List<situationFinanciere> findAll() throws SQLException {
            return new ArrayList<>(data.values());
        }

        @Override
        public situationFinanciere findById(Long id) {
            return data.get(id);
        }

        @Override
        public void create(situationFinanciere entity) {
            if (entity.getId() == null) {
                entity.setId(idCounter++);
            }
            data.put(entity.getId(), entity);
        }

        @Override
        public void update(situationFinanciere entity) {
            data.put(entity.getId(), entity);
        }

        @Override
        public void delete(situationFinanciere entity) {
            data.remove(entity.getId());
        }

        @Override
        public void deleteById(Long id) {
            data.remove(id);
        }
    }

    public static void main(String[] args) {
        try {
            SituationFinanciereRepositoryStub repo = new SituationFinanciereRepositoryStub();
            situationFinanciereService service = new situationFinanciereServiceImpl(repo);

            testCreate(service);
            testFindById(service);
            testFindAll(service);
            testUpdate(service);
            testDelete(service);
            testExists(service);
            testCount(service);
            
            System.out.println("All SituationFinanciereService tests passed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testCreate(situationFinanciereService service) throws Exception {
        System.out.println("Testing Create...");
        situationFinanciere s = situationFinanciere.builder()
            .totalDesActes(1000.0)
            .totalPaye(500.0)
            .build();
        situationFinanciere created = service.create(s);
        if (created.getId() == null) throw new RuntimeException("Create failed: ID is null");
        System.out.println("Create passed.");
    }

    public static void testFindById(situationFinanciereService service) throws Exception {
        System.out.println("Testing FindById...");
        situationFinanciere s = situationFinanciere.builder().totalDesActes(200.0).build();
        s = service.create(s);
        Optional<situationFinanciere> found = service.findById(s.getId());
        if (!found.isPresent()) throw new RuntimeException("FindById failed: not found");
        System.out.println("FindById passed.");
    }

    public static void testFindAll(situationFinanciereService service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();
        service.create(situationFinanciere.builder().totalDesActes(300.0).build());
        if (service.findAll().size() != initialCount + 1) throw new RuntimeException("FindAll failed: count mismatch");
        System.out.println("FindAll passed.");
    }

    public static void testUpdate(situationFinanciereService service) throws Exception {
        System.out.println("Testing Update...");
        situationFinanciere s = situationFinanciere.builder().totalDesActes(400.0).build();
        s = service.create(s);
        s.setTotalDesActes(450.0);
        situationFinanciere updated = service.update(s);
        if (!updated.getTotalDesActes().equals(450.0)) throw new RuntimeException("Update failed: value mismatch");
        System.out.println("Update passed.");
    }

    public static void testDelete(situationFinanciereService service) throws Exception {
        System.out.println("Testing Delete...");
        situationFinanciere s = situationFinanciere.builder().totalDesActes(0.0).build();
        s = service.create(s);
        Long id = s.getId();
        service.delete(id);
        if (service.exists(id)) throw new RuntimeException("Delete failed: still exists");
        System.out.println("Delete passed.");
    }

    public static void testExists(situationFinanciereService service) throws Exception {
        System.out.println("Testing Exists...");
        situationFinanciere s = situationFinanciere.builder().totalDesActes(100.0).build();
        s = service.create(s);
        if (!service.exists(s.getId())) throw new RuntimeException("Exists failed: returned false");
        System.out.println("Exists passed.");
    }

    public static void testCount(situationFinanciereService service) throws Exception {
        System.out.println("Testing Count...");
        long count = service.count();
        if (count < 0) throw new RuntimeException("Count failed: negative");
        System.out.println("Count passed.");
    }
}

