package ma.TeethCare.service.test;

import ma.TeethCare.entities.admin.admin;
import ma.TeethCare.service.modules.impl.AdminServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Salma BAKAROUM
 * @date 2025-12-10
 */

public class AdminServiceTest {
    
    // Static service instance to be reused across tests
    private static AdminServiceImpl adminService;
    private static Long createdAdminId;

    // Stub implementation for AdminRepository
    static class AdminRepositoryStub implements ma.TeethCare.repository.api.AdminRepository {
        private java.util.Map<Long, admin> data = new java.util.HashMap<>();
        private long idCounter = 1;

        @Override public void create(admin entity) {
             if (entity.getIdEntite() == null) entity.setIdEntite(idCounter++);
             data.put(entity.getIdEntite(), entity);
        }
        @Override public admin findById(Long id) { return data.get(id); }
        @Override public java.util.List<admin> findAll() { return new java.util.ArrayList<>(data.values()); }
        @Override public void update(admin entity) { data.put(entity.getIdEntite(), entity); }
        @Override public void delete(admin entity) { if (entity != null) data.remove(entity.getIdEntite()); }
        @Override public void deleteById(Long id) { data.remove(id); }

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
            // Setup DB structure if needed
            // ensureSchema(); // Disabled for Stub test

            // Setup
            adminService = new AdminServiceImpl(new AdminRepositoryStub());
            
            testCreate();
            testFindById();
            testUpdate();
            testFindAll();
            testExists();
            testCount();
            testDelete();
            
            System.out.println("\n=== TOUS LES TESTS SONT TERMINÉS ===");
            
        } catch (Exception e) {
            System.err.println("❌ UNE EXCEPTION CRITIQUE EST SURVENUE:");
            e.printStackTrace();
        }
    }

    private static void ensureSchema() {
        try (java.sql.Connection conn = ma.TeethCare.conf.SessionFactory.getInstance().getConnection();
             java.sql.Statement stmt = conn.createStatement()) {
            
            // Attempt to add columns if they are missing. catch exception if strictly duplicate or just ignore execution errors for existing cols
            // MySQL 8.0+ supports IF NOT EXISTS in ALTER, but older versions or different dialects might not.
            // Safe way: try-catch each
            try {
                stmt.execute("ALTER TABLE admin ADD COLUMN domaine VARCHAR(255)");
                System.out.println("✓ Colonne 'domaine' ajoutée à la table 'admin'");
            } catch (Exception e) {
                // Ignore if exists
            }
            try {
                stmt.execute("ALTER TABLE admin ADD COLUMN permissionAdmin VARCHAR(255)");
                System.out.println("✓ Colonne 'permissionAdmin' ajoutée à la table 'admin'");
            } catch (Exception e) {
                // Ignore if exists
            }
        } catch (Exception e) {
            System.err.println("Note: Schema update check failed (might be fine if columns exist): " + e.getMessage());
        }
    }

    public static void testCreate() throws Exception {
        System.out.println("\n--- TEST: CREATE ---");
        admin newAdmin = new admin();
        long timestamp = System.currentTimeMillis();
        newAdmin.setNom("AdminTest");
        newAdmin.setEmail("admin." + timestamp + "@teethcare.ma");
        newAdmin.setUsername("admin" + timestamp); // Unique Login
        newAdmin.setPassword("Secret123");
        // BaseEntity attributes handled by repo if not set, or we set dateCreation
        newAdmin.setDateCreation(LocalDate.now());

        try {
            adminService.create(newAdmin);
            createdAdminId = newAdmin.getIdEntite(); // OR getIdUser/getIdAdmin depending on mapping
            if (createdAdminId == null) createdAdminId = newAdmin.getId();

            System.out.println("✅ Admin créé avec ID : " + createdAdminId);
            if (createdAdminId == null) {
                System.err.println("❌ ERREUR: ID est null après création.");
            }
        } catch (Exception e) {
            System.err.println("❌ CREATE FAILED: " + e.getMessage());
            throw e;
        }
    }

    public static void testFindById() throws Exception {
        System.out.println("\n--- TEST: FIND BY ID ---");
        if (createdAdminId == null) {
            System.out.println("⚠️ Skip (Create failed)");
            return;
        }
        Optional<admin> found = adminService.findById(createdAdminId);
        if (found.isPresent()) {
            System.out.println("✅ Admin trouvé: " + found.get().getNom());
        } else {
            System.err.println("❌ ERREUR: Admin non trouvé avec ID " + createdAdminId);
        }
    }

    public static void testUpdate() throws Exception {
        System.out.println("\n--- TEST: UPDATE ---");
        if (createdAdminId == null) return;
        
        Optional<admin> found = adminService.findById(createdAdminId);
        if (found.isPresent()) {
            admin a = found.get();
            a.setNom("UpdatedAdmin");
            
            adminService.update(a);
            
            Optional<admin> updated = adminService.findById(createdAdminId);
            if (updated.isPresent() && "Security".equals("UpdatedAdmin".equals(updated.get().getNom()))) {
                System.out.println("✅ Admin mis à jour avec succès.");
            } else {
                System.err.println("❌ ERREUR: Mise à jour échouée.");
            }
        }
    }

    public static void testFindAll() throws Exception {
        System.out.println("\n--- TEST: FIND ALL ---");
        List<admin> list = adminService.findAll();
        System.out.println("ℹ️ Nombre d'admins: " + list.size());
        if (list.size() > 0) {
            System.out.println("✅ FindAll retourne des résultats.");
        } else {
            System.out.println("⚠️ Liste vide.");
        }
    }

    public static void testExists() throws Exception {
        System.out.println("\n--- TEST: EXISTS ---");
        if (createdAdminId == null) return;
        boolean exists = adminService.exists(createdAdminId);
        System.out.println("Exists (" + createdAdminId + "): " + exists);
        if (exists) System.out.println("✅ Exists OK.");
        else System.err.println("❌ ERREUR: Exists KO.");
    }

    public static void testCount() throws Exception {
        System.out.println("\n--- TEST: COUNT ---");
        long c = adminService.count();
        System.out.println("Count: " + c);
        if (c > 0) System.out.println("✅ Count OK.");
    }

    public static void testDelete() throws Exception {
        System.out.println("\n--- TEST: DELETE ---");
        if (createdAdminId == null) return;
        
        boolean deleted = adminService.delete(createdAdminId);
        if (deleted) System.out.println("✅ Delete retourné true.");
        
        if (!adminService.exists(createdAdminId)) {
            System.out.println("✅ Admin supprimé (vérifié par exists).");
        } else {
            System.err.println("❌ ERREUR: Admin existe toujours !");
        }
    }
}
