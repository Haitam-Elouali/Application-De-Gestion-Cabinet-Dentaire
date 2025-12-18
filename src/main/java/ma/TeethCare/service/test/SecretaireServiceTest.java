package ma.TeethCare.service.test;

import ma.TeethCare.entities.secretaire.secretaire;
import ma.TeethCare.service.modules.impl.secretaireServiceImpl;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Salma BAKAROUM
 * @date 2025-12-17
 */

public class SecretaireServiceTest {

    private static secretaireServiceImpl secretaireService;
    private static Long createdSecId;

    // Stub implementation for SecretaireRepository
    static class SecretaireRepositoryStub implements ma.TeethCare.repository.api.SecretaireRepository {
        private java.util.Map<Long, secretaire> data = new java.util.HashMap<>();
        private long idCounter = 1;

        @Override
        public void create(secretaire entity) {
            if (entity.getIdEntite() == null)
                entity.setIdEntite(idCounter++);
            data.put(entity.getIdEntite(), entity);
        }

        @Override
        public secretaire findById(Long id) {
            return data.get(id);
        }

        @Override
        public java.util.List<secretaire> findAll() {
            return new java.util.ArrayList<>(data.values());
        }

        @Override
        public void update(secretaire entity) {
            data.put(entity.getIdEntite(), entity);
        }

        @Override
        public void delete(secretaire entity) {
            if (entity != null)
                data.remove(entity.getIdEntite());
        }

        @Override
        public void deleteById(Long id) {
            data.remove(id);
        }

        @Override
        public Optional<secretaire> findByNumCNSS(String numCNSS) {
            // Stub doesn't have numCNSS field on entity, but we follow interface
            return Optional.empty();
        }

        @Override
        public Optional<secretaire> findByCin(String cin) {
            return data.values().stream()
                    .filter(s -> s.getCin() != null && s.getCin().equals(cin))
                    .findFirst();
        }
    }

    public static void main(String[] args) {
        System.out.println("=== DÉBUT DES TESTS SECRÉTAIRE SERVICE ===");
        try {
            secretaireService = new secretaireServiceImpl(new SecretaireRepositoryStub());

            testCreate();
            testFindById();
            testUpdate();
            testFindAll();
            testExists();
            testCount();
            testDelete();
            testFindByCin();

            System.out.println("\n=== TOUS LES TESTS SONT TERMINÉS AVEC SUCCÈS ===");
        } catch (Exception e) {
            System.err.println("❌ UNE ERREUR EST SURVENUE DURANT LES TESTS:");
            e.printStackTrace();
        }
    }

    private static void testCreate() throws Exception {
        System.out.println("\n--- TEST: CREATE ---");
        secretaire s = new secretaire();
        long timestamp = System.currentTimeMillis();
        s.setNom("SecTest");
        s.setEmail("sec." + timestamp + "@teethcare.ma");
        s.setCin("CIN" + timestamp);
        s.setSalaire(4500.0);
        s.setCommission(15);
        s.setDateEmbauche(LocalDate.now());

        secretaireService.create(s);
        createdSecId = s.getIdEntite();

        if (createdSecId != null) {
            System.out.println("✅ Secrétaire créé avec ID : " + createdSecId);
        } else {
            throw new RuntimeException("❌ ERREUR: ID est null après création.");
        }
    }

    private static void testFindById() throws Exception {
        System.out.println("\n--- TEST: FIND BY ID ---");
        Optional<secretaire> found = secretaireService.findById(createdSecId);
        if (found.isPresent() && "SecTest".equals(found.get().getNom())) {
            System.out.println("✅ Secrétaire trouvé: " + found.get().getNom());
        } else {
            throw new RuntimeException("❌ ERREUR: Secrétaire non trouvé ou nom incorrect.");
        }
    }

    private static void testUpdate() throws Exception {
        System.out.println("\n--- TEST: UPDATE ---");
        Optional<secretaire> found = secretaireService.findById(createdSecId);
        secretaire s = found.get();
        s.setNom("UpdatedSec");
        secretaireService.update(s);

        Optional<secretaire> updated = secretaireService.findById(createdSecId);
        if (updated.isPresent() && "UpdatedSec".equals(updated.get().getNom())) {
            System.out.println("✅ Secrétaire mis à jour avec succès.");
        } else {
            throw new RuntimeException("❌ ERREUR: Mise à jour échouée.");
        }
    }

    private static void testFindAll() throws Exception {
        System.out.println("\n--- TEST: FIND ALL ---");
        List<secretaire> list = secretaireService.findAll();
        System.out.println("ℹ️ Nombre de secrétaires: " + list.size());
        if (!list.isEmpty()) {
            System.out.println("✅ FindAll retourne des résultats.");
        } else {
            throw new RuntimeException("❌ ERREUR: Liste vide.");
        }
    }

    private static void testExists() throws Exception {
        System.out.println("\n--- TEST: EXISTS ---");
        if (secretaireService.exists(createdSecId)) {
            System.out.println("✅ La secrétaire existe bien.");
        } else {
            throw new RuntimeException("❌ ERREUR: La secrétaire n'existe pas.");
        }
    }

    private static void testCount() throws Exception {
        System.out.println("\n--- TEST: COUNT ---");
        if (secretaireService.count() > 0) {
            System.out.println("✅ Nombre de secrétaires correct: " + secretaireService.count());
        } else {
            throw new RuntimeException("❌ ERREUR: Le compteur est à 0.");
        }
    }

    private static void testDelete() throws Exception {
        System.out.println("\n--- TEST: DELETE ---");
        boolean deleted = secretaireService.delete(createdSecId);
        if (deleted && !secretaireService.exists(createdSecId)) {
            System.out.println("✅ Secrétaire supprimé avec succès.");
        } else {
            throw new RuntimeException("❌ ERREUR: La suppression a échoué.");
        }
    }

    private static void testFindByCin() throws Exception {
        System.out.println("\n--- TEST: FIND BY CIN ---");
        secretaire s = new secretaire();
        s.setNom("CinSec");
        s.setCin("SEC123");
        secretaireService.create(s);

        Optional<secretaire> found = secretaireService.findByCin("SEC123");
        if (found.isPresent() && "CinSec".equals(found.get().getNom())) {
            System.out.println("✅ Secrétaire trouvé par CIN.");
        } else {
            throw new RuntimeException("❌ ERREUR: Recherche par CIN échouée.");
        }
    }
}
