package ma.TeethCare.service.test;

import ma.TeethCare.entities.utilisateur.utilisateur;
import ma.TeethCare.repository.api.UtilisateurRepository;
import ma.TeethCare.service.modules.users.api.utilisateurService;
import ma.TeethCare.service.modules.users.impl.utilisateurServiceImpl;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Zouhair MOKADAMI
 * @date 2025-12-10
 */

public class UtilisateurServiceTest {

    // Stub implementation for UtilisateurRepository (In-Memory)
    static class UtilisateurRepositoryStub implements UtilisateurRepository {
        private Map<Long, utilisateur> data = new HashMap<>();
        private long idCounter = 1;

        @Override
        public List<utilisateur> findAll() throws SQLException {
            return new ArrayList<>(data.values());
        }

        @Override
        public utilisateur findById(Long id) {
            return data.get(id);
        }

        @Override
        public void create(utilisateur entity) {
            if (entity.getId() == null) {
                entity.setId(idCounter++);
            }
            data.put(entity.getId(), entity);
        }

        @Override
        public void update(utilisateur entity) {
            data.put(entity.getId(), entity);
        }

        @Override
        public void delete(utilisateur entity) {
            if (entity != null && entity.getId() != null) {
                data.remove(entity.getId());
            }
        }

        @Override
        public void deleteById(Long id) {
            data.remove(id);
        }

        @Override
        public Optional<utilisateur> findByEmail(String email) {
            return data.values().stream()
                    .filter(u -> email.equals(u.getEmail()))
                    .findFirst();
        }

        @Override
        public Optional<utilisateur> findByLogin(String login) {
            return data.values().stream()
                    .filter(u -> login.equals(u.getUsername()))
                    .findFirst();
        }
    }

    public static void main(String[] args) {
        try {
            UtilisateurRepositoryStub repo = new UtilisateurRepositoryStub();
            utilisateurService service = new utilisateurServiceImpl(repo);

            testCreate(service);
            testFindById(service);
            testFindAll(service);
            testUpdate(service);
            testDelete(service);
            testExists(service);
            testCount(service);

            System.out.println("All UtilisateurService tests passed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testCreate(utilisateurService service) throws Exception {
        System.out.println("Testing Create...");
        utilisateur u = utilisateur.builder()
                .nom("Test User")
                .email("user@test.com")
                .username("testLogin")
                .password("1234")
                .build();
        utilisateur created = service.create(u);
        if (created.getId() == null) throw new RuntimeException("Create failed: ID is null");
        System.out.println("Create passed.");
    }

    public static void testFindById(utilisateurService service) throws Exception {
        System.out.println("Testing FindById...");
        utilisateur u = utilisateur.builder().nom("User2").email("u2@test.com").username("user2").build();
        u = service.create(u);
        Optional<utilisateur> found = service.findById(u.getId());
        if (!found.isPresent()) throw new RuntimeException("FindById failed: not found");
        System.out.println("FindById passed.");
    }

    public static void testFindAll(utilisateurService service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();
        service.create(utilisateur.builder().nom("User3").email("u3@test.com").username("user3").build());
        if (service.findAll().size() != initialCount + 1) throw new RuntimeException("FindAll failed: count mismatch");
        System.out.println("FindAll passed.");
    }

    public static void testUpdate(utilisateurService service) throws Exception {
        System.out.println("Testing Update...");
        utilisateur u = utilisateur.builder().nom("User4").email("u4@test.com").username("user4").build();
        u = service.create(u);
        u.setNom("Updated Name");
        utilisateur updated = service.update(u);
        if (!"Updated Name".equals(updated.getNom())) throw new RuntimeException("Update failed: value mismatch");
        System.out.println("Update passed.");
    }

    public static void testDelete(utilisateurService service) throws Exception {
        System.out.println("Testing Delete...");
        utilisateur u = utilisateur.builder().nom("User5").email("u5@test.com").username("user5").build();
        u = service.create(u);
        Long id = u.getId();
        service.delete(id);
        if (service.exists(id)) throw new RuntimeException("Delete failed: still exists");
        System.out.println("Delete passed.");
    }

    public static void testExists(utilisateurService service) throws Exception {
        System.out.println("Testing Exists...");
        utilisateur u = utilisateur.builder().nom("User6").email("u6@test.com").username("user6").build();
        u = service.create(u);
        if (!service.exists(u.getId())) throw new RuntimeException("Exists failed: returned false");
        System.out.println("Exists passed.");
    }

    public static void testCount(utilisateurService service) throws Exception {
        System.out.println("Testing Count...");
        long count = service.count();
        if (count < 0) throw new RuntimeException("Count failed: negative");
        System.out.println("Count passed.");
    }
}

