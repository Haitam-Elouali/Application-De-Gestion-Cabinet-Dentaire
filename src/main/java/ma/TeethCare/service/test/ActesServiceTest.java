package ma.TeethCare.service.test;

import ma.TeethCare.entities.actes.actes;
import ma.TeethCare.mvc.dto.actes.ActesDTO;
import ma.TeethCare.repository.api.ActesRepository;
import ma.TeethCare.service.modules.dossierMedical.api.actesService;
import ma.TeethCare.service.modules.dossierMedical.impl.actesServiceImpl;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Hamza ALAOUI
 * @date 2025-12-17
 */

public class ActesServiceTest {

    static class ActesRepositoryStub implements ActesRepository {
        private final Map<Long, actes> data = new HashMap<>();
        private long idCounter = 1;

        @Override
        public List<actes> findAll() throws SQLException {
            return new ArrayList<>(data.values());
        }

        @Override
        public actes findById(Long id) {
            return data.get(id);
        }

        @Override
        public void create(actes entity) {
            if (entity.getId() == null) {
                entity.setId(idCounter++);
            }
            data.put(entity.getId(), entity);
        }

        @Override
        public void update(actes entity) {
            data.put(entity.getId(), entity);
        }

        @Override
        public void delete(actes entity) {
            if (entity != null && entity.getId() != null) {
                data.remove(entity.getId());
            }
        }

        @Override
        public void deleteById(Long id) {
            data.remove(id);
        }

        @Override
        public List<actes> findByCategorie(String categorie) {
            List<actes> res = new ArrayList<>();
            for (actes a : data.values()) {
                if (a.getCategorie() != null && a.getCategorie().equals(categorie)) {
                    res.add(a);
                }
            }
            return res;
        }
    }

    public static void main(String[] args) {
        try {
            ActesRepositoryStub repo = new ActesRepositoryStub();
            actesService service = new actesServiceImpl(repo);

            testCreate(service);
            testFindById(service);
            testFindAll(service);
            testUpdate(service);
            testDelete(service);
            testExists(service);
            testCount(service);

            System.out.println("All ActesService tests passed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testCreate(actesService service) throws Exception {
        System.out.println("Testing Create...");
        ActesDTO a = ActesDTO.builder()
                .nom("Acte Alpha")
                .categorie("CAT1")
                .prix(120.0)
                .build();

        ActesDTO created = service.create(a);
        if (created.getId() == null) throw new RuntimeException("Create failed: ID is null");
        System.out.println("Create passed.");
    }

    public static void testFindById(actesService service) throws Exception {
        System.out.println("Testing FindById...");
        ActesDTO a = ActesDTO.builder()
                .nom("FindById")
                .build();
        a = service.create(a);

        Optional<ActesDTO> found = service.findById(a.getId());
        if (!found.isPresent()) throw new RuntimeException("FindById failed: not found");
        System.out.println("FindById passed.");
    }

    public static void testFindAll(actesService service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();

        ActesDTO a = ActesDTO.builder()
                .nom("All 1")
                .build();
        service.create(a);

        if (service.findAll().size() != initialCount + 1)
            throw new RuntimeException("FindAll failed: count mismatch");
        System.out.println("FindAll passed.");
    }

    public static void testUpdate(actesService service) throws Exception {
        System.out.println("Testing Update...");
        ActesDTO a = ActesDTO.builder()
                .nom("Old Name")
                .categorie("CAT1")
                .prix(50.0)
                .build();

        a = service.create(a);
        a.setNom("New Name");

        ActesDTO updated = service.update(a);
        if (!"New Name".equals(updated.getNom()))
            throw new RuntimeException("Update failed: value mismatch");
        System.out.println("Update passed.");
    }

    public static void testDelete(actesService service) throws Exception {
        System.out.println("Testing Delete...");
        ActesDTO a = ActesDTO.builder()
                .nom("Delete Me")
                .build();

        a = service.create(a);
        Long id = a.getId();

        service.delete(id);

        if (service.exists(id))
            throw new RuntimeException("Delete failed: still exists");
        System.out.println("Delete passed.");
    }

    public static void testExists(actesService service) throws Exception {
        System.out.println("Testing Exists...");
        ActesDTO a = ActesDTO.builder()
                .nom("Exists")
                .build();

        a = service.create(a);

        if (!service.exists(a.getId()))
            throw new RuntimeException("Exists failed: returned false");
        System.out.println("Exists passed.");
    }

    public static void testCount(actesService service) throws Exception {
        System.out.println("Testing Count...");
        long count = service.count();
        if (count < 0) throw new RuntimeException("Count failed: negative");
        System.out.println("Count passed.");
    }
}
