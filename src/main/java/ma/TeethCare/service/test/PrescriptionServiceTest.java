package ma.TeethCare.service.test;

import ma.TeethCare.entities.prescription.prescription;
import ma.TeethCare.mvc.dto.prescription.PrescriptionDTO;
import ma.TeethCare.repository.api.PrescriptionRepository;
import ma.TeethCare.service.modules.dossierMedical.api.prescriptionService;
import ma.TeethCare.service.modules.dossierMedical.impl.prescriptionServiceImpl;
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
            if (entity.getId() == null) {
                entity.setId(idCounter++);
            }
            data.put(entity.getId(), entity);
        }

        @Override
        public void update(prescription entity) {
            data.put(entity.getId(), entity);
        }

        @Override
        public void delete(prescription entity) {
            data.remove(entity.getId());
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
        PrescriptionDTO p = PrescriptionDTO.builder()
            .duree(10)
            .instructions("Instructions")
            .build();
        PrescriptionDTO created = service.create(p);
        if (created.getId() == null) throw new RuntimeException("Create failed: ID is null");
        System.out.println("Create passed.");
    }

    public static void testFindById(prescriptionService service) throws Exception {
        System.out.println("Testing FindById...");
        PrescriptionDTO p = PrescriptionDTO.builder().duree(20).build();
        p = service.create(p);
        Optional<PrescriptionDTO> found = service.findById(p.getId());
        if (!found.isPresent()) throw new RuntimeException("FindById failed: not found");
        System.out.println("FindById passed.");
    }

    public static void testFindAll(prescriptionService service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();
        service.create(PrescriptionDTO.builder().duree(30).build());
        if (service.findAll().size() != initialCount + 1) throw new RuntimeException("FindAll failed: count mismatch");
        System.out.println("FindAll passed.");
    }

    public static void testUpdate(prescriptionService service) throws Exception {
        System.out.println("Testing Update...");
        PrescriptionDTO p = PrescriptionDTO.builder().duree(40).build();
        p = service.create(p);
        p.setDuree(50);
        PrescriptionDTO updated = service.update(p);
        if (updated.getDuree() != 50) throw new RuntimeException("Update failed: value mismatch");
        System.out.println("Update passed.");
    }

    public static void testDelete(prescriptionService service) throws Exception {
        System.out.println("Testing Delete...");
        PrescriptionDTO p = PrescriptionDTO.builder().duree(60).build();
        p = service.create(p);
        Long id = p.getId();
        service.delete(id);
        if (service.exists(id)) throw new RuntimeException("Delete failed: still exists");
        System.out.println("Delete passed.");
    }

    public static void testExists(prescriptionService service) throws Exception {
        System.out.println("Testing Exists...");
        PrescriptionDTO p = PrescriptionDTO.builder().duree(70).build();
        p = service.create(p);
        if (!service.exists(p.getId())) throw new RuntimeException("Exists failed: returned false");
        System.out.println("Exists passed.");
    }

    public static void testCount(prescriptionService service) throws Exception {
        System.out.println("Testing Count...");
        long count = service.count();
        if (count < 0) throw new RuntimeException("Count failed: negative");
        System.out.println("Count passed.");
    }
}

