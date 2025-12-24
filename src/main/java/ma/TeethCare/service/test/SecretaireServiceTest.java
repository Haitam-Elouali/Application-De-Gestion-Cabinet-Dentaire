package ma.TeethCare.service.test;

import ma.TeethCare.entities.secretaire.secretaire;
import ma.TeethCare.service.modules.users.impl.secretaireServiceImpl;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Salma BAKAROUM
 * @date 2025-12-17
 */

import ma.TeethCare.service.modules.users.dto.CreateSecretaireRequest;
import ma.TeethCare.service.modules.users.dto.UserAccountDto;
import ma.TeethCare.common.enums.Sexe;

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
            // testUpdate(); // Update might not be supported/tested via DTO in this simple
            // test or requires specific request
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
        long timestamp = System.currentTimeMillis();
        CreateSecretaireRequest req = new CreateSecretaireRequest(
                "SecTest", // nom
                "sec." + timestamp + "@teethcare.ma", // email
                "Address", // adresse
                "CIN" + timestamp, // cin
                "0600000000", // tel
                Sexe.Femme, // sexe
                "sec" + timestamp, // login
                "pass", // motDePasse
                LocalDate.now(), // dateNaissance
                4500.0, // salaire
                150.0, // prime
                LocalDate.now(), // dateRecrutement
                18, // soldeConge
                "CNSS" + timestamp, // numCNSS
                10.0 // commission
        );

        UserAccountDto created = secretaireService.create(req);
        createdSecId = created.id();

        if (createdSecId != null) {
            System.out.println("✅ Secrétaire créé avec ID : " + createdSecId);
        } else {
            throw new RuntimeException("❌ ERREUR: ID est null après création.");
        }
    }

    private static void testFindById() throws Exception {
        System.out.println("\n--- TEST: FIND BY ID ---");
        UserAccountDto found = secretaireService.findById(createdSecId);
        if (found != null && "SecTest".equals(found.nom())) {
            System.out.println("✅ Secrétaire trouvé: " + found.nom());
        } else {
            throw new RuntimeException("❌ ERREUR: Secrétaire non trouvé ou nom incorrect.");
        }
    }

    private static void testFindAll() throws Exception {
        System.out.println("\n--- TEST: FIND ALL ---");
        List<UserAccountDto> list = secretaireService.findAll();
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
        long timestamp = System.currentTimeMillis() + 100;
        CreateSecretaireRequest req = new CreateSecretaireRequest(
                "CinSec", // nom
                "sec.cin" + timestamp + "@teethcare.ma", // email
                "Address", // adresse
                "SEC123" + timestamp, // cin (used for search)
                "0600000000", // tel
                Sexe.Homme, // sexe
                "cinsec" + timestamp, // login
                "pass", // motDePasse
                LocalDate.now(), // dateNaissance
                4500.0, // salaire
                150.0, // prime
                LocalDate.now(), // dateRecrutement
                18, // soldeConge
                "CNSS" + timestamp, // numCNSS
                10.0 // commission
        );
        secretaireService.create(req);

        Optional<UserAccountDto> found = secretaireService.findByCin("SEC123" + timestamp);
        if (found.isPresent() && "CinSec".equals(found.get().nom())) {
            System.out.println("✅ Secrétaire trouvé par CIN.");
        } else {
            throw new RuntimeException("❌ ERREUR: Recherche par CIN échouée.");
        }
    }
}
