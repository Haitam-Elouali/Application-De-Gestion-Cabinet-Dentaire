package ma.TeethCare.service.test;

import ma.TeethCare.entities.dossierMedicale.dossierMedicale;
import ma.TeethCare.repository.api.DossierMedicaleRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author MOKADAMI Zouhair
 * @date 2025-12-17
 */

import ma.TeethCare.service.modules.dossierMedical.dto.DossierMedicalDto;
import ma.TeethCare.service.modules.dossierMedical.impl.dossierMedicaleServiceImpl;
import ma.TeethCare.service.modules.dossierMedical.api.dossierMedicaleService;

public class DossierMedicaleServiceTest {

    // Stub implementation for DossierMedicaleRepository (In-Memory)
    static class DossierMedicaleRepositoryStub implements DossierMedicaleRepository {
        private Map<Long, dossierMedicale> data = new HashMap<>();
        private long idCounter = 1;

        @Override
        public List<dossierMedicale> findAll() throws SQLException {
            return new ArrayList<>(data.values());
        }

        @Override
        public dossierMedicale findById(Long id) {
            return data.get(id);
        }

        @Override
        public void create(dossierMedicale entity) {
            if (entity.getId() == null) {
                entity.setId(idCounter++);
            }
            data.put(entity.getId(), entity);
        }

        @Override
        public void update(dossierMedicale entity) {
            data.put(entity.getId(), entity);
        }

        @Override
        public void delete(dossierMedicale entity) {
            if (entity != null && entity.getId() != null) {
                data.remove(entity.getId());
            }
        }

        @Override
        public void deleteById(Long id) {
            data.remove(id);
        }
    }

    public static void main(String[] args) {
        try {
            DossierMedicaleRepositoryStub repo = new DossierMedicaleRepositoryStub();
            // Use constructor injection if possible or setter
            dossierMedicaleService service = new dossierMedicaleServiceImpl(repo);

            testCreate(service);
            testFindById(service);
            testFindAll(service);
            testUpdate(service);
            testDelete(service);
            testExists(service);
            testCount(service);

            System.out.println("All DossierMedicaleService tests passed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testCreate(dossierMedicaleService service) throws Exception {
        System.out.println("Testing Create...");
        DossierMedicalDto dm = new DossierMedicalDto(null, 1L, LocalDateTime.now());

        DossierMedicalDto created = service.create(dm);
        if (created.id() == null)
            throw new RuntimeException("Create failed: ID is null");
        System.out.println("Create passed.");
    }

    public static void testFindById(dossierMedicaleService service) throws Exception {
        System.out.println("Testing FindById...");
        DossierMedicalDto dm = new DossierMedicalDto(null, 2L, LocalDateTime.now());
        dm = service.create(dm);

        DossierMedicalDto found = service.findById(dm.id());
        if (found == null)
            throw new RuntimeException("FindById failed: not found");
        System.out.println("FindById passed.");
    }

    public static void testFindAll(dossierMedicaleService service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();
        service.create(new DossierMedicalDto(null, 3L, LocalDateTime.now()));

        if (service.findAll().size() != initialCount + 1)
            throw new RuntimeException("FindAll failed: count mismatch");
        System.out.println("FindAll passed.");
    }

    public static void testUpdate(dossierMedicaleService service) throws Exception {
        System.out.println("Testing Update...");
        DossierMedicalDto dm = new DossierMedicalDto(null, 4L, LocalDateTime.now());
        dm = service.create(dm);

        // Update patientId to 999L. Records are immutable, so create new instance with
        // update.
        DossierMedicalDto toUpdate = new DossierMedicalDto(dm.id(), 999L, dm.dateDeCreation());

        DossierMedicalDto updated = service.update(dm.id(), toUpdate);
        if (!Long.valueOf(999L).equals(updated.patientId()))
            throw new RuntimeException("Update failed: value mismatch");
        System.out.println("Update passed.");
    }

    public static void testDelete(dossierMedicaleService service) throws Exception {
        System.out.println("Testing Delete...");
        DossierMedicalDto dm = new DossierMedicalDto(null, 5L, LocalDateTime.now());
        dm = service.create(dm);
        Long id = dm.id();
        service.delete(id);
        if (service.exists(id))
            throw new RuntimeException("Delete failed: still exists");
        System.out.println("Delete passed.");
    }

    public static void testExists(dossierMedicaleService service) throws Exception {
        System.out.println("Testing Exists...");
        DossierMedicalDto dm = new DossierMedicalDto(null, 6L, LocalDateTime.now());
        dm = service.create(dm);
        if (!service.exists(dm.id()))
            throw new RuntimeException("Exists failed: returned false");
        System.out.println("Exists passed.");
    }

    public static void testCount(dossierMedicaleService service) throws Exception {
        System.out.println("Testing Count...");
        long count = service.count();
        if (count < 0)
            throw new RuntimeException("Count failed: negative");
        System.out.println("Count passed.");
    }
}
