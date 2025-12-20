package ma.TeethCare.service.test;

import ma.TeethCare.entities.staff.staff;
import ma.TeethCare.service.modules.users.impl.staffServiceImpl;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Salma BAKAROUM
 * @date 2025-12-17
 */

public class StaffServiceTest {

    private static staffServiceImpl staffService;
    private static Long createdStaffId;

    // Stub implementation for StaffRepository
    static class StaffRepositoryStub implements ma.TeethCare.repository.api.StaffRepository {
        private java.util.Map<Long, staff> data = new java.util.HashMap<>();
        private long idCounter = 1;

        @Override
        public void create(staff entity) {
            if (entity.getIdEntite() == null)
                entity.setIdEntite(idCounter++);
            data.put(entity.getIdEntite(), entity);
        }

        @Override
        public staff findById(Long id) {
            return data.get(id);
        }

        @Override
        public java.util.List<staff> findAll() {
            return new java.util.ArrayList<>(data.values());
        }

        @Override
        public void update(staff entity) {
            data.put(entity.getIdEntite(), entity);
        }

        @Override
        public void delete(staff entity) {
            if (entity != null)
                data.remove(entity.getIdEntite());
        }

        @Override
        public void deleteById(Long id) {
            data.remove(id);
        }

        @Override
        public Optional<staff> findByEmail(String email) {
            return data.values().stream()
                    .filter(s -> s.getEmail() != null && s.getEmail().equals(email))
                    .findFirst();
        }

        @Override
        public Optional<staff> findByCin(String cin) {
            return data.values().stream()
                    .filter(s -> s.getCin() != null && s.getCin().equals(cin))
                    .findFirst();
        }
    }

    public static void main(String[] args) {
        System.out.println("=== DÉBUT DES TESTS STAFF SERVICE ===");
        try {
            staffService = new staffServiceImpl(new StaffRepositoryStub());

            testCreate();
            testFindById();
            testUpdate();
            testFindAll();
            testExists();
            testCount();
            testDelete();
            testFindByEmail();
            testFindByCin();

            System.out.println("\n=== TOUS LES TESTS SONT TERMINÉS AVEC SUCCÈS ===");
        } catch (Exception e) {
            System.err.println("❌ UNE ERREUR EST SURVENUE DURANT LES TESTS:");
            e.printStackTrace();
        }
    }

    private static void testCreate() throws Exception {
        System.out.println("\n--- TEST: CREATE ---");
        staff s = new staff();
        long timestamp = System.currentTimeMillis();
        s.setNom("StaffTest");
        s.setEmail("staff." + timestamp + "@teethcare.ma");
        s.setCin("CIN" + timestamp);
        s.setSalaire(5000.0);
        s.setDateEmbauche(LocalDate.now());

        staffService.create(s);
        createdStaffId = s.getIdEntite();

        if (createdStaffId != null) {
            System.out.println("✅ Staff créé avec ID : " + createdStaffId);
        } else {
            throw new RuntimeException("❌ ERREUR: ID est null après création.");
        }
    }

    private static void testFindById() throws Exception {
        System.out.println("\n--- TEST: FIND BY ID ---");
        Optional<staff> found = staffService.findById(createdStaffId);
        if (found.isPresent() && "StaffTest".equals(found.get().getNom())) {
            System.out.println("✅ Staff trouvé: " + found.get().getNom());
        } else {
            throw new RuntimeException("❌ ERREUR: Staff non trouvé ou nom incorrect.");
        }
    }

    private static void testUpdate() throws Exception {
        System.out.println("\n--- TEST: UPDATE ---");
        Optional<staff> found = staffService.findById(createdStaffId);
        staff s = found.get();
        s.setNom("UpdatedStaff");
        staffService.update(s);

        Optional<staff> updated = staffService.findById(createdStaffId);
        if (updated.isPresent() && "UpdatedStaff".equals(updated.get().getNom())) {
            System.out.println("✅ Staff mis à jour avec succès.");
        } else {
            throw new RuntimeException("❌ ERREUR: Mise à jour échouée.");
        }
    }

    private static void testFindAll() throws Exception {
        System.out.println("\n--- TEST: FIND ALL ---");
        List<staff> list = staffService.findAll();
        System.out.println("ℹ️ Nombre de staff: " + list.size());
        if (!list.isEmpty()) {
            System.out.println("✅ FindAll retourne des résultats.");
        } else {
            throw new RuntimeException("❌ ERREUR: Liste vide.");
        }
    }

    private static void testExists() throws Exception {
        System.out.println("\n--- TEST: EXISTS ---");
        if (staffService.exists(createdStaffId)) {
            System.out.println("✅ Le staff existe bien.");
        } else {
            throw new RuntimeException("❌ ERREUR: Le staff n'existe pas.");
        }
    }

    private static void testCount() throws Exception {
        System.out.println("\n--- TEST: COUNT ---");
        if (staffService.count() > 0) {
            System.out.println("✅ Nombre de staff correct: " + staffService.count());
        } else {
            throw new RuntimeException("❌ ERREUR: Le compteur est à 0.");
        }
    }

    private static void testDelete() throws Exception {
        System.out.println("\n--- TEST: DELETE ---");
        boolean deleted = staffService.delete(createdStaffId);
        if (deleted && !staffService.exists(createdStaffId)) {
            System.out.println("✅ Staff supprimé avec succès.");
        } else {
            throw new RuntimeException("❌ ERREUR: La suppression a échoué.");
        }
    }

    private static void testFindByEmail() throws Exception {
        System.out.println("\n--- TEST: FIND BY EMAIL ---");
        staff s = new staff();
        s.setNom("EmailStaff");
        s.setEmail("emailtest@teethcare.ma");
        staffService.create(s);

        Optional<staff> found = staffService.findByEmail("emailtest@teethcare.ma");
        if (found.isPresent() && "EmailStaff".equals(found.get().getNom())) {
            System.out.println("✅ Staff trouvé par email.");
        } else {
            throw new RuntimeException("❌ ERREUR: Recherche par email échouée.");
        }
    }

    private static void testFindByCin() throws Exception {
        System.out.println("\n--- TEST: FIND BY CIN ---");
        staff s = new staff();
        s.setNom("CinStaff");
        s.setCin("CIN12345");
        staffService.create(s);

        Optional<staff> found = staffService.findByCin("CIN12345");
        if (found.isPresent() && "CinStaff".equals(found.get().getNom())) {
            System.out.println("✅ Staff trouvé par CIN.");
        } else {
            throw new RuntimeException("❌ ERREUR: Recherche par CIN échouée.");
        }
    }
}

