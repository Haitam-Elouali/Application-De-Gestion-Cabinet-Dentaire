package ma.TeethCare.service.test;

import ma.TeethCare.entities.dossierMedicale.dossierMedicale;
import ma.TeethCare.repository.api.DossierMedicaleRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

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
            DossierMedicaleServiceTest service = new DossierMedicaleServiceTest(repo);

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

    private final DossierMedicaleRepository dossierMedicaleRepository;

    public DossierMedicaleServiceTest(DossierMedicaleRepository dossierMedicaleRepository) {
        this.dossierMedicaleRepository = dossierMedicaleRepository;
    }

    public dossierMedicale create(dossierMedicale entity) throws Exception {
        if (entity == null) {
            throw new IllegalArgumentException("DossierMedicale ne peut pas être null");
        }
        dossierMedicaleRepository.create(entity);
        return entity;
    }

    public Optional<dossierMedicale> findById(Long id) throws Exception {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(dossierMedicaleRepository.findById(id));
    }

    public List<dossierMedicale> findAll() throws Exception {
        return dossierMedicaleRepository.findAll();
    }

    public dossierMedicale update(dossierMedicale entity) throws Exception {
        if (entity == null || entity.getId() == null) {
            throw new IllegalArgumentException("DossierMedicale ou ID invalide");
        }
        dossierMedicaleRepository.update(entity);
        return entity;
    }

    public boolean delete(Long id) throws Exception {
        if (id == null) {
            return false;
        }
        dossierMedicaleRepository.deleteById(id);
        return true;
    }

    public boolean exists(Long id) throws Exception {
        if (id == null) {
            return false;
        }
        return dossierMedicaleRepository.findById(id) != null;
    }

    public long count() throws Exception {
        return dossierMedicaleRepository.findAll().size();
    }

    public static void testCreate(DossierMedicaleServiceTest service) throws Exception {
        System.out.println("Testing Create...");
        dossierMedicale dm = dossierMedicale.builder()
                .patientId(1L)
                .dateDeCreation(LocalDateTime.now())
                .build();
        dossierMedicale created = service.create(dm);
        if (created.getId() == null)
            throw new RuntimeException("Create failed: ID is null");
        System.out.println("Create passed.");
    }

    public static void testFindById(DossierMedicaleServiceTest service) throws Exception {
        System.out.println("Testing FindById...");
        dossierMedicale dm = dossierMedicale.builder()
                .patientId(2L)
                .dateDeCreation(LocalDateTime.now())
                .build();
        dm = service.create(dm);
        Optional<dossierMedicale> found = service.findById(dm.getId());
        if (!found.isPresent())
            throw new RuntimeException("FindById failed: not found");
        System.out.println("FindById passed.");
    }

    public static void testFindAll(DossierMedicaleServiceTest service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();
        service.create(dossierMedicale.builder()
                .patientId(3L)
                .dateDeCreation(LocalDateTime.now())
                .build());
        if (service.findAll().size() != initialCount + 1)
            throw new RuntimeException("FindAll failed: count mismatch");
        System.out.println("FindAll passed.");
    }

    public static void testUpdate(DossierMedicaleServiceTest service) throws Exception {
        System.out.println("Testing Update...");
        dossierMedicale dm = dossierMedicale.builder()
                .patientId(4L)
                .dateDeCreation(LocalDateTime.now())
                .build();
        dm = service.create(dm);
        dm.setPatientId(999L);
        dossierMedicale updated = service.update(dm);
        if (!Long.valueOf(999L).equals(updated.getPatientId()))
            throw new RuntimeException("Update failed: value mismatch");
        System.out.println("Update passed.");
    }

    public static void testDelete(DossierMedicaleServiceTest service) throws Exception {
        System.out.println("Testing Delete...");
        dossierMedicale dm = dossierMedicale.builder()
                .patientId(5L)
                .dateDeCreation(LocalDateTime.now())
                .build();
        dm = service.create(dm);
        Long id = dm.getId();
        service.delete(id);
        if (service.exists(id))
            throw new RuntimeException("Delete failed: still exists");
        System.out.println("Delete passed.");
    }

    public static void testExists(DossierMedicaleServiceTest service) throws Exception {
        System.out.println("Testing Exists...");
        dossierMedicale dm = dossierMedicale.builder()
                .patientId(6L)
                .dateDeCreation(LocalDateTime.now())
                .build();
        dm = service.create(dm);
        if (!service.exists(dm.getId()))
            throw new RuntimeException("Exists failed: returned false");
        System.out.println("Exists passed.");
    }

    public static void testCount(DossierMedicaleServiceTest service) throws Exception {
        System.out.println("Testing Count...");
        long count = service.count();
        if (count < 0)
            throw new RuntimeException("Count failed: negative");
        System.out.println("Count passed.");
    }
}
