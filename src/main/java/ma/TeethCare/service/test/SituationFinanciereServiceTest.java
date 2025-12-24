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

import ma.TeethCare.service.modules.caisse.dto.SituationFinanciereDto;
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
            testFindById(service);
            testFindAll(service);
            testUpdate(service);
            testDelete(service);
            testExists(service);
            testCount(service);

            System.out.println("All SituationFinanciereService tests passed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testCreate(situationFinanciereService service) throws Exception {
        System.out.println("Testing Create...");
        SituationFinanciereDto s = new SituationFinanciereDto(null, 1000.0, 500.0, 500.0, Statut.En_attente,
                Promo.Aucune,
                1L);

        SituationFinanciereDto created = service.create(s);
        if (created.id() == null)
            throw new RuntimeException("Create failed: ID is null");
        System.out.println("Create passed.");
    }

    public static void testFindById(situationFinanciereService service) throws Exception {
        System.out.println("Testing FindById...");
        SituationFinanciereDto s = new SituationFinanciereDto(null, 200.0, 0.0, 200.0, Statut.En_attente, Promo.Aucune,
                1L);
        s = service.create(s);

        SituationFinanciereDto found = service.findById(s.id());
        if (found == null)
            throw new RuntimeException("FindById failed: not found");
        System.out.println("FindById passed.");
    }

    public static void testFindAll(situationFinanciereService service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();
        service.create(new SituationFinanciereDto(null, 300.0, 0.0, 300.0, Statut.En_attente, Promo.Aucune, 1L));

        if (service.findAll().size() != initialCount + 1)
            throw new RuntimeException("FindAll failed: count mismatch");
        System.out.println("FindAll passed.");
    }

    public static void testUpdate(situationFinanciereService service) throws Exception {
        System.out.println("Testing Update...");
        SituationFinanciereDto s = new SituationFinanciereDto(null, 400.0, 0.0, 400.0, Statut.En_attente, Promo.Aucune,
                1L);
        s = service.create(s);

        SituationFinanciereDto toUpdate = new SituationFinanciereDto(s.id(), 450.0, s.totalPaye(), 450.0, s.statut(),
                s.enPromo(), s.dossierMedicaleId());

        SituationFinanciereDto updated = service.update(s.id(), toUpdate);
        if (!updated.totalDesActes().equals(450.0))
            throw new RuntimeException("Update failed: value mismatch");
        System.out.println("Update passed.");
    }

    public static void testDelete(situationFinanciereService service) throws Exception {
        System.out.println("Testing Delete...");
        SituationFinanciereDto s = new SituationFinanciereDto(null, 0.0, 0.0, 0.0, Statut.Payee, Promo.Aucune, 1L);
        s = service.create(s);
        Long id = s.id();
        service.delete(id);
        if (service.exists(id))
            throw new RuntimeException("Delete failed: still exists");
        System.out.println("Delete passed.");
    }

    public static void testExists(situationFinanciereService service) throws Exception {
        System.out.println("Testing Exists...");
        SituationFinanciereDto s = new SituationFinanciereDto(null, 100.0, 0.0, 100.0, Statut.En_attente, Promo.Aucune,
                1L);
        s = service.create(s);
        if (!service.exists(s.id()))
            throw new RuntimeException("Exists failed: returned false");
        System.out.println("Exists passed.");
    }

    public static void testCount(situationFinanciereService service) throws Exception {
        System.out.println("Testing Count...");
        long count = service.count();
        if (count < 0)
            throw new RuntimeException("Count failed: negative");
        System.out.println("Count passed.");
    }
}
