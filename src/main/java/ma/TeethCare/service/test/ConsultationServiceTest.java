package ma.TeethCare.service.test;

import ma.TeethCare.entities.consultation.consultation;
import ma.TeethCare.mvc.dto.consultation.ConsultationDTO;
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
        ConsultationDTO c = ConsultationDTO.builder()
                .date(LocalDate.now())
                .notes("Observation test")
                .diagnostique("Diagnostic test")
                .build();

        ConsultationDTO created = service.create(c);
        if (created.getId() == null)
            throw new RuntimeException("Create failed: ID is null");

        System.out.println("Create passed.");
    }

    public static void testFindById(consultationService service) throws Exception {
        System.out.println("Testing FindById...");
        ConsultationDTO c = ConsultationDTO.builder()
                .date(LocalDate.now())
                .notes("FindById test")
                .build();

        c = service.create(c);
        Optional<ConsultationDTO> found = service.findById(c.getId());

        if (!found.isPresent())
            throw new RuntimeException("FindById failed: not found");

        System.out.println("FindById passed.");
    }

    public static void testFindAll(consultationService service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();

        ConsultationDTO c = ConsultationDTO.builder()
                .date(LocalDate.now())
                .notes("FindAll test")
                .build();
        service.create(c);

        if (service.findAll().size() != initialCount + 1)
            throw new RuntimeException("FindAll failed: count mismatch");

        System.out.println("FindAll passed.");
    }

    public static void testUpdate(consultationService service) throws Exception {
        System.out.println("Testing Update...");
        ConsultationDTO c = ConsultationDTO.builder()
                .date(LocalDate.now())
                .notes("Old Observation")
                .build();

        c = service.create(c);
        c.setNotes("New Observation");

        ConsultationDTO updated = service.update(c);
        if (!"New Observation".equals(updated.getNotes()))
            throw new RuntimeException("Update failed: value mismatch");

        System.out.println("Update passed.");
    }

    public static void testDelete(consultationService service) throws Exception {
        System.out.println("Testing Delete...");
        ConsultationDTO c = ConsultationDTO.builder()
                .date(LocalDate.now())
                .notes("Delete Me")
                .build();

        c = service.create(c);
        Long id = c.getId();

        service.delete(id);

        if (service.exists(id))
            throw new RuntimeException("Delete failed: still exists");

        System.out.println("Delete passed.");
    }

    public static void testExists(consultationService service) throws Exception {
        System.out.println("Testing Exists...");
        ConsultationDTO c = ConsultationDTO.builder()
                .date(LocalDate.now())
                .notes("Exists test")
                .build();

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

