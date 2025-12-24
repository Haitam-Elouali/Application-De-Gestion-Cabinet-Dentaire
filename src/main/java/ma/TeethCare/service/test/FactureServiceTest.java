package ma.TeethCare.service.test;

import ma.TeethCare.entities.facture.facture;
import ma.TeethCare.repository.api.FactureRepository;
import ma.TeethCare.service.modules.caisse.api.factureService;
import ma.TeethCare.service.modules.caisse.impl.factureServiceImpl;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.sql.SQLException;

/**
 * @author ELOUALI Haitam
 * @date 2025-12-09
 */

import ma.TeethCare.service.modules.caisse.dto.FactureDto;
import ma.TeethCare.common.enums.Statut;
import java.time.LocalDateTime;

public class FactureServiceTest {

    static class FactureRepositoryStub implements FactureRepository {
        private Map<Long, facture> data = new HashMap<>();
        private long idCounter = 1;

        @Override
        public List<facture> findAll() throws SQLException {
            return new ArrayList<>(data.values());
        }

        @Override
        public facture findById(Long id) {
            return data.get(id);
        }

        @Override
        public void create(facture entity) {
            if (entity.getId() == null) {
                entity.setId(idCounter++);
            }
            data.put(entity.getId(), entity);
        }

        @Override
        public void update(facture entity) {
            data.put(entity.getId(), entity);
        }

        @Override
        public void delete(facture entity) {
            data.remove(entity.getId());
        }

        @Override
        public void deleteById(Long id) {
            data.remove(id);
        }
    }

    public static void main(String[] args) {
        try {
            FactureRepositoryStub repo = new FactureRepositoryStub();
            factureService service = new factureServiceImpl(repo);

            testCreate(service);
            testFindById(service);
            testFindAll(service);
            testUpdate(service);
            testDelete(service);
            testExists(service);
            testCount(service);

            System.out.println("All FactureService tests passed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testCreate(factureService service) throws Exception {
        System.out.println("Testing Create...");
        FactureDto f = new FactureDto(null, 1L, 1L, 1L, 100.0, 0.0, 100.0, Statut.En_attente, "CASH",
                LocalDateTime.now());

        FactureDto created = service.create(f);
        if (created.id() == null)
            throw new RuntimeException("Create failed: ID is null");
        System.out.println("Create passed.");
    }

    public static void testFindById(factureService service) throws Exception {
        System.out.println("Testing FindById...");
        FactureDto f = new FactureDto(null, 1L, 1L, 1L, 200.0, 0.0, 200.0, Statut.Payee, "CARD", LocalDateTime.now());
        f = service.create(f);

        FactureDto found = service.findById(f.id());
        if (found == null)
            throw new RuntimeException("FindById failed: not found");
        System.out.println("FindById passed.");
    }

    public static void testFindAll(factureService service) throws Exception {
        System.out.println("Testing FindAll...");
        int initialCount = service.findAll().size();
        service.create(
                new FactureDto(null, 1L, 1L, 1L, 300.0, 0.0, 300.0, Statut.En_attente, "CASH", LocalDateTime.now()));

        if (service.findAll().size() != initialCount + 1)
            throw new RuntimeException("FindAll failed: count mismatch");
        System.out.println("FindAll passed.");
    }

    public static void testUpdate(factureService service) throws Exception {
        System.out.println("Testing Update...");
        FactureDto f = new FactureDto(null, 1L, 1L, 1L, 400.0, 0.0, 400.0, Statut.En_attente, "CASH",
                LocalDateTime.now());
        f = service.create(f);

        FactureDto toUpdate = new FactureDto(f.id(), f.consultationId(), f.patientId(), f.secretaireId(), 500.0,
                f.totalePaye(), 500.0, f.statut(), f.modePaiement(), f.dateFacture());

        FactureDto updated = service.update(f.id(), toUpdate);
        if (updated.totaleFacture() != 500.0)
            throw new RuntimeException("Update failed: value mismatch");
        System.out.println("Update passed.");
    }

    public static void testDelete(factureService service) throws Exception {
        System.out.println("Testing Delete...");
        FactureDto f = new FactureDto(null, 1L, 1L, 1L, 600.0, 0.0, 600.0, Statut.En_attente, "CASH",
                LocalDateTime.now());
        f = service.create(f);
        Long id = f.id();
        service.delete(id);
        if (service.exists(id))
            throw new RuntimeException("Delete failed: still exists");
        System.out.println("Delete passed.");
    }

    public static void testExists(factureService service) throws Exception {
        System.out.println("Testing Exists...");
        FactureDto f = new FactureDto(null, 1L, 1L, 1L, 700.0, 0.0, 700.0, Statut.En_attente, "CASH",
                LocalDateTime.now());
        f = service.create(f);
        if (!service.exists(f.id()))
            throw new RuntimeException("Exists failed: returned false");
        System.out.println("Exists passed.");
    }

    public static void testCount(factureService service) throws Exception {
        System.out.println("Testing Count...");
        long count = service.count();
        if (count < 0)
            throw new RuntimeException("Count failed: negative");
        System.out.println("Count passed.");
    }
}
