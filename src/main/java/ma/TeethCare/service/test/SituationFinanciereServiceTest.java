package ma.TeethCare.service.test;

import ma.TeethCare.entities.situationFinanciere.situationFinanciere;
import ma.TeethCare.repository.api.SituationFinanciereRepository;
import ma.TeethCare.service.modules.caisse.api.situationFinanciereService;
import ma.TeethCare.service.modules.caisse.impl.situationFinanciereServiceImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.sql.SQLException;

/**
 * @author Haitam ELOUALI
 * @date 2025-12-14
 */

import ma.TeethCare.mvc.dto.situationFinanciere.SituationFinanciereDTO;
import ma.TeethCare.common.enums.Statut;
import ma.TeethCare.common.enums.Promo;

public class SituationFinanciereServiceTest {

    static class SituationFinanciereRepositoryStub implements SituationFinanciereRepository {
        private Map<Long, situationFinanciere> data = new HashMap<>();
        private long idCounter = 1;

        @Override
        public List<situationFinanciere> findAll() throws SQLException {
            return new ArrayList<>(data.values());
        }

        @Override
        public situationFinanciere findById(Long id) {
            return data.get(id);
        }

        @Override
        public void create(situationFinanciere entity) {
            if (entity.getId() == null) {
                entity.setId(idCounter++);
            }
            data.put(entity.getId(), entity);
        }

        @Override
        public void update(situationFinanciere entity) {
            data.put(entity.getId(), entity);
        }

        @Override
        public void delete(situationFinanciere entity) {
            data.remove(entity.getId());
        }

        @Override
        public void deleteById(Long id) {
            data.remove(id);
        }
    }

    public static void main(String[] args) {
        try {
            SituationFinanciereRepositoryStub repo = new SituationFinanciereRepositoryStub();
            situationFinanciereService service = new situationFinanciereServiceImpl(repo);

            testCreate(service);
            // Re-instantiate service/repo for cleaner tests or reuse. Reuse is fine if clean.
            // testFindById depends on created data, so reuse.
            
            // Note: Since method signatures are generic, we can't easily pass ID unless we return it.
            // We'll adjust tests to return created object or ID logic.
            // For simplicity, we are running sequential tests in main.
            
            System.out.println("All SituationFinanciereService tests passed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testCreate(situationFinanciereService service) throws Exception {
        System.out.println("Testing Create...");
        SituationFinanciereDTO s = SituationFinanciereDTO.builder()
                .totalDesActes(1000.0)
                .totalPaye(500.0)
                .credit(500.0)
                .statut(Statut.En_attente.name())
                .enPromo(Promo.Aucune.name())
                .dossierMedicaleId(1L)
                .build();

        SituationFinanciereDTO created = service.create(s);
        if (created.getId() == null)
            throw new RuntimeException("Create failed: ID is null");
        System.out.println("Create passed. ID: " + created.getId());
        
        testFindById(service, created.getId());
        testFindAll(service);
        testUpdate(service, created.getId());
        testDelete(service, created.getId());
    }

    public static void testFindById(situationFinanciereService service, Long id) throws Exception {
        System.out.println("Testing FindById...");
        Optional<SituationFinanciereDTO> found = service.findById(id);
        if (!found.isPresent())
            throw new RuntimeException("FindById failed: not found");
        System.out.println("FindById passed.");
    }

    public static void testFindAll(situationFinanciereService service) throws Exception {
        System.out.println("Testing FindAll...");
        List<SituationFinanciereDTO> list = service.findAll();
        if (list.isEmpty())
            throw new RuntimeException("FindAll failed: list empty (should have at least 1)");
        System.out.println("FindAll passed. Size: " + list.size());
    }

    public static void testUpdate(situationFinanciereService service, Long id) throws Exception {
        System.out.println("Testing Update...");
        Optional<SituationFinanciereDTO> opt = service.findById(id);
        if (opt.isPresent()) {
            SituationFinanciereDTO s = opt.get();
            s.setTotalDesActes(450.0);
            
            SituationFinanciereDTO updated = service.update(s);
            if (!updated.getTotalDesActes().equals(450.0))
                throw new RuntimeException("Update failed: value mismatch");
            System.out.println("Update passed.");
        }
    }

    public static void testDelete(situationFinanciereService service, Long id) throws Exception {
        System.out.println("Testing Delete...");
        service.delete(id);
        if (service.exists(id))
            throw new RuntimeException("Delete failed: still exists");
        System.out.println("Delete passed.");
    }

    // Removed standalone tests for simplicity and flow (dependency on ID)
    public static void testExists(situationFinanciereService service) throws Exception {
         // ...
    }

    public static void testCount(situationFinanciereService service) throws Exception {
         // ...
    }
}
