package ma.TeethCare.service.test;

import ma.TeethCare.entities.statistique.statistique;
import ma.TeethCare.repository.api.StatistiqueRepository;
import ma.TeethCare.service.modules.dashboard_statistiques.api.statistiqueService;
import ma.TeethCare.service.modules.dashboard_statistiques.impl.statistiqueServiceImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

/**
 * @author ELOUALI Haitam
 * @date 2025-12-10
 */

import ma.TeethCare.service.modules.dashboard_statistiques.dto.StatistiqueDto;
import java.util.stream.Collectors;

public class StatistiqueServiceTest {

    // Stub implementation for StatistiqueRepository (In-Memory)
    static class StatistiqueRepositoryStub implements StatistiqueRepository {
        private Map<Long, statistique> data = new HashMap<>();
        private long idCounter = 1;

        @Override
        public List<statistique> findAll() throws SQLException {
            return new ArrayList<>(data.values());
        }

        @Override
        public statistique findById(Long id) {
            return data.get(id);
        }

        @Override
        public void create(statistique entity) {
            if (entity.getId() == null) {
                entity.setId(idCounter++);
            }
            data.put(entity.getId(), entity);
        }

        @Override
        public void update(statistique entity) {
            data.put(entity.getId(), entity);
        }

        @Override
        public void delete(statistique entity) {
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
        System.out.println("=== DÉBUT DES TESTS STATISTIQUE SERVICE ===");
        try {
            StatistiqueRepositoryStub repo = new StatistiqueRepositoryStub();
            statistiqueService service = new statistiqueServiceImpl(repo);

            testCreate(service);
            testFindById(service);
            testFindAll(service);
            testUpdate(service);
            testDelete(service);
            testExists(service);
            testCount(service);

            System.out.println("=== TOUS LES TESTS SONT TERMINÉS AVEC SUCCÈS ===");
        } catch (Exception e) {
            System.err.println("❌ UNE ERREUR EST SURVENUE DURANT LES TESTS:");
            e.printStackTrace();
        }
    }

    public static void testCreate(statistiqueService service) throws Exception {
        System.out.println("\n--- TEST: CREATE ---");
        StatistiqueDto s = new StatistiqueDto(
                null,
                "Chiffre d'affaire",
                10000.0,
                "FINANCIER",
                LocalDate.now(),
                null);

        StatistiqueDto created = service.create(s);

        if (created.id() != null) {
            System.out.println("✅ Statistique créée avec ID : " + created.id());
        } else {
            throw new RuntimeException("❌ ERREUR: ID est null après création.");
        }
    }

    public static void testFindById(statistiqueService service) throws Exception {
        System.out.println("\n--- TEST: FIND BY ID ---");
        // Assuming ID 1 created in previous step
        StatistiqueDto found = service.findById(1L);
        if (found != null) {
            System.out.println("✅ Statistique trouvée: " + found.nom());
        } else {
            throw new RuntimeException("❌ ERREUR: Statistique non trouvée avec ID 1");
        }
    }

    public static void testFindAll(statistiqueService service) throws Exception {
        System.out.println("\n--- TEST: FIND ALL ---");
        int initialCount = service.findAll().size();

        StatistiqueDto s2 = new StatistiqueDto(null, "Stat 2", 500.0, "TEST", LocalDate.now(), null);
        service.create(s2);

        List<StatistiqueDto> list = service.findAll();
        System.out.println("ℹ️ Nombre de statistiques: " + list.size());

        if (list.size() == initialCount + 1) {
            System.out.println("✅ FindAll retourne le bon nombre de résultats.");
        } else {
            throw new RuntimeException("❌ ERREUR: Nombre de résultats incorrect.");
        }
    }

    public static void testUpdate(statistiqueService service) throws Exception {
        System.out.println("\n--- TEST: UPDATE ---");
        StatistiqueDto found = service.findById(1L);
        if (found != null) {
            StatistiqueDto toUpdate = new StatistiqueDto(
                    found.id(),
                    "Updated Stat",
                    found.chiffre(),
                    found.type(),
                    found.dateCalcul(),
                    found.cabinetId());

            service.update(found.id(), toUpdate);

            StatistiqueDto updated = service.findById(1L);
            if (updated != null && "Updated Stat".equals(updated.nom())) {
                System.out.println("✅ Statistique mise à jour avec succès.");
            } else {
                throw new RuntimeException("❌ ERREUR: Mise à jour échouée.");
            }
        }
    }

    public static void testDelete(statistiqueService service) throws Exception {
        System.out.println("\n--- TEST: DELETE ---");
        boolean deleted = service.delete(1L);
        if (deleted) {
            System.out.println("✅ Delete retourné true.");
        } else {
            throw new RuntimeException("❌ ERREUR: Delete retourné false.");
        }

        if (!service.exists(1L)) {
            System.out.println("✅ Statistique supprimée (vérifié par exists).");
        } else {
            throw new RuntimeException("❌ ERREUR: Statistique existe toujours !");
        }
    }

    public static void testExists(statistiqueService service) throws Exception {
        System.out.println("\n--- TEST: EXISTS ---");
        StatistiqueDto s = new StatistiqueDto(null, "For Exists", 0.0, "TEST", LocalDate.now(), null);
        StatistiqueDto created = service.create(s);
        Long id = created.id();

        boolean exists = service.exists(id);
        System.out.println("Exists (" + id + "): " + exists);
        if (exists)
            System.out.println("✅ Exists OK.");
        else
            throw new RuntimeException("❌ ERREUR: Exists KO.");
    }

    public static void testCount(statistiqueService service) throws Exception {
        System.out.println("\n--- TEST: COUNT ---");
        long count = service.count();
        System.out.println("Count: " + count);
        if (count >= 0)
            System.out.println("✅ Count OK.");
        else
            throw new RuntimeException("❌ ERREUR: Count négatif.");
    }
}
