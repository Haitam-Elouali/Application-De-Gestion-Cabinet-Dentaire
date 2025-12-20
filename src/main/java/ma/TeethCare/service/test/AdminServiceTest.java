package ma.TeethCare.service.test;

import ma.TeethCare.entities.admin.admin;
import ma.TeethCare.service.modules.users.impl.AdminServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Salma BAKAROUM
 * @date 2025-12-10
 */
public class AdminServiceTest {

    private static AdminServiceImpl adminService;
    private static Long createdAdminId;

    // Stub implementation for AdminRepository
    static class AdminRepositoryStub implements ma.TeethCare.repository.api.AdminRepository {
        private java.util.Map<Long, admin> data = new java.util.HashMap<>();
        private long idCounter = 1;

        @Override
        public void create(admin entity) {
            if (entity.getIdEntite() == null)
                entity.setIdEntite(idCounter++);
            data.put(entity.getIdEntite(), entity);
        }

        @Override
        public admin findById(Long id) {
            return data.get(id);
        }

        @Override
        public java.util.List<admin> findAll() {
            return new java.util.ArrayList<>(data.values());
        }

        @Override
        public void update(admin entity) {
            data.put(entity.getIdEntite(), entity);
        }

        @Override
        public void delete(admin entity) {
            if (entity != null)
                data.remove(entity.getIdEntite());
        }

        @Override
        public void deleteById(Long id) {
            data.remove(id);
        }

        @Override
        public java.util.List<admin> findByDomaine(String domaine) throws Exception {
            return data.values().stream()
                    .filter(a -> a.getDomaine() != null && a.getDomaine().equals(domaine))
                    .collect(java.util.stream.Collectors.toList());
        }
    }

    public static void main(String[] args) {
        System.out.println("=== DÉBUT DES TESTS ADMIN SERVICE ===");
        try {
            adminService = new AdminServiceImpl(new AdminRepositoryStub());

            testCreate();
            testFindById();
            testUpdate();
            testFindAll();
            testExists();
            testCount();
            testDelete();
            testFindByDomaine();

            System.out.println("\n=== TOUS LES TESTS SONT TERMINÉS AVEC SUCCÈS ===");
        } catch (Exception e) {
            System.err.println("❌ UNE ERREUR EST SURVENUE DURANT LES TESTS:");
            e.printStackTrace();
        }
    }

    private static void testCreate() throws Exception {
        System.out.println("\n--- TEST: CREATE ---");
        admin newAdmin = new admin();
        long timestamp = System.currentTimeMillis();
        newAdmin.setNom("AdminTest");
        newAdmin.setEmail("admin." + timestamp + "@teethcare.ma");
        newAdmin.setUsername("admin" + timestamp);
        newAdmin.setPassword("Secret123");
        newAdmin.setDateCreation(LocalDate.now());

        adminService.create(newAdmin);
        createdAdminId = newAdmin.getIdEntite();

        if (createdAdminId != null) {
            System.out.println("✅ Admin créé avec ID : " + createdAdminId);
        } else {
            throw new RuntimeException("❌ ERREUR: ID est null après création.");
        }
    }

    private static void testFindById() throws Exception {
        System.out.println("\n--- TEST: FIND BY ID ---");
        Optional<admin> found = adminService.findById(createdAdminId);
        if (found.isPresent() && "AdminTest".equals(found.get().getNom())) {
            System.out.println("✅ Admin trouvé: " + found.get().getNom());
        } else {
            throw new RuntimeException("❌ ERREUR: Admin non trouvé ou nom incorrect.");
        }
    }

    private static void testUpdate() throws Exception {
        System.out.println("\n--- TEST: UPDATE ---");
        Optional<admin> found = adminService.findById(createdAdminId);
        admin a = found.get();
        a.setNom("UpdatedAdmin");
        adminService.update(a);

        Optional<admin> updated = adminService.findById(createdAdminId);
        if (updated.isPresent() && "UpdatedAdmin".equals(updated.get().getNom())) {
            System.out.println("✅ Admin mis à jour avec succès.");
        } else {
            throw new RuntimeException("❌ ERREUR: Mise à jour échouée.");
        }
    }

    private static void testFindAll() throws Exception {
        System.out.println("\n--- TEST: FIND ALL ---");
        List<admin> list = adminService.findAll();
        System.out.println("ℹ️ Nombre d'admins: " + list.size());
        if (!list.isEmpty()) {
            System.out.println("✅ FindAll retourne des résultats.");
        } else {
            throw new RuntimeException("❌ ERREUR: Liste vide.");
        }
    }

    private static void testExists() throws Exception {
        System.out.println("\n--- TEST: EXISTS ---");
        if (adminService.exists(createdAdminId)) {
            System.out.println("✅ L'admin existe bien.");
        } else {
            throw new RuntimeException("❌ ERREUR: L'admin n'existe pas.");
        }
    }

    private static void testCount() throws Exception {
        System.out.println("\n--- TEST: COUNT ---");
        if (adminService.count() > 0) {
            System.out.println("✅ Nombre d'admins correct: " + adminService.count());
        } else {
            throw new RuntimeException("❌ ERREUR: Le compteur est à 0.");
        }
    }

    private static void testDelete() throws Exception {
        System.out.println("\n--- TEST: DELETE ---");
        boolean deleted = adminService.delete(createdAdminId);
        if (deleted && !adminService.exists(createdAdminId)) {
            System.out.println("✅ Admin supprimé avec succès.");
        } else {
            throw new RuntimeException("❌ ERREUR: La suppression a échoué.");
        }
    }

    private static void testFindByDomaine() throws Exception {
        System.out.println("\n--- TEST: FIND BY DOMAINE ---");
        admin newAdmin = new admin();
        newAdmin.setNom("DomaineAdmin");
        newAdmin.setDomaine("Cardiology");
        adminService.create(newAdmin);

        List<admin> results = adminService.findByDomaine("Cardiology");
        if (!results.isEmpty() && "DomaineAdmin".equals(results.get(0).getNom())) {
            System.out.println("✅ Admin trouvé par domaine.");
        } else {
            throw new RuntimeException("❌ ERREUR: Recherche par domaine échouée.");
        }
    }
}

