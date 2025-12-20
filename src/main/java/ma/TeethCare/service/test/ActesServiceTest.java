package ma.TeethCare.service.test;

import ma.TeethCare.entities.actes.actes;
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
            if (entity.getIdEntite() == null) {
                entity.setIdEntite(idCounter++);
            }
            data.put(entity.getIdEntite(), entity);
        }

        @Override
        public void update(actes entity) {
            data.put(entity.getIdEntite(), entity);
        }

        @Override
        public void delete(actes entity) {
            if (entity != null && entity.getIdEntite() != null) {
                data.remove(entity.getIdEntite());
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
        actes a = new actes();
        a.setNom("Acte Alpha");
        a.setCategorie("CAT1");
        a.setPrix(120.0);

        actes created = service.create(a);
        if (created.getIdEntite() == null) throw new RuntimeException("Create failed: ID is null");
        System.out.println("Create passed.");
    }

    public static void testFindById(actesService service) throws Exception {
        System.out.println("Testing FindById...");
        actes a = new actes();
        a.setNom("FindById");
        a = service.create(a);

        Optional<actes> found = service.findById(a.getIdEntite());
        if (!found.isPresent()) throw new RuntimeException("FindById failed: not found");
        System.out.println("FindById passed.");
    }

    public static void testFindAll(actesService service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();

        actes a = new actes();
        a.setNom("All 1");
        service.create(a);

        if (service.findAll().size() != initialCount + 1)
            throw new RuntimeException("FindAll failed: count mismatch");
        System.out.println("FindAll passed.");
    }

    public static void testUpdate(actesService service) throws Exception {
        System.out.println("Testing Update...");
        actes a = new actes();
        a.setNom("Old Name");
        a.setCategorie("CAT1");
        a.setPrix(50.0);

        a = service.create(a);
        a.setNom("New Name");

        actes updated = service.update(a);
        if (!"New Name".equals(updated.getNom()))
            throw new RuntimeException("Update failed: value mismatch");
        System.out.println("Update passed.");
    }

    public static void testDelete(actesService service) throws Exception {
        System.out.println("Testing Delete...");
        actes a = new actes();
        a.setNom("Delete Me");

        a = service.create(a);
        Long id = a.getIdEntite();

        service.delete(id);

        if (service.exists(id))
            throw new RuntimeException("Delete failed: still exists");
        System.out.println("Delete passed.");
    }

    public static void testExists(actesService service) throws Exception {
        System.out.println("Testing Exists...");
        actes a = new actes();
        a.setNom("Exists");

        a = service.create(a);

        if (!service.exists(a.getIdEntite()))
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

