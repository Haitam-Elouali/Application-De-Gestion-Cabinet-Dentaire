package ma.TeethCare.service.test;

import ma.TeethCare.entities.prescription.prescription;
import ma.TeethCare.repository.api.PrescriptionRepository;
import ma.TeethCare.service.modules.api.prescriptionService;
import ma.TeethCare.service.modules.impl.prescriptionServiceImpl;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.sql.SQLException;

/**
 * @author CHOUKHAIRI Noureddine
 * @date 2025-12-09
 */

public class PrescriptionServiceTest {

    static class PrescriptionRepositoryStub implements PrescriptionRepository {
        private Map<Long, prescription> data = new HashMap<>();
        private long idCounter = 1;

        @Override
        public List<prescription> findAll() throws SQLException {
            return new ArrayList<>(data.values());
        }

        @Override
        public prescription findById(Long id) {
            return data.get(id);
        }

        @Override
        public void create(prescription entity) {
            if (entity.getIdPr() == null) {
                entity.setIdPr(idCounter++);
            }
            data.put(entity.getIdPr(), entity);
        }

        @Override
        public void update(prescription entity) {
            data.put(entity.getIdPr(), entity);
        }

        @Override
        public void delete(prescription entity) {
            data.remove(entity.getIdPr());
        }

        @Override
        public void deleteById(Long id) {
            data.remove(id);
        }
    }

    public static void main(String[] args) {
        try {
            PrescriptionRepositoryStub repo = new PrescriptionRepositoryStub();
            prescriptionService service = new prescriptionServiceImpl(repo);

            testCreate(service);
            testFindById(service);
            testFindAll(service);
            testUpdate(service);
            testDelete(service);
            testExists(service);
            testCount(service);
            
            System.out.println("All PrescriptionService tests passed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testCreate(prescriptionService service) throws Exception {
        System.out.println("Testing Create...");
        prescription p = prescription.builder()
            .quantite(10)
            .build();
        prescription created = service.create(p);
        if (created.getIdPr() == null) throw new RuntimeException("Create failed: ID is null");
        System.out.println("Create passed.");
    }

    public static void testFindById(prescriptionService service) throws Exception {
        System.out.println("Testing FindById...");
        prescription p = prescription.builder().quantite(20).build();
        p = service.create(p);
        Optional<prescription> found = service.findById(p.getIdPr());
        if (!found.isPresent()) throw new RuntimeException("FindById failed: not found");
        System.out.println("FindById passed.");
    }

    public static void testFindAll(prescriptionService service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();
        service.create(prescription.builder().quantite(30).build());
        if (service.findAll().size() != initialCount + 1) throw new RuntimeException("FindAll failed: count mismatch");
        System.out.println("FindAll passed.");
    }

    public static void testUpdate(prescriptionService service) throws Exception {
        System.out.println("Testing Update...");
        prescription p = prescription.builder().quantite(40).build();
        p = service.create(p);
        p.setQuantite(50);
        prescription updated = service.update(p);
        if (updated.getQuantite() != 50) throw new RuntimeException("Update failed: value mismatch");
        System.out.println("Update passed.");
    }

    public static void testDelete(prescriptionService service) throws Exception {
        System.out.println("Testing Delete...");
        prescription p = prescription.builder().quantite(60).build();
        p = service.create(p);
        Long id = p.getIdPr();
        service.delete(id);
        if (service.exists(id)) throw new RuntimeException("Delete failed: still exists");
        System.out.println("Delete passed.");
    }

    public static void testExists(prescriptionService service) throws Exception {
        System.out.println("Testing Exists...");
        prescription p = prescription.builder().quantite(70).build();
        p = service.create(p);
        if (!service.exists(p.getIdPr())) throw new RuntimeException("Exists failed: returned false");
        System.out.println("Exists passed.");
    }

    public static void testCount(prescriptionService service) throws Exception {
        System.out.println("Testing Count...");
        long count = service.count();
        if (count < 0) throw new RuntimeException("Count failed: negative");
        System.out.println("Count passed.");
    }
}
