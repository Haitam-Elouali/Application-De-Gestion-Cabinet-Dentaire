package ma.TeethCare.service.test;

import ma.TeethCare.entities.cabinetMedicale.cabinetMedicale;
import ma.TeethCare.repository.api.CabinetMedicaleRepository;
import ma.TeethCare.service.modules.api.cabinetMedicaleService;
import ma.TeethCare.service.modules.impl.cabinetMedicaleServiceImpl;
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

public class CabinetMedicaleServiceTest {

    static class CabinetMedicaleRepositoryStub implements CabinetMedicaleRepository {
        private Map<Long, cabinetMedicale> data = new HashMap<>();
        private long idCounter = 1;

        @Override
        public List<cabinetMedicale> findAll() throws SQLException {
            return new ArrayList<>(data.values());
        }

        @Override
        public cabinetMedicale findById(Long id) {
            return data.get(id);
        }

        @Override
        public void create(cabinetMedicale entity) {
            if (entity.getIdCabinet() == null) {
                entity.setIdCabinet(idCounter++);
            }
            data.put(entity.getIdCabinet(), entity);
        }

        @Override
        public void update(cabinetMedicale entity) {
            data.put(entity.getIdCabinet(), entity);
        }

        @Override
        public void delete(cabinetMedicale entity) {
            data.remove(entity.getIdCabinet());
        }

        @Override
        public void deleteById(Long id) {
            data.remove(id);
        }

        @Override
        public Optional<cabinetMedicale> findByEmail(String email) {
            return data.values().stream().filter(c -> c.getEmail().equals(email)).findFirst();
        }
    }

    public static void main(String[] args) {
        try {
            CabinetMedicaleRepositoryStub repo = new CabinetMedicaleRepositoryStub();
            cabinetMedicaleService service = new cabinetMedicaleServiceImpl(repo);

            testCreate(service);
            testFindById(service);
            testFindAll(service);
            testUpdate(service);
            testDelete(service);
            testExists(service);
            testCount(service);
            
            System.out.println("All CabinetMedicaleService tests passed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testCreate(cabinetMedicaleService service) throws Exception {
        System.out.println("Testing Create...");
        cabinetMedicale c = cabinetMedicale.builder()
            .nom("Cabinet Alpha")
            .email("alpha@cabinet.com")
            .adresse("1 First St")
            .build();
        cabinetMedicale created = service.create(c);
        if (created.getIdCabinet() == null) throw new RuntimeException("Create failed: ID is null");
        System.out.println("Create passed.");
    }

    public static void testFindById(cabinetMedicaleService service) throws Exception {
        System.out.println("Testing FindById...");
        cabinetMedicale c = cabinetMedicale.builder().nom("Test ID").build();
        c = service.create(c);
        Optional<cabinetMedicale> found = service.findById(c.getIdCabinet());
        if (!found.isPresent()) throw new RuntimeException("FindById failed: not found");
        System.out.println("FindById passed.");
    }

    public static void testFindAll(cabinetMedicaleService service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();
        service.create(cabinetMedicale.builder().nom("All 1").build());
        if (service.findAll().size() != initialCount + 1) throw new RuntimeException("FindAll failed: count mismatch");
        System.out.println("FindAll passed.");
    }

    public static void testUpdate(cabinetMedicaleService service) throws Exception {
        System.out.println("Testing Update...");
        cabinetMedicale c = cabinetMedicale.builder().nom("Old Name").build();
        c = service.create(c);
        c.setNom("New Name");
        cabinetMedicale updated = service.update(c);
        if (!updated.getNom().equals("New Name")) throw new RuntimeException("Update failed: value mismatch");
        System.out.println("Update passed.");
    }

    public static void testDelete(cabinetMedicaleService service) throws Exception {
        System.out.println("Testing Delete...");
        cabinetMedicale c = cabinetMedicale.builder().nom("Delete Me").build();
        c = service.create(c);
        Long id = c.getIdCabinet();
        service.delete(id);
        if (service.exists(id)) throw new RuntimeException("Delete failed: still exists");
        System.out.println("Delete passed.");
    }

    public static void testExists(cabinetMedicaleService service) throws Exception {
        System.out.println("Testing Exists...");
        cabinetMedicale c = cabinetMedicale.builder().nom("Exists").build();
        c = service.create(c);
        if (!service.exists(c.getIdCabinet())) throw new RuntimeException("Exists failed: returned false");
        System.out.println("Exists passed.");
    }

    public static void testCount(cabinetMedicaleService service) throws Exception {
        System.out.println("Testing Count...");
        long count = service.count();
        if (count < 0) throw new RuntimeException("Count failed: negative");
        System.out.println("Count passed.");
    }
}
