package ma.TeethCare.service.test;

import ma.TeethCare.entities.interventionMedecin.interventionMedecin;
import ma.TeethCare.repository.api.InterventionMedecinRepository;
import ma.TeethCare.service.modules.api.interventionMedecinService;
import ma.TeethCare.service.modules.impl.interventionMedecinServiceImpl;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Hamza ALAOUI
 * @date 2025-12-17
 */


public class InterventionMedecinServiceTest {

    static class InterventionMedecinRepositoryStub implements InterventionMedecinRepository {

        private final Map<Long, interventionMedecin> data = new HashMap<>();
        private long idCounter = 1;

        @Override
        public List<interventionMedecin> findAll() throws SQLException {
            return new ArrayList<>(data.values());
        }

        @Override
        public interventionMedecin findById(Long id) {
            return data.get(id);
        }

        @Override
        public void create(interventionMedecin entity) {
            if (entity.getId() == null) {
                entity.setId(idCounter);
                entity.setIdEntite(idCounter);
                idCounter++;
            }
            data.put(entity.getId(), entity);
        }

        @Override
        public void update(interventionMedecin entity) {
            data.put(entity.getId(), entity);
        }

        @Override
        public void delete(interventionMedecin entity) {
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
            InterventionMedecinRepositoryStub repo = new InterventionMedecinRepositoryStub();
            interventionMedecinService service = new interventionMedecinServiceImpl(repo);

            testCreate(service);
            testFindById(service);
            testFindAll(service);
            testUpdate(service);
            testDelete(service);
            testExists(service);
            testCount(service);

            System.out.println("All InterventionMedecinService tests passed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testCreate(interventionMedecinService service) throws Exception {
        System.out.println("Testing Create...");
        interventionMedecin i = new interventionMedecin();
        i.setConsultationId(1L);

        interventionMedecin created = service.create(i);
        if (created.getId() == null)
            throw new RuntimeException("Create failed: ID is null");

        System.out.println("Create passed.");
    }

    public static void testFindById(interventionMedecinService service) throws Exception {
        System.out.println("Testing FindById...");
        interventionMedecin i = new interventionMedecin();
        i.setConsultationId(2L);

        i = service.create(i);
        Optional<interventionMedecin> found = service.findById(i.getId());

        if (!found.isPresent())
            throw new RuntimeException("FindById failed: not found");

        System.out.println("FindById passed.");
    }

    public static void testFindAll(interventionMedecinService service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();

        interventionMedecin i = new interventionMedecin();
        i.setConsultationId(3L);
        service.create(i);

        if (service.findAll().size() != initialCount + 1)
            throw new RuntimeException("FindAll failed: count mismatch");

        System.out.println("FindAll passed.");
    }

    public static void testUpdate(interventionMedecinService service) throws Exception {
        System.out.println("Testing Update...");
        interventionMedecin i = new interventionMedecin();
        i.setConsultationId(4L);

        i = service.create(i);
        i.setConsultationId(99L);

        interventionMedecin updated = service.update(i);
        if (!updated.getConsultationId().equals(99L))
            throw new RuntimeException("Update failed: value mismatch");

        System.out.println("Update passed.");
    }

    public static void testDelete(interventionMedecinService service) throws Exception {
        System.out.println("Testing Delete...");
        interventionMedecin i = new interventionMedecin();
        i.setConsultationId(5L);

        i = service.create(i);
        Long id = i.getId();

        service.delete(id);

        if (service.exists(id))
            throw new RuntimeException("Delete failed: still exists");

        System.out.println("Delete passed.");
    }

    public static void testExists(interventionMedecinService service) throws Exception {
        System.out.println("Testing Exists...");
        interventionMedecin i = new interventionMedecin();
        i.setConsultationId(6L);

        i = service.create(i);

        if (!service.exists(i.getId()))
            throw new RuntimeException("Exists failed: returned false");

        System.out.println("Exists passed.");
    }

    public static void testCount(interventionMedecinService service) throws Exception {
        System.out.println("Testing Count...");
        long count = service.count();

        if (count < 0)
            throw new RuntimeException("Count failed: negative");

        System.out.println("Count passed.");
    }
}