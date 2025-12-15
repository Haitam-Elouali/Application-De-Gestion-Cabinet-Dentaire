package ma.TeethCare.service.test;

import ma.TeethCare.entities.patient.Patient;
import ma.TeethCare.repository.api.PatientRepository;
import ma.TeethCare.service.modules.api.PatientService;
import ma.TeethCare.service.modules.impl.PatientServiceImpl;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Zouhair MOKADAMI
 * @date 2025-12-10
 */

public class PatientServiceTest {

    // Stub implementation for PatientRepository (In-Memory)
    static class PatientRepositoryStub implements PatientRepository {
        private Map<Long, Patient> data = new HashMap<>();
        private long idCounter = 1;

        @Override
        public List<Patient> findAll() throws SQLException {
            return new ArrayList<>(data.values());
        }

        @Override
        public Patient findById(Long id) {
            return data.get(id);
        }

        @Override
        public void create(Patient entity) {
            if (entity.getIdEntite() == null) {
                entity.setIdEntite(idCounter++);
            }
            data.put(entity.getIdEntite(), entity);
        }

        @Override
        public void update(Patient entity) {
            data.put(entity.getIdEntite(), entity);
        }

        @Override
        public void delete(Patient entity) {
            if (entity != null && entity.getIdEntite() != null) {
                data.remove(entity.getIdEntite());
            }
        }

        @Override
        public void deleteById(Long id) {
            data.remove(id);
        }
    }

    public static void main(String[] args) {
        try {
            PatientRepositoryStub repo = new PatientRepositoryStub();
            PatientService service = new PatientServiceImpl(repo);

            testCreate(service);
            testFindById(service);
            testFindAll(service);
            testUpdate(service);
            testDelete(service);
            testExists(service);
            testCount(service);

            System.out.println("All PatientService tests passed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testCreate(PatientService service) throws Exception {
        System.out.println("Testing Create...");
        Patient p = Patient.builder()
                .nom("Doe")
                .prenom("John")
                .adresse("123 Main St")
                .build();
        Patient created = service.create(p);
        if (created.getIdEntite() == null) throw new RuntimeException("Create failed: ID is null");
        System.out.println("Create passed.");
    }

    public static void testFindById(PatientService service) throws Exception {
        System.out.println("Testing FindById...");
        Patient p = Patient.builder().nom("Smith").prenom("Jane").build();
        p = service.create(p);
        Optional<Patient> found = service.findById(p.getIdEntite());
        if (!found.isPresent()) throw new RuntimeException("FindById failed: not found");
        System.out.println("FindById passed.");
    }

    public static void testFindAll(PatientService service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();
        service.create(Patient.builder().nom("Brown").prenom("Bob").build());
        if (service.findAll().size() != initialCount + 1) throw new RuntimeException("FindAll failed: count mismatch");
        System.out.println("FindAll passed.");
    }

    public static void testUpdate(PatientService service) throws Exception {
        System.out.println("Testing Update...");
        Patient p = Patient.builder().nom("White").prenom("Walter").build();
        p = service.create(p);
        p.setNom("Heisenberg");
        Patient updated = service.update(p);
        if (!"Heisenberg".equals(updated.getNom())) throw new RuntimeException("Update failed: value mismatch");
        System.out.println("Update passed.");
    }

    public static void testDelete(PatientService service) throws Exception {
        System.out.println("Testing Delete...");
        Patient p = Patient.builder().nom("Pinkman").prenom("Jesse").build();
        p = service.create(p);
        Long id = p.getIdEntite();
        service.delete(id);
        if (service.exists(id)) throw new RuntimeException("Delete failed: still exists");
        System.out.println("Delete passed.");
    }

    public static void testExists(PatientService service) throws Exception {
        System.out.println("Testing Exists...");
        Patient p = Patient.builder().nom("Goodman").prenom("Saul").build();
        p = service.create(p);
        if (!service.exists(p.getIdEntite())) throw new RuntimeException("Exists failed: returned false");
        System.out.println("Exists passed.");
    }

    public static void testCount(PatientService service) throws Exception {
        System.out.println("Testing Count...");
        long count = service.count();
        if (count < 0) throw new RuntimeException("Count failed: negative");
        System.out.println("Count passed.");
    }
}
