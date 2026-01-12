package ma.TeethCare.service.test;

import ma.TeethCare.entities.medecin.medecin;
import ma.TeethCare.mvc.dto.medecin.MedecinDTO;
import ma.TeethCare.repository.api.MedecinRepository;
import ma.TeethCare.service.modules.users.api.medecinService;
import ma.TeethCare.service.modules.users.impl.medecinServiceImpl;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Hamza ALAOUI
 * @date 2025-12-17
 */

public class MedecinServiceTest {

    static class MedecinRepositoryStub implements MedecinRepository {

        private final Map<Long, medecin> data = new HashMap<>();
        private long idCounter = 1;

        @Override
        public List<medecin> findAll() throws SQLException {
            return new ArrayList<>(data.values());
        }

        @Override
        public medecin findById(Long id) {
            return data.get(id);
        }

        @Override
        public void create(medecin entity) {
            if (entity.getId() == null) {
                entity.setId(idCounter);
                // entity.setIdEntite(idCounter); // Redundant if setId used
                idCounter++;
            }
            data.put(entity.getId(), entity);
        }

        @Override
        public void update(medecin entity) {
            data.put(entity.getId(), entity);
        }

        @Override
        public void delete(medecin entity) {
            if (entity != null && entity.getId() != null) {
                data.remove(entity.getId());
            }
        }

        @Override
        public void deleteById(Long id) {
            data.remove(id);
        }

        @Override
        public Optional<medecin> findByCin(String cin) {
            return Optional.empty();
        }

        @Override
        public Optional<medecin> findByEmail(String email) {
            return data.values()
                    .stream()
                    .filter(m -> email != null && email.equals(m.getEmail()))
                    .findFirst();
        }
    }

    public static void main(String[] args) {
        try {
            MedecinRepositoryStub repo = new MedecinRepositoryStub();
            medecinService service = new medecinServiceImpl(repo);

            testCreate(service);
            testFindById(service);
            testFindAll(service);
            testUpdate(service);
            testDelete(service);
            testExists(service);
            testCount(service);

            System.out.println("All MedecinService tests passed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testCreate(medecinService service) throws Exception {
        System.out.println("Testing Create...");
        MedecinDTO m = MedecinDTO.builder()
                .nom("Dr Alpha")
                .email("alpha@medecin.com")
                .specialite("Dentiste")
                .build();

        MedecinDTO created = service.create(m);
        if (created.getId() == null)
            throw new RuntimeException("Create failed: ID is null");

        System.out.println("Create passed.");
    }

    public static void testFindById(medecinService service) throws Exception {
        System.out.println("Testing FindById...");
        MedecinDTO m = MedecinDTO.builder()
                .nom("Dr Find")
                .build();

        m = service.create(m);
        Optional<MedecinDTO> found = service.findById(m.getId());

        if (!found.isPresent())
            throw new RuntimeException("FindById failed: not found");

        System.out.println("FindById passed.");
    }

    public static void testFindAll(medecinService service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();

        MedecinDTO m = MedecinDTO.builder()
                .nom("Dr All")
                .build();
        service.create(m);

        if (service.findAll().size() != initialCount + 1)
            throw new RuntimeException("FindAll failed: count mismatch");

        System.out.println("FindAll passed.");
    }

    public static void testUpdate(medecinService service) throws Exception {
        System.out.println("Testing Update...");
        MedecinDTO m = MedecinDTO.builder()
                .nom("Old Name")
                .build();

        m = service.create(m);
        m.setNom("New Name");

        MedecinDTO updated = service.update(m);
        if (!"New Name".equals(updated.getNom()))
            throw new RuntimeException("Update failed: value mismatch");

        System.out.println("Update passed.");
    }

    public static void testDelete(medecinService service) throws Exception {
        System.out.println("Testing Delete...");
        MedecinDTO m = MedecinDTO.builder()
                .nom("Delete Me")
                .build();

        m = service.create(m);
        Long id = m.getId();

        service.delete(id);

        if (service.exists(id))
            throw new RuntimeException("Delete failed: still exists");

        System.out.println("Delete passed.");
    }

    public static void testExists(medecinService service) throws Exception {
        System.out.println("Testing Exists...");
        MedecinDTO m = MedecinDTO.builder()
                .nom("Exists")
                .build();

        m = service.create(m);

        if (!service.exists(m.getId()))
            throw new RuntimeException("Exists failed: returned false");

        System.out.println("Exists passed.");
    }

    public static void testCount(medecinService service) throws Exception {
        System.out.println("Testing Count...");
        long count = service.count();

        if (count < 0)
            throw new RuntimeException("Count failed: negative value");

        System.out.println("Count passed.");
    }
}

