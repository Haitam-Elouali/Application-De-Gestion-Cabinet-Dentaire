package ma.TeethCare.service.test;

import ma.TeethCare.entities.consultation.consultation;
import ma.TeethCare.repository.api.ConsultationRepository;
import ma.TeethCare.service.modules.dossierMedical.api.consultationService;
import ma.TeethCare.service.modules.dossierMedical.impl.consultationServiceImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

/**
 * @author Hamza ALAOUI
 * @date 2025-12-17
 */


public class ConsultationServiceTest {

    static class ConsultationRepositoryStub implements ConsultationRepository {

        private final Map<Long, consultation> data = new HashMap<>();
        private long idCounter = 1;

        @Override
        public List<consultation> findAll() throws SQLException {
            return new ArrayList<>(data.values());
        }

        @Override
        public consultation findById(Long id) {
            return data.get(id);
        }

        @Override
        public void create(consultation entity) {
            if (entity.getId() == null) {
                entity.setId(idCounter);
                entity.setIdEntite(idCounter);
                idCounter++;
            }
            data.put(entity.getId(), entity);
        }

        @Override
        public void update(consultation entity) {
            data.put(entity.getId(), entity);
        }

        @Override
        public void delete(consultation entity) {
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
            ConsultationRepositoryStub repo = new ConsultationRepositoryStub();
            consultationService service = new consultationServiceImpl(repo);

            testCreate(service);
            testFindById(service);
            testFindAll(service);
            testUpdate(service);
            testDelete(service);
            testExists(service);
            testCount(service);

            System.out.println("All ConsultationService tests passed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testCreate(consultationService service) throws Exception {
        System.out.println("Testing Create...");
        consultation c = new consultation();
        c.setDate(LocalDate.now());
        c.setObservation("Observation test");
        c.setDiagnostic("Diagnostic test");

        consultation created = service.create(c);
        if (created.getId() == null)
            throw new RuntimeException("Create failed: ID is null");

        System.out.println("Create passed.");
    }

    public static void testFindById(consultationService service) throws Exception {
        System.out.println("Testing FindById...");
        consultation c = new consultation();
        c.setObservation("FindById test");

        c = service.create(c);
        Optional<consultation> found = service.findById(c.getId());

        if (!found.isPresent())
            throw new RuntimeException("FindById failed: not found");

        System.out.println("FindById passed.");
    }

    public static void testFindAll(consultationService service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();

        consultation c = new consultation();
        c.setObservation("FindAll test");
        service.create(c);

        if (service.findAll().size() != initialCount + 1)
            throw new RuntimeException("FindAll failed: count mismatch");

        System.out.println("FindAll passed.");
    }

    public static void testUpdate(consultationService service) throws Exception {
        System.out.println("Testing Update...");
        consultation c = new consultation();
        c.setObservation("Old Observation");

        c = service.create(c);
        c.setObservation("New Observation");

        consultation updated = service.update(c);
        if (!"New Observation".equals(updated.getObservation()))
            throw new RuntimeException("Update failed: value mismatch");

        System.out.println("Update passed.");
    }

    public static void testDelete(consultationService service) throws Exception {
        System.out.println("Testing Delete...");
        consultation c = new consultation();
        c.setObservation("Delete Me");

        c = service.create(c);
        Long id = c.getId();

        service.delete(id);

        if (service.exists(id))
            throw new RuntimeException("Delete failed: still exists");

        System.out.println("Delete passed.");
    }

    public static void testExists(consultationService service) throws Exception {
        System.out.println("Testing Exists...");
        consultation c = new consultation();
        c.setObservation("Exists test");

        c = service.create(c);

        if (!service.exists(c.getId()))
            throw new RuntimeException("Exists failed: returned false");

        System.out.println("Exists passed.");
    }

    public static void testCount(consultationService service) throws Exception {
        System.out.println("Testing Count...");
        long count = service.count();

        if (count < 0)
            throw new RuntimeException("Count failed: negative value");

        System.out.println("Count passed.");
    }
}

